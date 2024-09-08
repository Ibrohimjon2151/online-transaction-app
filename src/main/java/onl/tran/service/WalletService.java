package onl.tran.service;

import lombok.RequiredArgsConstructor;
import onl.tran.payload.response.WalletResponseDto;
import onl.tran.repository.WalletRepository;
import onl.tran.constants.AlertMessages;
import onl.tran.entity.Wallet;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.WalletDto;
import onl.tran.service.util.CurrentUserDetails;
import onl.tran.service.util.GenerateWalletNumber;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
   wallet.setWalletNumber(Long.parseLong(walletNumber));

   wallet.setOwner(CurrentUserDetails.getCurrentUser());

   Calendar calendar = Calendar.getInstance();
   calendar.setTime(new Date(System.currentTimeMillis()));
   calendar.add(Calendar.YEAR, 4);
   Date date = new Date(calendar.getTimeInMillis());
   wallet.setExpireDate(date);


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
  Optional<Wallet> optionalWallet = walletRepository.findByIdAndOwner_Id(id, CurrentUserDetails.getCurrentUserId());
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

 public ApiResponse getAllActiveWallets() {
  try {
   List<Wallet> byActiveAndOwnerId = walletRepository.findByActiveAndOwner_Id(true, CurrentUserDetails.getCurrentUserId());
   List<WalletResponseDto> walletResponseDtoList = byActiveAndOwnerId.stream().map(this::convertWalletDto).toList();
   return new ApiResponse(true, walletResponseDtoList);
  } catch (Exception e) {
   return ApiResponse.builder().message(e.getMessage()).success(false).build();
  }
 }


 public ApiResponse getSingleWallet(Long id) {
  Optional<Wallet> optionalWallet = walletRepository.findByIdAndOwner_Id(id, CurrentUserDetails.getCurrentUserId());
  if (optionalWallet.isEmpty()) {
   return new ApiResponse(false, AlertMessages.NOT_FOUND_WALLET);
  }
  Wallet wallet = optionalWallet.get();
  WalletResponseDto walletResponseDto = convertWalletDto(wallet);
  return new ApiResponse(true, walletResponseDto);
 }


 /**
  * CONVERT WALLET TO WALLET DTO
  */
 private WalletResponseDto convertWalletDto(Wallet wallet) {
  WalletResponseDto dto = new WalletResponseDto();
  dto.setActive(wallet.isActive());
  dto.setBalance(wallet.getBalance());
  dto.setName(wallet.getName());
  dto.setOwner(wallet.getOwner().getFirstName() + " " + wallet.getOwner().getLastName());
  dto.setWalletNumber(wallet.getWalletNumber());
  dto.setExpirationDate(wallet.getExpireDate());
  return dto;
 }
}


