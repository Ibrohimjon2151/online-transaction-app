package onl.tran.TEST;

import lombok.RequiredArgsConstructor;
import onl.tran.TEST.model.User;
import onl.tran.exceptions.WrongNumber;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class TestController {

  static User [] users = new User[3];
  static {

    users[1] = new User("Ali", LocalDate.now().minusYears(15));
    users[2] = new User("Alijon", LocalDate.now().minusYears(65));
    users[3] = new User("Alimuhammad", LocalDate.now().minusYears(36));

  }


  @GetMapping("test-api")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Hello World");
  }

  @GetMapping("test-single-name/{id}")
  public ResponseEntity<User> getSingleData(@PathVariable int id) throws WrongNumber {
    User user;
    try{
      user = users[id];
    } catch (Exception e) {
      throw new WrongNumber();
    }

    return ResponseEntity.ok(user);
  }
}
