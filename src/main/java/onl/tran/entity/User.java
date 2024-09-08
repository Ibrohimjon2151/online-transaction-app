package onl.tran.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.*;
import onl.tran.entity.template.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity implements UserDetails {

 @Column(nullable = false)
 private String firstName;

 private String lastName;

 @Column(nullable = false, unique = true)
 @Size(min = 7, max = 13)
 private String phoneNumber;

 @Column(nullable = false)
 private String password;

 private Integer temporarySmsCode;

 @Enumerated(EnumType.STRING)
 Role role;

 private boolean enabled = false;

 private Date expirationTime;

 @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
  return List.of(new SimpleGrantedAuthority(role.name()));
 }

 @Override
 public String getUsername() {
  return phoneNumber;
 }

 @Override
 public boolean isAccountNonExpired() {
  return true;
 }

 @Override
 public boolean isAccountNonLocked() {
  return true;
 }

 @Override
 public boolean isCredentialsNonExpired() {
  return true;
 }

 @Override
 public boolean isEnabled() {
  return this.enabled;
 }
 @Override
 public String getPassword() {
  return this.password;
 }
}
