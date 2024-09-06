package onl.tran.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import onl.tran.entity.template.AbstractEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Transaction extends AbstractEntity {

 @OneToOne
 private User transactor;

 @ManyToOne
 private Wallet sendWallet;

 @ManyToOne
 private Wallet recieveWallet;

 private double amount;

}
