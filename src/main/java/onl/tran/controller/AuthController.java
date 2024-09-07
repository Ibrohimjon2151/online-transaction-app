package onl.tran.controller;

import lombok.RequiredArgsConstructor;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.AuthenticationDto;
import onl.tran.payload.LoginDto;
import onl.tran.payload.SmsPhoneNumberDto;
import onl.tran.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public final class AuthController implements AuthControllerInt {

 private final UserService userService;


 /**
  * CREATE USER ACCOUNT
  */
 @Override
 @PostMapping("/register")
 public ResponseEntity<ApiResponse> register(@RequestBody AuthenticationDto authenticationDto) {
  ApiResponse response = userService.createAccount(authenticationDto);
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }


 /**
  * CHECK ENTERED SMS CODE
  */
 @PostMapping("/sms")
 @Override
 public ResponseEntity<ApiResponse> checkSmsCode(@RequestBody SmsPhoneNumberDto smsDto) {
  ApiResponse response = userService.checkSmsCodeEnableUser(smsDto);
  return ResponseEntity.status(response.isSuccess() ? 200 : 403).body(response);
 }


 /**
  * LOGIN
  */
 @Override
 @PostMapping
 public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
  ApiResponse response = userService.login(loginDto);
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }
}
