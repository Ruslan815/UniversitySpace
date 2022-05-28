package ru.ruslan.service.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ruslan.entity.task.Task;
import ru.ruslan.repository.task.TaskRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private TaskCommentService taskCommentService;

    private final Long taskId = 777L;

    @Test
    public void deleteTaskSuccessful() {
        Task task = new Task();
        task.setTaskId(taskId);
        String expectedResponse = "Task deleted successfully!";
        try {
            Optional<Task> optional = Optional.of(task);
            Mockito.when(taskRepository.findById(taskId)).thenReturn(optional);
            doNothing().when(taskCommentService).deleteAllByTaskId(taskId);
            doNothing().when(taskRepository).deleteById(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String actualResponse = null;
        try {
            actualResponse = taskService.deleteTaskById(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void deleteTaskFailedInvalidId() {
        String expectedResponse = "Not found task with id: " + taskId;
        try {
            Optional<Task> optional = Optional.empty();
            Mockito.when(taskRepository.findById(taskId)).thenReturn(optional);
            doNothing().when(taskCommentService).deleteAllByTaskId(taskId);
            doNothing().when(taskRepository).deleteById(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String actualResponse = null;
        try {
            actualResponse = taskService.deleteTaskById(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expectedResponse, actualResponse);
    }
}