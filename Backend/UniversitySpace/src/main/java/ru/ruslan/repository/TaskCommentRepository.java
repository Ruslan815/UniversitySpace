package ru.ruslan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.TaskComment;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    TaskComment findByTaskId(Long taskId);
}
