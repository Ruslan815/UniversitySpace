package ru.ruslan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.entity.Publication;
import ru.ruslan.entity.SecurityUser;
import ru.ruslan.entity.Task;
import ru.ruslan.service.TaskService;
import ru.ruslan.service.UserService;

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

        return ResponseEntity.ok().body(taskService.createTask(someTask));
    }

    @GetMapping("/createTask")
    public String getCreateTaskPage() {
        return "html/Task/newTaskPage.html";
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
