package onl.tran.entity.task;


import jakarta.persistence.Entity;
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
public class TaskRecord extends AbstractEntity {

  private LocalDateTime deadline;

  @OneToOne
  private Task task;
}
