package onl.tran.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import onl.tran.component.TransactionListener;
import onl.tran.constants.TransactionState;
import onl.tran.entity.template.AbstractEntity;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(TransactionListener.class)
public class Transaction extends AbstractEntity {

 @ManyToOne
 private User transactor;

 @ManyToOne
 private Wallet sourceWallet;

 @ManyToOne
 private Wallet destinationWallet;

 @Column(nullable = false)
 @Enumerated(EnumType.STRING)
 TransactionState state;

 @Column(nullable = false)
 private double amount;

 private int confirmationCode;

 private Date expirationDate;
}
