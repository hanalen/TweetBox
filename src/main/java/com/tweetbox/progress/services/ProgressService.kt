package com.tweetbox.progress.services

import com.tweetbox.progress.dao.ProgressDaoImpl
import com.tweetbox.progress.dao.ResponseProgressDto
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

    fun createProgress(userId: Long): Progress {
        var progress = Progress();
        progress.progressStatus = ProgressStatus.NONE;
        return this.progressDaoImpl.saveProgress(progress);
    }

    fun createProgress(userId: Long, progressStatus: ProgressStatus): ResponseProgressDto {
        var progress = Progress();
        progress.userId = userId;
        progress.progressStatus = progressStatus;
        var result = this.progressDaoImpl.saveProgress(progress);
        var responseProgressDto = ResponseProgressDto(result);

        return responseProgressDto;
    }

    fun updateProgress(progressId: Long, progressStatus: ProgressStatus): ResponseProgressDto {
        var progress = Progress();
        progress.id = progressId;
        progress.progressStatus = progressStatus;

        var result = this.progressDaoImpl.updateProgress(progress);
        var responseProgressDto = ResponseProgressDto(result);

        return responseProgressDto;
    }

}