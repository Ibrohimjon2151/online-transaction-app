package onl.tran.service;

import jakarta.annotation.PostConstruct;
import onl.tran.repository.TransactionRepository;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ScheduledFuture;

@Service
public class TransactionDeleteScheduler {

 private final TaskScheduler taskScheduler;
 private final TransactionRepository transactionRepository;

 public TransactionDeleteScheduler(TaskScheduler taskScheduler, TransactionRepository transactionRepository) {
  this.taskScheduler = taskScheduler;
  this.transactionRepository = transactionRepository;
 }
 @PostConstruct
 public void init() {
 }

 public void scheduleDeletion(Long transactionId) {
  ScheduledFuture<?> future = taskScheduler.schedule(() -> deleteTransaction(transactionId),
   Instant.now().plusSeconds(2 * 60)); // 2 minutes
 }

 private void deleteTransaction(Long transactionId) {
  System.out.println("Transaction has been deleted");
  transactionRepository.deleteById(transactionId);
 }
}