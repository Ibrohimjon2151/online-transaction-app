package onl.tran.controller;

import lombok.RequiredArgsConstructor;
import onl.tran.exceptions.IllegalTransaction;
import onl.tran.exceptions.WrongNumber;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.TransactionDto;
import onl.tran.payload.request.ConfirmTransactionDto;
import onl.tran.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transaction")
@RequiredArgsConstructor
public final class TransactionController implements TransactionControllerInt {

 private final TransactionService transactionService;


 /**
  * PERFORM TRANSACTION BY CURRENT USER
  */
 @Override
 @PostMapping("/perform")
 public ResponseEntity<?> performTransaction(@RequestBody TransactionDto transactionDto) {
  ApiResponse response = null;
  try {
   response = transactionService.performTransaction(transactionDto);
  } catch (IllegalTransaction e) {
   response = new ApiResponse(e.getMessage(),false);
  }
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }


 /**
  * APPROVE TRANSACTION
  */
 @PostMapping("/approve")
 @Override
 public ResponseEntity<?> approveTransaction(@RequestParam Long id) throws WrongNumber {
  ApiResponse response = transactionService.approveTransaction(id);
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }

 /**
  * CONFIRM TRANSACTION CHECK ENTERED SMS CODE
  */
 @PostMapping("/confirm")
 @Override
 public ResponseEntity<?> confirmTransaction(@RequestBody ConfirmTransactionDto confirmTransactionDto) {
  ApiResponse response = transactionService.confirmTransaction(confirmTransactionDto);
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }

 /**
  * CANCEL TRANSACTION AND DELETE AFTER 2 MINUTES
  */
 @Override
 @PutMapping("/cancel/{id}")
 public ResponseEntity<?> cancelTransaction(@PathVariable Long id) {
  ApiResponse response = transactionService.cancelTransaction(id);
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }
}
