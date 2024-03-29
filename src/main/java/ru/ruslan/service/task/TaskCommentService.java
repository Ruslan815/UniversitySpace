package ru.ruslan.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.task.TaskComment;
import ru.ruslan.repository.task.TaskCommentRepository;
import ru.ruslan.service.user.SecurityUserService;
import ru.ruslan.service.user.UserService;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TaskCommentService {

    @Autowired
    private final TaskCommentRepository taskCommentRepository;
    @Autowired
    private final UserService userService;

    public TaskCommentService(TaskCommentRepository taskCommentRepository, UserService userService) {
        this.taskCommentRepository = taskCommentRepository;
        this.userService = userService;
    }

    public TaskComment getTaskCommentById(Long taskCommentId) {
        return taskCommentRepository.findById(taskCommentId).orElseThrow();
    }

    public List<TaskComment> getAllTaskCommentsByTaskId(Long taskId) {
        return taskCommentRepository.findAllByTaskId(taskId);
    }

    public String createTaskComment(TaskComment someTaskComment) throws Exception {
        someTaskComment.setAuthorId(SecurityUserService.getCurrentUserId());
        String authorUsername = userService.findUserById(someTaskComment.getAuthorId()).getUsername();
        someTaskComment.setAuthorUsername(authorUsername);

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

    @Transactional // transaction is necessary
    public void deleteAllByTaskId(Long taskId) {
        taskCommentRepository.deleteAllByTaskId(taskId);
    }
}
