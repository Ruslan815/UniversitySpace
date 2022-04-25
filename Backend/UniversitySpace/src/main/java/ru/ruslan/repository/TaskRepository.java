package ru.ruslan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
