package ru.ruslan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.ruslan.entity.Publication;
import ru.ruslan.service.PublicationService;

@Controller
public class PublicationController {

    @Autowired
    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
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
