package com.tweetbox.reader.controllers;

import com.tweetbox.progress.dtos.ResponseProgressDto;
import com.tweetbox.reader.dtos.RequestReadArchive;
import com.tweetbox.reader.services.ReaderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reader")
public class ReaderController {
  private final ReaderService readerService;

  public ReaderController(ReaderService readerService) {
    this.readerService = readerService;
  }


  @PostMapping("")
  public ResponseProgressDto ReadTweetArchive(@RequestBody RequestReadArchive requestReadArchive) {
    return this.readerService.readTweetArchive(requestReadArchive);
  }
}
