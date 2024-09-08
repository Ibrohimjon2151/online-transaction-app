package onl.tran.exceptions;

import onl.tran.constants.ErrorMessages;

public class IllegalTransaction extends Exception {

 /**
  * EXCEPTION FOR ALERT WHEN ERROR OCCURRED WHILE TRANSACTION
  */
 @Override
 public String getMessage() {
  return ErrorMessages.ILLEGAL_WALLET_NUMBER;
 }

}
