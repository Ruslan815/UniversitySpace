package ru.ruslan.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.task.TaskComment;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    List<TaskComment> findAllByTaskId(Long taskId);
    void deleteAllByTaskId(Long taskId);
}
