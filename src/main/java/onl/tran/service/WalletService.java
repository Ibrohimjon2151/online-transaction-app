package onl.tran.service;

import lombok.RequiredArgsConstructor;
import onl.tran.repository.WalletRepository;
import onl.tran.constants.AlertMessages;
import onl.tran.entity.Wallet;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.WalletDto;
import onl.tran.service.util.GenerateWalletNumber;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService {
 private final WalletRepository walletRepository;
 private final GenerateWalletNumber generateWalletNumber;

 public ApiResponse createWallet(WalletDto dto) {
  ApiResponse response;
  try {
   Wallet wallet = new Wallet();
   wallet.setName(dto.getName());

   String walletNumber = generateWalletNumber.getUnique16DigitNumber();
   wallet.setWalletNumber(walletNumber);

   walletRepository.save(wallet);
   response = ApiResponse.builder().message(AlertMessages.SUCCESSFULLY_SAVED_WALLET).success(true).build();
   return response;
  } catch (Exception e) {
   response = ApiResponse.builder().message(e.getMessage()).success(false).build();
   return response;
  }
 }

 public ApiResponse updateWallet(Long id, WalletDto walletDto) {
  ApiResponse response;
  Optional<Wallet> optionalWallet = walletRepository.findById(id);
  if (optionalWallet.isEmpty()) {
   response = ApiResponse.builder().message(AlertMessages.NOT_FOUND_WALLET).success(false).build();
   return response;
  }
  Wallet wallet = optionalWallet.get();
  wallet.setName(walletDto.getName());
  walletRepository.save(wallet);
  response = ApiResponse.builder().message(AlertMessages.SUCCESSFULLY_UPDATED_WALLET).success(true).build();
  return response;
 }
}


