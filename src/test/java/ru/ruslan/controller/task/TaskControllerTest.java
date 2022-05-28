package ru.ruslan.controller.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.entity.task.Task;
import ru.ruslan.service.task.TaskService;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskControllerTest {

    @Autowired
    private TaskController taskController;

    @MockBean
    private TaskService taskService;

    private final String title = "title";
    private final String text = "text";
    private final String deadline = "2022-11-12 15:38:02";
    private final Integer cost = 100;

    @Test
    public void createTaskSuccessful() {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(text);
        task.setDeadline(deadline);
        task.setCost(cost);

        ResponseEntity<?> expectedResponse = ResponseEntity.ok().body("Task created successfully!");
        try {
            Mockito.when(taskService.createTask(task)).thenReturn("Task created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = taskController.createTask(task);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createTaskFailedTooHighCost() {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(text);
        task.setDeadline(deadline);
        task.setCost(cost);

        ResponseEntity<?> expectedResponse = ResponseEntity.status(500).body(null);
        try {
            Mockito.when(taskService.createTask(task)).thenThrow(Exception.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseEntity<?> actualResponse = taskController.createTask(task);

        assertEquals(expectedResponse, actualResponse);
    }
}