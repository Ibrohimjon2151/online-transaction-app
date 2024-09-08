package onl.tran.controller;


import lombok.RequiredArgsConstructor;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.request.LoginDto;
import onl.tran.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public final class UserController implements UserControllerInt {

 private final UserService userService;

 @PostMapping("/changePassword")
 @Override
 public ResponseEntity<ApiResponse> changePassword(@RequestBody LoginDto loginDto) {
  ApiResponse response = userService.changePassword(loginDto);
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }
}
