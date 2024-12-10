package onl.tran.service.task;

import lombok.RequiredArgsConstructor;
import onl.tran.entity.task.Task;
import onl.tran.exceptions.ExceptionInRunningJob;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.TaskDto;
import onl.tran.repository.task.TaskRepository;
import onl.tran.service.util.CurrentUserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository taskRepository;
  private final TaskRecordService taskRecordService;



  public ApiResponse createTask(TaskDto task) throws ExceptionInRunningJob {

    Task newTask = new Task();
    newTask.setCompleted(false);
    newTask.setTitle(task.title());
    newTask.setDeadline(task.deadLine());
    newTask.setDescription(task.description());
    newTask.setOwner(CurrentUserDetails.getCurrentUser());
    taskRepository.save(newTask);


    // CREATE RECORD FOR TASK
    taskRecordService.addRecord(newTask);
    

    return new ApiResponse(true, "Task created");
  }

}
