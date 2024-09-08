package onl.tran.component;

import jakarta.persistence.PreUpdate;
import onl.tran.constants.TransactionState;
import onl.tran.entity.Transaction;
import onl.tran.service.TransactionDeleteScheduler;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

 private final TransactionDeleteScheduler deleteScheduler;

 public TransactionListener(@Lazy TransactionDeleteScheduler deleteScheduler) {
  this.deleteScheduler = deleteScheduler;
 }

 @PreUpdate
 public void preUpdate(Transaction transaction) {
  if (transaction.getState().equals(TransactionState.CANCELED)) {
   deleteScheduler.scheduleDeletion(transaction.getId());
   System.out.println("Transaction " + transaction.getId() + " has been canceled");
  }
 }
}