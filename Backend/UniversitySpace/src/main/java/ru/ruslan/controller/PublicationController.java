package ru.ruslan.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ruslan.entity.SecurityUser;

@Controller
public class PublicationController {

    @GetMapping("/publication")
    public String getAllPublications(@RequestParam String pageNumber) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof SecurityUser) {
            username = ((SecurityUser) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println(username);
        return "html/test";
    }

    @GetMapping("/publication/{id}")
    public String getPublication(@PathVariable String id, @RequestParam String pageNumber) {
        // Get Publication with specified ID
        return "html/test";
    }
}
