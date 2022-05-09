package ru.ruslan.service.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ruslan.entity.publication.PublicationComment;
import ru.ruslan.repository.publication.PublicationCommentRepository;
import ru.ruslan.service.user.SecurityUserService;
import ru.ruslan.service.user.UserService;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PublicationCommentService {
    @Autowired
    private final PublicationCommentRepository publicationCommentRepository;
    @Autowired
    private final UserService userService;

    public PublicationCommentService(PublicationCommentRepository publicationCommentRepository, UserService userService) {
        this.publicationCommentRepository = publicationCommentRepository;
        this.userService = userService;
    }

    public List<PublicationComment> getAllPublicationCommentsByPublicationId(Long publicationId) {
        return publicationCommentRepository.findAllByPublicationId(publicationId);
    }

    public String createPublicationComment(PublicationComment publicationComment) throws Exception {
        publicationComment.setAuthorId(SecurityUserService.getCurrentUserId());
        String authorUsername = userService.findUserById(publicationComment.getAuthorId()).getUsername();
        publicationComment.setAuthorUsername(authorUsername);

        long currentTimeInMillis = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        publicationComment.setCreationTime(formatter.format(currentTimeInMillis));

        publicationCommentRepository.save(publicationComment);
        return "PublicationComment created successfully!";
    }

    public String deletePublicationCommentById(Long publicationCommentId) {
        publicationCommentRepository.deleteById(publicationCommentId);
        return "PublicationComment deleted successfully!";
    }

    @Transactional // transaction is necessary
    public void deleteAllByPublicationId(Long publicationId) {
        publicationCommentRepository.deleteAllByPublicationId(publicationId);
    }
}
