package com.tweetbox.progress.dao;

import com.tweetbox.progress.entities.Progress;

public class ResponseProgressDto extends Progress {
    public ResponseProgressDto(Progress progress) {
        this.progressStatus = progress.progressStatus;
        this.userId = progress.userId;
        this.id = progress.id;
    }
}
