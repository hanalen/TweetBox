package com.tweetbox.uploader.controllers;

import com.tweetbox.progress.dao.ResponseProgressDto;
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
    public String FileCorrect(RequestCorrectFile requestFileCorrect) {
        String msg = this.uploaderService.CorrectFile(requestFileCorrect);
        return msg;
    }

    @GetMapping("/unzip")
    public String unZipFile2(RequestUnZipDto requestUnZipDto) {
        return this.uploaderService.UnZip(requestUnZipDto);
    }

    @PostMapping("/upload")
    public ResponseProgressDto uploadFile(@RequestPart MultipartFile file, @RequestParam Long userId) {
        return this.uploaderService.Upload(file, userId);
    }
}
