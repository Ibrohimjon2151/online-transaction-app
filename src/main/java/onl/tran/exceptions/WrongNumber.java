package onl.tran.exceptions;

import onl.tran.constants.AlertMessages;

import java.io.IOException;

public class WrongNumber extends IOException {


 /**
  * EXCEPTION FOR ALERT WHEN ERROR OCCURRED WHILE SENDING MESSAGE
  */
 @Override
 public String getMessage() {
  return AlertMessages.CANNOT_SEND_MESSAGE;
 }


}
