package onl.tran.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponseDto {

 private String name;

 private double balance;

 private String owner;

 private Long walletNumber;

 private boolean active;

 private Date expirationDate;

}
