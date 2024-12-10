package onl.tran.service.util.quarts;

import lombok.RequiredArgsConstructor;
import onl.tran.entity.task.TaskRecord;
import onl.tran.exceptions.ExceptionInRunningJob;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class QuartzService {


  private final Scheduler scheduler;

  public void createTaskJob(TaskRecord taskRecord) throws ExceptionInRunningJob {

    JobDataMap jobDataMap = new JobDataMap();
    jobDataMap.put("TASK_RECORD", taskRecord);

    JobDetail jobDetail = JobBuilder.newJob(TaskQuartzJob.class)
      .withIdentity(String.valueOf(taskRecord.getId()))
      .setJobData(jobDataMap)
      .build();


    Trigger trigger = TriggerBuilder.newTrigger()
      .forJob(jobDetail)
      .withIdentity(String.valueOf(taskRecord.getId()))
      .startAt(Date.from(taskRecord.getDeadline().atZone(ZoneId.systemDefault()).toInstant()))
      .build();

    try {
      
      scheduler.scheduleJob(jobDetail,trigger);
      
    } catch (SchedulerException e) {
      throw new ExceptionInRunningJob(e.getMessage());
    }
  }


}