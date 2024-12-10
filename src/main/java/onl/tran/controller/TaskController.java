package onl.tran.controller;


import lombok.RequiredArgsConstructor;
import onl.tran.entity.task.Task;
import onl.tran.exceptions.ExceptionInRunningJob;
import onl.tran.payload.ApiResponse;
import onl.tran.payload.TaskDto;
import onl.tran.service.task.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/task")
public final class TaskController {


  private final TaskService taskService;

  @PostMapping
  public ResponseEntity<ApiResponse> addTask(@RequestBody TaskDto task) throws ExceptionInRunningJob {
    ApiResponse response = taskService.createTask(task);
    return ResponseEntity.ok().body(response);
  }

}
