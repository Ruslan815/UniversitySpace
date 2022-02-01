package ru.ruslan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    @GetMapping("/task")
    public String getAllTasks(@RequestParam String pageNumber) {
        return "index";
    }

    @GetMapping("/task/{id}")
    public String getPublication(@PathVariable String id, @RequestParam String pageNumber) {
        // Get Task with specified ID
        return "index";
    }
}
