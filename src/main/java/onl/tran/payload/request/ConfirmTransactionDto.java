package onl.tran.payload.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmTransactionDto {

 private Long transactionId;

 private int smsCode;
}
