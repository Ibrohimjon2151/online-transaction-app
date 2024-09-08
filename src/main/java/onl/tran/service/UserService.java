package onl.tran.service;

import lombok.RequiredArgsConstructor;
import onl.tran.config.util.token.JwtService;
import onl.tran.constants.AlertMessages;
import onl.tran.entity.User;
import onl.tran.exceptions.WrongNumber;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.request.AuthenticationDto;
import onl.tran.payload.request.LoginDto;
import onl.tran.payload.SmsPhoneNumberDto;
import onl.tran.repository.UserRepository;
import onl.tran.service.util.GenerateWalletNumber;
import onl.tran.service.util.SmsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static onl.tran.constants.AlertMessages.CHANGED_SUCCESSFULLY;
import static onl.tran.entity.Role.USER;

@Service
@RequiredArgsConstructor
public class UserService {

 private final GenerateWalletNumber generateWalletNumber;
 private final SmsService smsService;
 private final PasswordEncoder passwordEncoder;
 private final UserRepository userRepository;
 private final JwtService jwtService;
 private final AuthenticationManager authenticationManager;


 public ApiResponse createAccount(AuthenticationDto authenticationDto) {

  User user = new User();

  int smsCode = generateWalletNumber.generateSixDigitNumber();

  Optional<User> optionalUser = userRepository.findByPhoneNumber(authenticationDto.getPhoneNumber());
  if (optionalUser.isPresent()) {
   return new ApiResponse(AlertMessages.ALREADY_EXISTED, false, null);
  }

  SmsPhoneNumberDto SmsPhoneNumberDto = new SmsPhoneNumberDto(authenticationDto.getPhoneNumber(), String.valueOf(smsCode));
  ApiResponse response = null;
  try {
   response = smsService.sendMessage(SmsPhoneNumberDto);
   if (true) {
    user.setRole(USER);
    user.setPhoneNumber(authenticationDto.getPhoneNumber());
    user.setPassword(passwordEncoder.encode(authenticationDto.getPassword()));
    user.setFirstName(authenticationDto.getFirstName());
    user.setLastName(authenticationDto.getLastName());

    user.setTemporarySmsCode(smsCode);
    //SET EXPIRATION TIME FOR SMS CODE
    user.setExpirationTime(new Date(System.currentTimeMillis() + (1000 * 120)));

    userRepository.save(user);
   }
   response.setData(smsCode);
   return response;
  } catch (WrongNumber e) {
   return new ApiResponse(e.getMessage(), false, null);
  }
 }

 public ApiResponse verifySmsCodeEnableUser(SmsPhoneNumberDto smsDto) {
  Optional<User> optionalUser = userRepository.findByPhoneNumber(smsDto.getPhoneNumber());
  if (optionalUser.isEmpty()) {
   return new ApiResponse(AlertMessages.USER_NOT_FOUND, false, null);
  } else {
   User user = optionalUser.get();
   if (user.getTemporarySmsCode() != Integer.parseInt(smsDto.getMessage())) {
    return new ApiResponse(AlertMessages.WRONG_SMS_CODE, false, null);
   } else {
    user.setTemporarySmsCode(null);
    userRepository.save(user);
    if (user.getExpirationTime().before(new Date(System.currentTimeMillis()))) {
     return new ApiResponse(AlertMessages.EXPIRED_SMS_CODE, false, null);
    }
    user.setEnabled(true);
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return new ApiResponse(AlertMessages.SUCCESSFULLY_ENTERED, true, jwtToken);
   }
  }
 }

 public ApiResponse login(LoginDto loginDto) {

  Authentication authenticate = authenticationManager.authenticate(
   new UsernamePasswordAuthenticationToken(
    loginDto.getPhoneNumber(),
    loginDto.getPassword()
   )
  );
  if (authenticate.isAuthenticated()) {
   var user = userRepository.findByPhoneNumber(loginDto.getPhoneNumber()).orElseThrow();
   var jwtToken = jwtService.generateToken(user);
   return new ApiResponse(AlertMessages.SUCCESSFULLY_ENTERED, true, jwtToken);
  }
  return new ApiResponse(AlertMessages.WRONG_LOGIN, false, null);
 }

 public ApiResponse changePassword(LoginDto loginDto) {
  Optional<User> optionalUser = userRepository.findByPhoneNumber(loginDto.getPhoneNumber());
  if (optionalUser.isEmpty()) {
   return new ApiResponse(AlertMessages.USER_NOT_FOUND, false, null);
  }
  User user = optionalUser.get();
  user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
  userRepository.save(user);
  return new ApiResponse(CHANGED_SUCCESSFULLY, true, null);
 }

 public ApiResponse sendSmsCodeVerifyPhone(String phoneNumber) throws WrongNumber {
  Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
  if (optionalUser.isEmpty()) {
   return new ApiResponse(AlertMessages.USER_NOT_FOUND, false, null);
  }
  User user = optionalUser.get();
  int smsCode = generateWalletNumber.generateSixDigitNumber();
  SmsPhoneNumberDto smsPhoneNumberDto = new SmsPhoneNumberDto(phoneNumber, String.valueOf(smsCode));
  ApiResponse response = smsService.sendMessage(smsPhoneNumberDto);
  if (!response.isSuccess()) {
   return response;
  }
  user.setTemporarySmsCode(smsCode);
  user.setExpirationTime(new Date(System.currentTimeMillis() + (1000 * 120)));
  userRepository.save(user);
  return new ApiResponse(AlertMessages.SUCCESSFULLY_SENT_SMS, true, null);
 }
}
