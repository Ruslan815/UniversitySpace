package ru.ruslan.entity;

import javax.persistence.*;

@Entity(name = "task_comments")
public class TaskComment extends Comment {

    private Long taskId;

    public TaskComment() {
    }

    public TaskComment(Long commentId, Long authorId, String creationTime, String text, Long taskId) {
        super(commentId, authorId, creationTime, text);
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
