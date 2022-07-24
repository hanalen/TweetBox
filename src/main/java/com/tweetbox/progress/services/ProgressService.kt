package com.tweetbox.progress.services

import com.tweetbox.progress.entities.Progress
import com.tweetbox.progress.entities.ProgressStatus
import com.tweetbox.progress.repositories.ProgressRepository
import org.springframework.stereotype.Component

@Component
class ProgressService(private var progressRepository: ProgressRepository) {
    fun Test2(): String {
        var progress = Progress();
        progress.user_id = 10;
        progress.progressStatus = ProgressStatus.NONE;
        this.progressRepository.save(progress);
        return "ab";
    }
}