package ru.ruslan.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.task.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
