package onl.tran.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import onl.tran.constants.AlertMessages;
import onl.tran.constants.TransactionState;
import onl.tran.entity.Transaction;
import onl.tran.entity.User;
import onl.tran.entity.Wallet;
import onl.tran.exceptions.IllegalTransaction;
import onl.tran.exceptions.WrongNumber;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.SmsPhoneNumberDto;
import onl.tran.payload.TransactionDto;
import onl.tran.payload.request.ConfirmTransactionDto;
import onl.tran.repository.TransactionRepository;
import onl.tran.repository.WalletRepository;
import onl.tran.service.util.CurrentUserDetails;
import onl.tran.service.util.GenerateWalletNumber;
import onl.tran.service.util.SmsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {


 private final WalletRepository walletRepository;
 private final TransactionRepository transactionRepository;
 private final SmsService smsService;
 private final GenerateWalletNumber generateWalletNumber;

 @Transactional
 public ApiResponse performTransaction(TransactionDto transactionDto) throws IllegalTransaction {
  Optional<Wallet> optionalSourceWallet = walletRepository.findByWalletNumberAndActiveAndOwner_Id(transactionDto.getSourceWalletNumber(), true, CurrentUserDetails.getCurrentUserId());
  if (optionalSourceWallet.isEmpty()) {
   throw new IllegalTransaction();
  }
  Optional<Wallet> optionalDestinationWallet = walletRepository.findByWalletNumberAndActive(transactionDto.getDestinationWalletNumber(), true);
  if (optionalDestinationWallet.isEmpty()) {
   throw new IllegalTransaction();
  }

  if (transactionDto.getAmount() < 1000) {
   return new ApiResponse(AlertMessages.LESS_AMOUNT, false);
  }
  Wallet sourceWallet = optionalSourceWallet.get();
  Wallet destinationWallet = optionalDestinationWallet.get();

  if (transactionDto.getAmount() > sourceWallet.getBalance()) {
   return new ApiResponse(AlertMessages.NOT_ENOUGH_BALANCE, false);
  }

  Transaction transaction = new Transaction();
  transaction.setAmount(transactionDto.getAmount());
  transaction.setSourceWallet(sourceWallet);
  transaction.setDestinationWallet(destinationWallet);
  transaction.setState(TransactionState.PENDING);
  transaction.setTransactor(CurrentUserDetails.getCurrentUser());
  transactionRepository.save(transaction);

  return new ApiResponse(AlertMessages.TRANSACTION_CREATED, true);
 }


 public ApiResponse approveTransaction(Long id) throws WrongNumber {
  Optional<Transaction> optionalTransaction = transactionRepository.findByIdAndTransactor_Id(id, CurrentUserDetails.getCurrentUserId());
  if (optionalTransaction.isEmpty()) {
   return new ApiResponse(AlertMessages.NOT_FOUND_TRANSACTION, false);
  }
  if (optionalTransaction.get().getState() != TransactionState.PENDING) {
   return new ApiResponse(AlertMessages.TRANSACTION_STATE_NOT_MATCH, false);
  }
  Transaction transaction = optionalTransaction.get();
  User transactor = transaction.getTransactor();
  SmsPhoneNumberDto smsDto = new SmsPhoneNumberDto();

  int smsCode = generateWalletNumber.generateSixDigitNumber();

  smsDto.setPhoneNumber(transactor.getPhoneNumber());
  smsDto.setMessage(String.valueOf(smsCode));

  transaction.setConfirmationCode(smsCode);
  transaction.setExpirationDate(new Date(System.currentTimeMillis() + 1000 * 120));
  transactionRepository.save(transaction);

  return smsService.sendMessage(smsDto);
 }

 public ApiResponse confirmTransaction(ConfirmTransactionDto confirmTransactionDto) {
  Optional<Transaction> optionalTransaction = transactionRepository.findByIdAndTransactor_Id(confirmTransactionDto.getTransactionId(), CurrentUserDetails.getCurrentUserId());
  if (optionalTransaction.isEmpty()) {
   return new ApiResponse(AlertMessages.NOT_FOUND_TRANSACTION, false);
  }
  if (optionalTransaction.get().getState() != TransactionState.PENDING) {
   return new ApiResponse(AlertMessages.TRANSACTION_STATE_NOT_MATCH, false);
  }
  Transaction transaction = optionalTransaction.get();
  if (transaction.getConfirmationCode() != confirmTransactionDto.getSmsCode()) {
   return new ApiResponse(AlertMessages.WRONG_SMS_CODE, false);
  }
  if (transaction.getExpirationDate().before(new Date(System.currentTimeMillis()))) {
   return new ApiResponse(AlertMessages.EXPIRED_SMS_CODE, false);
  }
  transaction.setConfirmationCode(0);
  transaction.setState(TransactionState.APPROVED);
  transaction.setExpirationDate(null);
  transactionRepository.save(transaction);

  Wallet sourceWallet = transaction.getSourceWallet();
  sourceWallet.setBalance(sourceWallet.getBalance() - transaction.getAmount());
  walletRepository.save(sourceWallet);

  Wallet destinationWallet = transaction.getDestinationWallet();
  destinationWallet.setBalance(destinationWallet.getBalance() + transaction.getAmount());
  walletRepository.save(destinationWallet);
  return new ApiResponse(AlertMessages.SUCCESSFULLY_COMPLETED, true);
 }


 public ApiResponse cancelTransaction(Long id) {
  Optional<Transaction> optionalTransaction = transactionRepository.findByIdAndTransactor_Id(id, CurrentUserDetails.getCurrentUserId());
  if (optionalTransaction.isEmpty()) {
   return new ApiResponse(AlertMessages.NOT_FOUND_TRANSACTION, false);
  }
  optionalTransaction.get().setState(TransactionState.CANCELED);
  transactionRepository.save(optionalTransaction.get());
  return new ApiResponse(AlertMessages.CANCELED_TRANSACTION, true);
 }
}
