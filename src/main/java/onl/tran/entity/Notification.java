package onl.tran.entity;

import jakarta.persistence.*;
import onl.tran.constants.NotifyType;
import onl.tran.entity.template.AbstractEntity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends AbstractEntity {

  @Enumerated(EnumType.STRING)
  private NotifyType notifyType;

  private String title;

  private Object content;

  @OneToMany
  private List<UsersNotifications> recipients;


}
