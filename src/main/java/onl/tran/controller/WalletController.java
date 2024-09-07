package onl.tran.controller;

import lombok.RequiredArgsConstructor;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.WalletDto;
import onl.tran.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/wallet")
@RequiredArgsConstructor
public final class WalletController implements WalletControllerInt {

 private final WalletService walletService;

 /**
  * CREATE WALLET USER FOR THEMSELF IN SESSION
  */
 @Override
 @PostMapping
 public ResponseEntity<?> createWallet(@RequestBody WalletDto walletDto) {
  ApiResponse wallet = walletService.createWallet(walletDto);
  return ResponseEntity.status(wallet.isSuccess() ? 200 : 400).body(wallet);
 }


 /**
  * UPDATE SELECTED WALLET'S DETAILS
  */
 @Override
 @PutMapping("/{id}")
 public ResponseEntity<?> updateWallet(@PathVariable Long id, @RequestBody WalletDto walletDto) {
  ApiResponse response = walletService.updateWallet(id, walletDto);
  return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
 }






}
