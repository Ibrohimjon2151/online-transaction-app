package onl.tran.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class Wallet extends AbstractEntity {

 @Column(nullable = false)
 private String name;

 @Column(nullable = false,length = 16)
 private String walletNumber;

 private double balance;

 @Column(nullable = false)
 private boolean active = true;

 @ManyToOne
 private User owner;

}
