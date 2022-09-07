package com.tweetbox.uploader.controllers;

import com.tweetbox.progress.dtos.ResponseProgressDto;
import com.tweetbox.uploader.dtos.RequestCorrectFile;
import com.tweetbox.uploader.dtos.RequestUnZipDto;
import com.tweetbox.uploader.services.UploaderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("uploader")
public class UploaderController {
  private final UploaderService uploaderService;

  public UploaderController(UploaderService uploaderService) {
    this.uploaderService = uploaderService;
  }

  @GetMapping("/correct-file")
  public ResponseProgressDto FileCorrect(RequestCorrectFile requestFileCorrect) {
    return this.uploaderService.CorrectFile(requestFileCorrect);
  }

  @GetMapping("/unzip")
  public ResponseProgressDto UnZipFile(RequestUnZipDto requestUnZipDto) {
    return this.uploaderService.UnZip(requestUnZipDto);
  }

  @PostMapping("/upload")
  public ResponseProgressDto UploadFile(@RequestPart MultipartFile file, @RequestParam Long userId) {
    return this.uploaderService.Upload(file, userId);
  }
}
