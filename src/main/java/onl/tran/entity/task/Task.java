package onl.tran.entity.task;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import onl.tran.entity.User;
import onl.tran.entity.template.AbstractEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Task extends AbstractEntity {

  private String title;

  private String description;

  private LocalDateTime deadline;

  private boolean completed;

  @ManyToOne
  private User owner;

}
