package ru.ruslan.controller.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ruslan.entity.publication.PublicationComment;
import ru.ruslan.service.publication.PublicationCommentService;

@Controller
public class PublicationCommentController {
    @Autowired
    private final PublicationCommentService publicationCommentService;

    public PublicationCommentController(PublicationCommentService publicationCommentService) {
        this.publicationCommentService = publicationCommentService;
    }

    @PostMapping("/api/publication/comment")
    public ResponseEntity<?> createPublicationComment(@RequestBody PublicationComment publicationComment) {
        return ResponseEntity.ok().body(publicationCommentService.createPublicationComment(publicationComment));
    }

    @GetMapping("/api/publication/comments")
    public ResponseEntity<?> getAllPublicationCommentsByPublicationId(@RequestParam Long publicationId) {
        return ResponseEntity.ok().body(publicationCommentService.getAllPublicationCommentsByPublicationId(publicationId));
    }

    @PostMapping("/api/publication/comment/delete")
    public ResponseEntity<?> deletePublicationCommentById(@RequestParam Long publicationCommentId) {
        return ResponseEntity.ok().body(publicationCommentService.deletePublicationCommentById(publicationCommentId));
    }
}
