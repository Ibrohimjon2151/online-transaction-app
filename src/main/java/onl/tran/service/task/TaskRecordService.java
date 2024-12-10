package onl.tran.service.task;

import onl.tran.entity.task.Task;
import onl.tran.entity.task.TaskRecord;
import onl.tran.exceptions.ExceptionInRunningJob;
import onl.tran.repository.TaskRecordRepository;
import onl.tran.service.util.quarts.QuartzService;
import org.springframework.stereotype.Service;

@Service
public class TaskRecordService {

  private final TaskRecordRepository taskRecordRepository;
  private final QuartzService quartzService;

  public TaskRecordService(TaskRecordRepository taskRecordRepository, QuartzService quartzService) {
    this.taskRecordRepository = taskRecordRepository;
    this.quartzService = quartzService;
  }

  public void addRecord(Task newTask) throws ExceptionInRunningJob {

    TaskRecord record = new TaskRecord();
    record.setTask(newTask);
    record.setDeadline(newTask.getDeadline());
    taskRecordRepository.save(record);
    
    quartzService.createTaskJob(record);

  }
}
