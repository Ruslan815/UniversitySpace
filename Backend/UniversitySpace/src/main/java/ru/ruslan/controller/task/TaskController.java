package ru.ruslan.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ruslan.entity.user.SecurityUser;
import ru.ruslan.entity.task.Task;
import ru.ruslan.entity.user.User;
import ru.ruslan.service.task.TaskService;
import ru.ruslan.service.user.UserService;

@Controller
public class TaskController {

    @Autowired
    private final TaskService taskService;
    @Autowired
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/api/tasks")
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @GetMapping("/tasks")
    public String getAllTasksPage() {
        return "html/Task/allTasksPage.html";
    }

    @GetMapping("/api/task")
    public ResponseEntity<?> getTaskById(@RequestParam Long taskId) {
        return ResponseEntity.ok().body(taskService.getTaskById(taskId));
    }

    @GetMapping("/task")
    public String getTaskByIdPage(@RequestParam Long taskId) {
        return "html/Task/taskPage.html";
    }

    @Transactional
    @PostMapping("/api/task")
    public ResponseEntity<?> createTask(@RequestBody Task someTask) {
        if (someTask.getOwnerId() == null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId;
            if (principal instanceof SecurityUser) {
                userId = ((SecurityUser) principal).getUserId();
            } else {
                userId = userService.findUserByUsername(principal.toString()).getId();
            }
            System.out.println(userId);
            someTask.setOwnerId(userId);
        }

        User someUser = userService.findUserById(someTask.getOwnerId());
        double userBalance = someUser.getBalance();
        double taskCost = someTask.getCost();
        if (userBalance < taskCost) {
            return ResponseEntity.status(500).body("User don't have enough size of balance for this task cost!");
        }

        userService.withdrawFromUserBalance(someUser, taskCost);

        return ResponseEntity.ok().body(taskService.createTask(someTask));
    }

    @GetMapping("/createTask")
    public String getCreateTaskPage() {
        return "html/Task/newTaskPage.html";
    }

    @Transactional
    @PostMapping("/api/task/resolve")
    public ResponseEntity<?> resolveTask(@RequestParam Long taskId, @RequestParam Long taskCommentId) {
        return ResponseEntity.ok().body(taskService.resolveTask(taskId, taskCommentId));
    }

    @PostMapping("/api/task/update")
    public ResponseEntity<?> updateTask(@RequestBody Task someTask) {
        return ResponseEntity.ok().body(taskService.updateTask(someTask));
    }

    @PostMapping("/api/task/delete")
    public ResponseEntity<?> deleteTaskById(@RequestParam Long taskId) {
        return ResponseEntity.ok().body(taskService.deleteTaskById(taskId));
    }
}
