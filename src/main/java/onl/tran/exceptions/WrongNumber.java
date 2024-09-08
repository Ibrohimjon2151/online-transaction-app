package onl.tran.exceptions;

import onl.tran.constants.AlertMessages;
import onl.tran.constants.ErrorMessages;

import java.io.IOException;

public class WrongNumber extends IOException {


 /**
  * EXCEPTION FOR ALERT WHEN ERROR OCCURRED WHILE SENDING MESSAGE
  */
 @Override
 public String getMessage() {
  return ErrorMessages.CANNOT_SEND_MESSAGE;
 }


}
