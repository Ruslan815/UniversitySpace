package ru.ruslan.service.publication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.entity.publication.Publication;
import ru.ruslan.repository.publication.PublicationRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublicationServiceTest {

    @Autowired
    private PublicationService publicationService;

    @MockBean
    private PublicationRepository publicationRepository;

    @MockBean
    private PublicationCommentService publicationCommentService;

    private final String title = "title";
    private final String content = "content";
    private final Long publicationId = 777L;

    @Test
    public void deletePublicationSuccessful() {
        Publication publication = new Publication();
        publication.setTitle(title);
        publication.setContent(content);
        String expectedResponse = "Publication deleted successfully!";
        try {
            Optional<Publication> optional = Optional.of(publication);
            Mockito.when(publicationRepository.findById(publicationId)).thenReturn(optional);
            doNothing().when(publicationCommentService).deleteAllByPublicationId(publicationId);
            doNothing().when(publicationRepository).deleteById(publicationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String actualResponse = null;
        try {
            actualResponse = publicationService.deletePublicationById(publicationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void deletePublicationFailedInvalidId() {
        String expectedResponse = "Not found publication with id: " + publicationId;
        try {
            Optional<Publication> optional = Optional.empty();
            Mockito.when(publicationRepository.findById(publicationId)).thenReturn(optional);
            doNothing().when(publicationCommentService).deleteAllByPublicationId(publicationId);
            doNothing().when(publicationRepository).deleteById(publicationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String actualResponse = null;
        try {
            actualResponse = publicationService.deletePublicationById(publicationId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedResponse, actualResponse);
    }
}