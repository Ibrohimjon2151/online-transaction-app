package onl.tran.TEST;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class TestController {

  @GetMapping("test-api")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("Hello World");
  }
}
