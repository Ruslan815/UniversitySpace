package ru.ruslan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.publication.PublicationComment;

import java.util.List;

public interface PublicationCommentRepository extends JpaRepository<PublicationComment, Long> {
    List<PublicationComment> findAllByPublicationId(Long publicationId);
}
