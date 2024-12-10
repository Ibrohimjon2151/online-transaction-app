package onl.tran.repository.notification;

import onl.tran.entity.Notification;
import onl.tran.entity.UserNotifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserNotificationsRepository extends JpaRepository<UserNotifications, Long> {

}
