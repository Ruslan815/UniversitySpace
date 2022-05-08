package ru.ruslan.service.publication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ruslan.entity.publication.Publication;
import ru.ruslan.entity.task.Task;
import ru.ruslan.repository.publication.PublicationRepository;
import ru.ruslan.service.user.SecurityUserService;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PublicationService {
    @Autowired
    private final PublicationRepository publicationRepository;

    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public List<Publication> getAllPublicationsByAuthorId(Long authorId) {
        return publicationRepository.findAllByAuthorId(authorId);
    }

    public Publication getPublicationById(Long publicationId) {
        return publicationRepository.findById(publicationId).orElseThrow();
    }

    public String createPublication(Publication somePublication) throws Exception {
        somePublication.setAuthorId(SecurityUserService.getCurrentUserId());

        long currentTimeInMillis = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        somePublication.setCreationTime(formatter.format(currentTimeInMillis));

        publicationRepository.save(somePublication);
        return "Publication created successfully!";
    }

    public String updatePublication(Publication updatedPublication) {
        updatedPublication.setModified(true);
        long currentTimeInMillis = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        updatedPublication.setLastModifiedTime(formatter.format(currentTimeInMillis));

        publicationRepository.save(updatedPublication);
        return "Publication updated successfully!";
    }

    public String deletePublicationById(Long publicationId) {
        publicationRepository.deleteById(publicationId);
        return "Publication deleted successfully!";
    }
}
