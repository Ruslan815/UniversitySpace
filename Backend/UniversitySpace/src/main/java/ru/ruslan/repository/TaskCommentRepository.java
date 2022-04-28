package ru.ruslan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.TaskComment;

import java.util.List;

public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    TaskComment findByTaskId(Long taskId);
    List<TaskComment> findAllByTaskId(Long taskId);
}
