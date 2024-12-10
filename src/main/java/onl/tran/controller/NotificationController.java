package onl.tran.controller;

import onl.tran.entity.Notification;
import onl.tran.payload.ApiResponse;
import onl.tran.service.notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notification")
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping
  ResponseEntity<ApiResponse> getNotifications() {
    notificationService.getCurrentUserNotifications();
  };

}
