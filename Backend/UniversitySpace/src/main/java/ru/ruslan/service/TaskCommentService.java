package ru.ruslan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ruslan.entity.TaskComment;
import ru.ruslan.repository.TaskCommentRepository;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TaskCommentService {

    @Autowired
    private final TaskCommentRepository taskCommentRepository;

    public TaskCommentService(TaskCommentRepository taskCommentRepository) {
        this.taskCommentRepository = taskCommentRepository;
    }

    public List<TaskComment> getAllTaskComments() {
        return taskCommentRepository.findAll();
    }

    public TaskComment getTaskCommentById(Long taskCommentId) {
        return taskCommentRepository.findById(taskCommentId).orElseThrow();
    }

    public TaskComment getTaskCommentByTaskId(Long taskId) {
        return taskCommentRepository.findByTaskId(taskId);
    }

    public String createTaskComment(TaskComment someTaskComment) {
        long currentTimeInMillis = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        someTaskComment.setCreationTime(formatter.format(currentTimeInMillis));

        taskCommentRepository.save(someTaskComment);
        return "TaskComment created successfully!";
    }

    public String updateTaskComment(TaskComment taskComment) {
        taskCommentRepository.save(taskComment);
        return "TaskComment updated successfully!";
    }

    public String deleteTaskCommentById(Long taskCommentId) {
        taskCommentRepository.deleteById(taskCommentId);
        return "TaskComment deleted successfully!";
    }
}
