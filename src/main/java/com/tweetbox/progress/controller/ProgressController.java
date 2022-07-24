package com.tweetbox.progress.controller;

import com.tweetbox.progress.dtos.GetProgressDto;
import com.tweetbox.progress.services.ProgressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("progress")
public class ProgressController {
    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("")
    public String GetProgress(GetProgressDto getProgressDto) {
        return this.progressService.Test2();
    }
}
