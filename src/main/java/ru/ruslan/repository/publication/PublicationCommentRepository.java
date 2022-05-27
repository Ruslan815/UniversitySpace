package ru.ruslan.repository.publication;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.publication.PublicationComment;

import java.util.List;

public interface PublicationCommentRepository extends JpaRepository<PublicationComment, Long> {
    List<PublicationComment> findAllByPublicationId(Long publicationId);
    void deleteAllByPublicationId(Long publicationId);
}
