package ru.ruslan.entity;

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
    private Long workerId;
    private String title;
    private String description;
    private Integer cost;
    private String creationTime;
    private String completionTime;
    private TaskStatus status = TaskStatus.Unresolved;

    public Task() {
    }

    public Task(Long taskId, Long ownerId, Long workerId, String title, String description, Integer cost, String creationDate, String completionDate, TaskStatus status) {
        this.taskId = taskId;
        this.ownerId = ownerId;
        this.workerId = workerId;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.creationTime = creationDate;
        this.completionTime = completionDate;
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

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
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

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public enum TaskStatus {
        Unresolved,
        InProgress,
        Resolved
    }
}
