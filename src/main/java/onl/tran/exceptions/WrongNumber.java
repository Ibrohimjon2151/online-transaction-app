package onl.tran.exceptions;

import onl.tran.constants.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongNumber extends IOException {


 /**
  * EXCEPTION FOR ALERT WHEN ERROR OCCURRED WHILE SENDING MESSAGE
  */
 @Override
 public String getMessage() {
  return ErrorMessages.CANNOT_SEND_MESSAGE;
 }


}
