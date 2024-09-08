package onl.tran.controller;

import onl.tran.entity.Wallet;
import onl.tran.payload.WalletDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public sealed interface WalletControllerInt permits WalletController {

 ResponseEntity<?> createWallet(@RequestBody WalletDto walletDto);

 ResponseEntity<?> updateWallet(@PathVariable Long id, @RequestBody WalletDto walletDto);

 ResponseEntity<?> getUsersAllWallets();

 ResponseEntity<?> getSingleWallet(@PathVariable Long id);



}
