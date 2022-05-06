package ru.ruslan.repository.publication;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ruslan.entity.publication.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
