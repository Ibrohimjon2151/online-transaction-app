package onl.tran.repository;

import onl.tran.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

 Optional<Transaction> findByIdAndTransactor_Id(Long id, Long currentUserId);
}
