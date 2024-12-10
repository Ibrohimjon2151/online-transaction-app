package onl.tran.service.notification;

import onl.tran.entity.Notification;
import onl.tran.entity.User;
import onl.tran.entity.UserNotifications;
import onl.tran.payload.NotifyDto;
import onl.tran.payload.UserNotifyDto;
import onl.tran.repository.notification.UserNotificationsRepository;
import onl.tran.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNotificationsService {


  private final UserService userService;
  private final UserNotificationsRepository userNotificationsRepository;

  public UserNotificationsService(UserService userService, UserNotificationsRepository userNotificationsRepository) {
    this.userService = userService;
    this.userNotificationsRepository = userNotificationsRepository;
  }

  public void setUserNotification(UserNotifyDto userNotifyDto) {

    //GET USERS
    var usersById = userService.getSomeUsersById(userNotifyDto.userIds());

    usersById.forEach(user -> {
      UserNotifications userNotifications = new UserNotifications();
      userNotifications.setUser(user);
      userNotifications.setNotification(userNotifyDto.notification());
      userNotificationsRepository.save(userNotifications);
    });
  }

}
