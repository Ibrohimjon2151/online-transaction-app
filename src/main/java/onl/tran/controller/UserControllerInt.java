package onl.tran.controller;


import onl.tran.payload.ApiResponse;
import onl.tran.payload.request.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public sealed interface UserControllerInt permits UserController{
 ResponseEntity<ApiResponse> changePassword(@RequestBody LoginDto loginDto);
}
