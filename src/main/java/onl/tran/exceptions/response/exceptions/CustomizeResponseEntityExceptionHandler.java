package onl.tran.exceptions.response.exceptions;

import onl.tran.exceptions.WrongNumber;
import onl.tran.exceptions.response.exceptions.dto.ErrorDetails;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
    var error = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(WrongNumber.class)
  public final ResponseEntity<ErrorDetails> handleWrongNumberExceptions(Exception ex, WebRequest request) {
    var error = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

    //TO GET CUSTOM EXCEPTION'S EXACT ERROR STATUS
    HttpStatus status = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class).value();

    return new ResponseEntity<>(error, status);
  }


}
