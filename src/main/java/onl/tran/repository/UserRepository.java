package onl.tran.repository;

import jakarta.validation.constraints.Size;
import onl.tran.entity.User;
import onl.tran.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

 Optional<User> findByPhoneNumber(@Size(min = 7, max = 13) String phoneNumber);

 List<User> findAllByIdIn(List<Long> ids);


}
