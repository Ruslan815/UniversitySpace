package ru.ruslan.entity.task;

import ru.ruslan.entity.abstracted.Comment;

import javax.persistence.*;

@Entity(name = "task_comments")
public class TaskComment extends Comment {
    private Long taskId;
    private String authorUsername;

    public TaskComment() {
    }

    public TaskComment(Long commentId, Long authorId, String creationTime, String text, Long taskId, String authorUsername) {
        super(commentId, authorId, creationTime, text);
        this.taskId = taskId;
        this.authorUsername = authorUsername;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
