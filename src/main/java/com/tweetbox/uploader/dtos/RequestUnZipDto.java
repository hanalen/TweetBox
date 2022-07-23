package com.tweetbox.uploader.dtos;

public class RequestUnZipDto {
    private String userId;
    private String fileName;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
