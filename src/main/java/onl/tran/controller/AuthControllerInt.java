package onl.tran.controller;

import jakarta.servlet.http.HttpServletResponse;
import onl.tran.exceptions.WrongNumber;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.request.AuthenticationDto;
import onl.tran.payload.request.LoginDto;
import onl.tran.payload.SmsPhoneNumberDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public sealed interface AuthControllerInt permits AuthController{

 ResponseEntity<ApiResponse> register(@RequestBody AuthenticationDto authenticationDto);

 ResponseEntity<ApiResponse> verifySmsCode(@RequestBody SmsPhoneNumberDto smsDto, HttpServletResponse httpServletResponse);

 ResponseEntity<ApiResponse> login(@RequestBody LoginDto loginDto);


 ResponseEntity<ApiResponse> resetPassword(@RequestParam String phoneNumber) throws WrongNumber;


}
