package ru.ruslan.controller.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ruslan.entity.TaskComment;
import ru.ruslan.service.TaskCommentService;

@Controller
public class TaskCommentController {

    @Autowired
    private final TaskCommentService taskCommentService;

    public TaskCommentController(TaskCommentService taskCommentService) {
        this.taskCommentService = taskCommentService;
    }

    @PostMapping("/api/task/comment")
    public ResponseEntity<?> createTaskComment(@RequestBody TaskComment taskComment) {
        return ResponseEntity.ok().body(taskCommentService.createTaskComment(taskComment));
    }

    @GetMapping("/api/task/comments")
    public ResponseEntity<?> getAllTaskCommentsByTaskId(@RequestParam Long taskId) {
        return ResponseEntity.ok().body(taskCommentService.getAllTaskCommentsByTaskId(taskId));
    }

    @PostMapping("/api/task/comment/update")
    public ResponseEntity<?> updateTaskComment(@RequestBody TaskComment taskComment) {
        return ResponseEntity.ok().body(taskCommentService.updateTaskComment(taskComment));
    }

    @PostMapping("/api/task/comment/delete")
    public ResponseEntity<?> deleteTaskCommentById(@RequestParam Long taskCommentId) {
        return ResponseEntity.ok().body(taskCommentService.deleteTaskCommentById(taskCommentId));
    }
}
