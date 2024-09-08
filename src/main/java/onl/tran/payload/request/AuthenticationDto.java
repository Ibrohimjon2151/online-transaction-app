package onl.tran.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDto {

 @NotNull
 private String phoneNumber;

 @NotNull
 private String password;

 @NotNull
 private String firstName;

 private String lastName;

}
