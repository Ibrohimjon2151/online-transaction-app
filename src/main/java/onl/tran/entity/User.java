package onl.tran.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import onl.tran.entity.template.AbstractEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity {

 @Column(nullable = false)
 private String firstName;

 private String lastName;

 @Column(nullable = false, unique = true)
 private String phoneNumber;

 private String password;

 private String temporarySmsCode;

}
