package ru.ruslan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.ruslan.service.PublicationService;

@Controller
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }


    @GetMapping("/publications")
    public ResponseEntity<?>  getAllPublications() {
        /*Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof SecurityUser) {
            username = ((SecurityUser) principal).getUsername();
        } else {
            username = principal.toString();
        }
        System.out.println(username);*/

        return ResponseEntity.ok().body(publicationService.getAllPublications());
    }

    @GetMapping("/publication/{id}")
    public ResponseEntity<?> getPublication(@PathVariable Long id) {
        return ResponseEntity.ok().body(publicationService.getPublicationById(id));
    }
}
