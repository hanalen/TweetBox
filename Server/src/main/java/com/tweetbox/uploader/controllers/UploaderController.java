package com.tweetbox.uploader.controllers;

import com.tweetbox.api.data.OAuthRes;
import com.tweetbox.progress.dtos.ResponseProgressDto;
import com.tweetbox.uploader.dtos.RequestCorrectFile;
import com.tweetbox.uploader.dtos.RequestUnZipDto;
import com.tweetbox.uploader.services.UploaderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("uploader")
public class UploaderController {
  private final UploaderService uploaderService;

  public UploaderController(UploaderService uploaderService) {
    this.uploaderService = uploaderService;
  }


  @GetMapping("/correct-file")
  public ResponseProgressDto FileCorrect(RequestCorrectFile requestFileCorrect, @SessionAttribute("OAUth") OAuthRes oauth) {
    return this.uploaderService.CorrectFile(requestFileCorrect);
  }

//  @GetMapping("/unzip")
//  public ResponseProgressDto UnZipFile(RequestUnZipDto requestUnZipDto) {
//    return this.uploaderService.UnZip(requestUnZipDto);
//  }

  @PostMapping("/upload")
  public ResponseProgressDto UploadFile(@RequestPart MultipartFile file, @RequestParam Long userId) {
    return this.uploaderService.Upload(file, userId);
  }
}
