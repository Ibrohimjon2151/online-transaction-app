package onl.tran.service.notification;

import onl.tran.constants.NotifyType;
import onl.tran.entity.Notification;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.NotifyDto;
import onl.tran.payload.UserNotifyDto;
import onl.tran.repository.notification.NotificationRepository;
import onl.tran.service.util.CurrentUserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {


  private final NotificationRepository notificationRepository;
  private final UserNotificationsService userNotificationsService;

  public NotificationService(NotificationRepository notificationRepository, UserNotificationsService userNotificationsService) {
    this.notificationRepository = notificationRepository;
    this.userNotificationsService = userNotificationsService;
  }

  public void createUserTaskNotification(NotifyDto notifyDto) {

    var notification = new Notification();
    notification.setTitle(notifyDto.title());
    notification.setContent(notifyDto.content());
    notification.setNotifyType(NotifyType.TASKS);
    notificationRepository.save(notification);


    var currentUserId = List.of(CurrentUserDetails.getCurrentUserId());
    var userNotifyDto = new UserNotifyDto(notification,currentUserId);    
    userNotificationsService.setUserNotification(userNotifyDto);

  }

  public ApiResponse getCurrentUserNotifications() {


    return null;
  }
}
