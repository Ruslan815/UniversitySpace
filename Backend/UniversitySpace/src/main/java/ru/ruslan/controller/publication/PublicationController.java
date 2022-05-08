package ru.ruslan.controller.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.entity.publication.Publication;
import ru.ruslan.entity.user.SecurityUser;
import ru.ruslan.service.publication.PublicationService;
import ru.ruslan.service.user.UserService;

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

    @GetMapping("/api/publications")
    public ResponseEntity<?> getAllPublications() {
        return ResponseEntity.ok().body(publicationService.getAllPublications());
    }

    @GetMapping("/api/publications/user")
    public ResponseEntity<?> getAllPublicationsByAuthorUsername(@RequestParam String username) {
        Long userId = userService.getUserIdByUsername(username);
        return ResponseEntity.ok().body(publicationService.getAllPublicationsByAuthorId(userId));
    }

    @GetMapping("/publications")
    public String getAllPublicationsPage() {
        return "html/publication/allPublicationsPage.html";
    }

    @GetMapping("/api/publication")
    public ResponseEntity<?> getPublicationById(@RequestParam Long publicationId) {
        return ResponseEntity.ok().body(publicationService.getPublicationById(publicationId));
    }

    @GetMapping("/publication")
    public String getPublicationByIdPage(@RequestParam Long publicationId) {
        return "html/publication/publicationPage.html";
    }

    @PostMapping("/api/publication")
    public ResponseEntity<?> createPublication(@RequestBody Publication somePublication) {
        try {
            return ResponseEntity.ok().body(publicationService.createPublication(somePublication));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/createPublication")
    public String getCreatePublicationPage() {
        return "html/publication/newPublicationPage.html";
    }

    @PostMapping("/api/publication/update")
    public ResponseEntity<?> updatePublication(@RequestBody Publication somePublication) {
        return ResponseEntity.ok().body(publicationService.updatePublication(somePublication));
    }

    @PostMapping("/api/publication/delete")
    public ResponseEntity<?> deletePublicationById(@RequestParam Long publicationId) {
        return ResponseEntity.ok().body(publicationService.deletePublicationById(publicationId));
    }
}
