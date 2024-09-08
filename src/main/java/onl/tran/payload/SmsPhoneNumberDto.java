package onl.tran.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SmsPhoneNumberDto {

 private String phoneNumber;

 private String message;

}
