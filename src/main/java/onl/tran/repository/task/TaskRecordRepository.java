package onl.tran.repository.task;

import onl.tran.entity.task.TaskRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRecordRepository extends JpaRepository<TaskRecord, Long> {

}
