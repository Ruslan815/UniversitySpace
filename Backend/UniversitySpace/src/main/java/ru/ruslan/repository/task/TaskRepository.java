package ru.ruslan.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.task.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOwnerId(Long ownerId);
}
