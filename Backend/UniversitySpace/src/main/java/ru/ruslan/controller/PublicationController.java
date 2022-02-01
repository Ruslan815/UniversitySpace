package ru.ruslan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicationController {

    @GetMapping("/publication")
    public String getAllPublications(@RequestParam String pageNumber) {
        return "html/test";
    }

    @GetMapping("/publication/{id}")
    public String getPublication(@PathVariable String id, @RequestParam String pageNumber) {
        // Get Publication with specified ID
        return "html/test";
    }
}
