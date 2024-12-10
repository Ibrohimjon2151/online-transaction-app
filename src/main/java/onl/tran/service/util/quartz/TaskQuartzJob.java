package onl.tran.service.util.quartz;

import onl.tran.entity.task.TaskRecord;
import onl.tran.payload.NotifyDto;
import onl.tran.service.notification.NotificationService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class TaskQuartzJob implements Job {


  private final NotificationService notificationService;

  public TaskQuartzJob(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    Object o = jobDataMap.get("TASK_RECORD");
    if (o instanceof TaskRecord taskRecord) {
      NotifyDto notifyDto = new NotifyDto(
        taskRecord.getTask().getTitle(),
        taskRecord.getTask().getId()
      );
      notificationService.createUserTaskNotification(notifyDto);
    }
  }
}
