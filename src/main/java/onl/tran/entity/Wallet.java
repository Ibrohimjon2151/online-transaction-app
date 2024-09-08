package onl.tran.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import onl.tran.entity.template.AbstractEntity;

import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Wallet extends AbstractEntity {

 @Column(nullable = false)
 private String name;

 @Column(nullable = false,length = 16)
 private Long walletNumber;

 private double balance = 50000000D;

 @Column(nullable = false)
 private boolean active = true;

 @Column(nullable = false)
 private Date expireDate;

 @ManyToOne(fetch = FetchType.EAGER)
 @JoinColumn(name = "owner_id", nullable = false)  // Use @JoinColumn for foreign keys
 private User owner;

 @OneToMany(mappedBy = "sourceWallet")
 private Set<Transaction> transactionsAsSource;

 @OneToMany(mappedBy = "destinationWallet")
 private Set<Transaction> transactionsAsDestination;

}
