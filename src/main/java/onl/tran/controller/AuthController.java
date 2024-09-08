package onl.tran.controller;

import lombok.RequiredArgsConstructor;
import onl.tran.exceptions.WrongNumber;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.request.AuthenticationDto;
import onl.tran.payload.request.LoginDto;
import onl.tran.payload.SmsPhoneNumberDto;
import onl.tran.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  * VERIFY ENTERED SMS CODE
  */
 @PostMapping("/verify")
 @Override
 public ResponseEntity<ApiResponse> verifySmsCode(@RequestBody SmsPhoneNumberDto smsDto) {
  ApiResponse response = userService.verifySmsCodeEnableUser(smsDto);
  return ResponseEntity.status(response.isSuccess() ? 200 : 403).body(response);
 }


 /**
  * LOGIN
  */
 @Override
 @PostMapping("/login")
 public ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto) {
  ApiResponse response = userService.login(loginDto);
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }


 /**
  * METHOD TO RESET PASSWORD AND SEND AGAIN SMS CODE AFTER TWO MINUTE
  */
 @Override
 @PostMapping("resetPassword")
 public ResponseEntity<ApiResponse> resetPassword(@RequestParam String phoneNumber) throws WrongNumber {
  ApiResponse response = userService.sendSmsCodeVerifyPhone(phoneNumber);
  return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
 }


}
