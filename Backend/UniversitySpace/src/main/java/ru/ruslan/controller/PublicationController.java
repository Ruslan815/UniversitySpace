package ru.ruslan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.entity.Publication;
import ru.ruslan.entity.SecurityUser;
import ru.ruslan.service.PublicationService;
import ru.ruslan.service.UserService;

@Controller
public class PublicationController {

    @Autowired
    private final PublicationService publicationService;
    @Autowired
    private final UserService userService;

    public PublicationController(PublicationService publicationService, UserService userService) {
        this.publicationService = publicationService;
        this.userService = userService;
    }

    @GetMapping("/publications")
    public ResponseEntity<?>  getAllPublications() {
        return ResponseEntity.ok().body(publicationService.getAllPublications());
    }

    @GetMapping("/publication")
    public ResponseEntity<?> getPublicationById(@RequestParam Long publicationId) {
        return ResponseEntity.ok().body(publicationService.getPublicationById(publicationId));
    }

    @PostMapping("/publication")
    public ResponseEntity<?> createPublication(@RequestBody Publication somePublication) {
        if (somePublication.getAuthorId() == null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId;
            if (principal instanceof SecurityUser) {
                userId = ((SecurityUser) principal).getUserId();
            } else {
                userId = userService.findUserByUsername(principal.toString()).getId();
            }
            System.out.println(userId);
        }
        return ResponseEntity.ok().body(publicationService.createPublication(somePublication));
    }

    @PostMapping("/publication/update")
    public ResponseEntity<?> updatePublication(@RequestBody Publication somePublication) {
        return ResponseEntity.ok().body(publicationService.updatePublication(somePublication));
    }

    @PostMapping("/publication/delete")
    public ResponseEntity<?> deletePublicationById(@RequestParam Long publicationId) {
        return ResponseEntity.ok().body(publicationService.deletePublicationById(publicationId));
    }
}
