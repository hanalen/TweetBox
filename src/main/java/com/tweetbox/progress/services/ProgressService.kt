package com.tweetbox.progress.services

import com.tweetbox.progress.dao.ProgressDaoImpl
import com.tweetbox.progress.entities.Progress
import com.tweetbox.progress.entities.ProgressStatus
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class ProgressService(private var progressDaoImpl: ProgressDaoImpl) {
    fun findProgressByUserId(userId: Long): Progress {
//        this.progressRepository.findOne()
        return this.progressDaoImpl.findByUserId(userId);
    }

    fun Test2(): String {
        var progress = Progress();
        progress.userId = 10;
        progress.progressStatus = ProgressStatus.NONE;
        this.progressDaoImpl.saveProgress(progress);
        return "ab";
    }
}