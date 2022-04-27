package ru.ruslan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.Task;
import ru.ruslan.repository.TaskRepository;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow();
    }

    public String createTask(Task someTask) {
        long currentTimeInMillis = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        someTask.setCreationTime(formatter.format(currentTimeInMillis));

        taskRepository.save(someTask);
        return "Task created successfully!";
    }

    public String updateTask(Task updatedTask) {
        taskRepository.save(updatedTask);
        return "Task updated successfully!";
    }

    public String deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
        return "Task deleted successfully!";
    }
}
