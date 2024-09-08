package onl.tran.payload;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

 @NotNull
 private Long sourceWalletNumber;

 @NotNull
 private Long destinationWalletNumber;

 @NotNull
 private double amount;

}
