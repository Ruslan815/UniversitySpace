package ru.ruslan.service.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.task.Task;
import ru.ruslan.entity.task.TaskComment;
import ru.ruslan.entity.user.User;
import ru.ruslan.repository.task.TaskRepository;
import ru.ruslan.service.task.TaskCommentService;
import ru.ruslan.service.user.UserService;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final TaskCommentService taskCommentService;

    public TaskService(TaskRepository taskRepository, UserService userService, TaskCommentService taskCommentService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.taskCommentService = taskCommentService;
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

    @Transactional
    public String resolveTask(Long taskId, Long taskCommentId) {
        Task task = getTaskById(taskId);
        task.setStatus(Task.TaskStatus.Resolved);
        task.setTaskCommentSolutionId(taskCommentId);
        taskRepository.save(task);

        long currentTimeInMillis = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = formatter.format(currentTimeInMillis);
        String deadline = task.getDeadline();

        TaskComment taskComment = taskCommentService.getTaskCommentById(taskCommentId);
        Long workerId = taskComment.getAuthorId();
        User worker = userService.findUserById(workerId);

        if (currentTime.compareTo(deadline) > 0) { // deadline is expired
            User taskOwner = userService.findUserById(task.getOwnerId());
            userService.depositToUserBalance(taskOwner, (double) task.getCost() / 2); // refund 50% of task cost
            userService.depositToUserBalance(worker, (double) task.getCost() / 2);
            userService.saveUser(taskOwner);
        } else {
            userService.depositToUserBalance(worker, (double) task.getCost());
        }
        userService.saveUser(worker);

        return "Task was resolved successful!";
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
