package ru.ruslan.controller.publication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.entity.publication.Publication;
import ru.ruslan.service.publication.PublicationService;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublicationControllerTest {

    @Autowired
    private PublicationController publicationController;

    @MockBean
    private PublicationService publicationService;

    private final String title = "title";
    private final String content = "content";

    @Test
    public void createPublicationSuccessful() {
        Publication publication = new Publication();
        publication.setTitle(title);
        publication.setContent(content);

        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body("Publication created successfully!");
        try {
            Mockito.when(publicationService.createPublication(publication)).thenReturn("Publication created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = publicationController.createPublication(publication);

        assertEquals(expectedResponse, actualResponse);
    }
}