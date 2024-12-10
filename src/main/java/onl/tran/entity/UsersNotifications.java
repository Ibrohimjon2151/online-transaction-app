package onl.tran.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import onl.tran.entity.template.AbstractEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UsersNotifications extends AbstractEntity {

  private boolean read;

  private LocalDateTime readTime;

  @ManyToOne
  private Notification notification;

  @ManyToOne
  private User user;

}
