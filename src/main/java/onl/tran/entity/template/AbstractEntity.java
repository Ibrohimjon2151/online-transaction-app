package onl.tran.entity.template;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class AbstractEntity {

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
 private Long id;

 private Date creationDate;

 private Date lastUpdatedDate;


 @PrePersist
 protected void onCreate() {
  creationDate = new Date(System.currentTimeMillis());
  lastUpdatedDate = new Date(System.currentTimeMillis());
 }

 @PreUpdate
 protected void onUpdate() {
  lastUpdatedDate = new Date(System.currentTimeMillis());
 }
}
