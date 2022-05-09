package ru.ruslan.entity.task;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private Long ownerId;
    private String title;
    private String description;
    private Integer cost;
    private String creationTime;
    private String deadline;
    private TaskStatus status = TaskStatus.Unresolved;
    private Long taskCommentSolutionId;

    public Task() {
    }

    public Task(Long taskId, Long ownerId, String title, String description, Integer cost,
                String creationTime, String deadline, TaskStatus status) {
        this.taskId = taskId;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.creationTime = creationTime;
        this.deadline = deadline;
        this.status = status;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public enum TaskStatus {
        Unresolved,
        Resolved
    }

    public Long getTaskCommentSolutionId() {
        return taskCommentSolutionId;
    }

    public void setTaskCommentSolutionId(Long taskCommentSolutionId) {
        this.taskCommentSolutionId = taskCommentSolutionId;
    }
}
