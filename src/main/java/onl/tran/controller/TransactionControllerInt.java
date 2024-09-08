package onl.tran.controller;

import onl.tran.exceptions.WrongNumber;
import onl.tran.payload.TransactionDto;
import onl.tran.payload.request.ConfirmTransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public sealed interface TransactionControllerInt permits TransactionController {

 ResponseEntity<?> performTransaction(@RequestBody TransactionDto transactionDto);

 ResponseEntity<?> approveTransaction(@RequestParam Long id) throws WrongNumber;

 ResponseEntity<?> confirmTransaction(@RequestBody ConfirmTransactionDto confirmTransactionDto);

 ResponseEntity<?> cancelTransaction(@PathVariable Long id);

}
