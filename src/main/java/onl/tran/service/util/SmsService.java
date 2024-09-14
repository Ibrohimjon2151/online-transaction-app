package onl.tran.service.util;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import onl.tran.constants.AlertMessages;
import onl.tran.exceptions.WrongNumber;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.SmsPhoneNumberDto;
import onl.tran.payload.sms.dto.Destination;
import onl.tran.payload.sms.dto.Message;
import onl.tran.payload.sms.dto.MessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {
 @Value("${TWILIO_API_URL}")
 String API_URL;

 @Value("${TWILIO_AUTH_TOKEN}")
 String AUTH_TOKEN;

 @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
 String OUTGOING_SMS_NUMBER;


 public ApiResponse sendMessage(SmsPhoneNumberDto SmsPhoneNumberDto) throws WrongNumber {

  MessageRequest messageRequest = getMessageRequest(SmsPhoneNumberDto);
  Gson gson = new Gson();
  String message = gson.toJson(messageRequest);
  /**
   * SEND REQUEST TO SEND EXACT PHONE NUMBER
   */
  OkHttpClient client = new OkHttpClient().newBuilder()
   .build();
  MediaType mediaType = MediaType.parse("application/json");
  RequestBody body = RequestBody.create(mediaType, String.valueOf(message));//IT HAS TO BE CHANGED WITH MESSAGE TO WORK CORRECTLY


  Request request = new Request.Builder()
   .url(API_URL)
   .method("POST", body)
   .addHeader("Authorization", "App " + AUTH_TOKEN)
   .addHeader("Content-Type", "application/json")
   .addHeader("Accept", "application/json")
   .build();
  try (Response response = client.newCall(request).execute()) {
   if (!response.isSuccessful()) {
    throw new WrongNumber();
   }
   return new ApiResponse(AlertMessages.SUCCESSFULLY_SENT_SMS, true, response);
  } catch (IOException e) {
   throw new WrongNumber();
  }

 }

 /**
  * GENERATE SMS MESSAGE
  */
 private MessageRequest getMessageRequest(SmsPhoneNumberDto SmsPhoneNumberDto) {
  MessageRequest messageRequest = new MessageRequest();
  Destination destination = new Destination();
  destination.setTo(SmsPhoneNumberDto.getPhoneNumber());

  List<Message> messages = new ArrayList<>();
  List<Destination> destinations = new ArrayList<>();
  destinations.add(destination);

  Message message = new Message();
  message.setFrom(OUTGOING_SMS_NUMBER);
  message.setText(SmsPhoneNumberDto.getMessage());
  message.setDestinations(destinations);
  messages.add(message);

  messageRequest.setMessages(messages);
  return messageRequest;
 }
}
