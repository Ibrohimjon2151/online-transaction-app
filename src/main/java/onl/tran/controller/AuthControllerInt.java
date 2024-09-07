package onl.tran.controller;

import onl.tran.payload.ApiResponse;
import onl.tran.payload.AuthenticationDto;
import onl.tran.payload.LoginDto;
import onl.tran.payload.SmsPhoneNumberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public sealed interface AuthControllerInt permits AuthController{

 ResponseEntity<ApiResponse> register(@RequestBody AuthenticationDto authenticationDto);

 ResponseEntity<ApiResponse> checkSmsCode(@RequestBody SmsPhoneNumberDto smsDto);

 ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto);
}
