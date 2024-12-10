package onl.tran.service.util.quarts;

import onl.tran.entity.task.TaskRecord;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskQuartzJob implements Job {


  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
    Object o = jobDataMap.get("TASK_RECORD");
    if (o instanceof TaskRecord taskRecord) {
      System.out.println("Job is running: " + taskRecord);



    }
  }
}
