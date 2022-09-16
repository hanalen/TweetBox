package com.tweetbox.uploader.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record RequestUnZipDto(@JsonProperty("userId") Long userId,
                              @JsonProperty("fileName") String fileName,
                              @JsonProperty("progressId") Long progressId)
        implements Serializable {
}
//public class RequestUnZipDto {
//  private Long userId;
//  private String fileName;
//  private Long progressId;
//
//  public void setUserId(Long userId) {
//    this.userId = userId;
//  }
//
//  public Long getUserId() {
//    return this.userId;
//  }
//
//  public void setFileName(String fileName) {
//    this.fileName = fileName;
//  }
//
//  public String getFileName() {
//    return this.fileName;
//  }
//
//  public Long getProgressId() {
//    return progressId;
//  }
//
//  public void setProgressId(Long progressId) {
//    this.progressId = progressId;
//  }
//}
