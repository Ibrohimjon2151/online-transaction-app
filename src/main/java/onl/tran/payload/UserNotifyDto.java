package onl.tran.payload;

import onl.tran.entity.Notification;

import java.util.List;

public record UserNotifyDto(Notification notification, List<Long> userIds) {
}
