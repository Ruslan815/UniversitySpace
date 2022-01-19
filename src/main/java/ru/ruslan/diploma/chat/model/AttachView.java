package ru.ruslan.diploma.chat.model;

public class AttachView {

    private String fileName;

    public AttachView(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
