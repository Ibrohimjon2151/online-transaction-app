package onl.tran.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsPhoneNumberDto {

 private String phoneNumber;

 private String message;

}
