package ru.ruslan.repository.publication;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.publication.Publication;

import java.util.List;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
    List<Publication> findAllByAuthorId(Long authorId);
}
