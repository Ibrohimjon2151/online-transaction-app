package onl.tran.repository;

import onl.tran.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

 boolean existsByWalletNumber(Long randomNumber);//CHECK WALLET NUMBER EXISTS

 List<Wallet> findByActiveAndOwner_Id(boolean active, Long owner_id);

 Optional<Wallet> findByIdAndOwner_Id(Long id, Long owner_id);

 Optional<Wallet> findByWalletNumberAndActive(Long walletNumber, boolean active);

 Optional<Wallet> findByWalletNumberAndActiveAndOwner_Id(Long walletNumber, boolean active, Long owner_id);

}
