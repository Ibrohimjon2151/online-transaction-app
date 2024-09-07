package onl.tran.repository;

import onl.tran.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

 boolean existsByWalletNumber(String randomNumber);//CHECK WALLET NUMBER EXISTS
}
