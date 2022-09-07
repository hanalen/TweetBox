package com.tweetbox.progress.services

import com.tweetbox.progress.dao.ProgressDaoImpl
import com.tweetbox.progress.dtos.ResponseProgressDto
import com.tweetbox.progress.entities.Progress
import com.tweetbox.progress.entities.ProgressStatus
import org.springframework.stereotype.Service

@Service
class ProgressService(private var progressDaoImpl: ProgressDaoImpl) {
  fun findProgressByUserId(userId: Long): Progress {
    return this.progressDaoImpl.findByUserId(userId);
  }

  fun findProgressById(progressId: Long): ResponseProgressDto {
    var progress = this.progressDaoImpl.findById(progressId);

    return ResponseProgressDto(progress);
  }

  fun createProgress(userId: Long): ResponseProgressDto {
    var progress = Progress();
    progress.progressStatus = ProgressStatus.NONE;
    var result = this.progressDaoImpl.saveProgress(progress);

    return ResponseProgressDto(result);
  }

  fun createProgress(userId: Long, progressStatus: ProgressStatus): ResponseProgressDto {
    var progress = Progress();
    progress.userId = userId;
    progress.progressStatus = progressStatus;
    var result = this.progressDaoImpl.saveProgress(progress);

    return ResponseProgressDto(result);
  }

  fun updateProgress(progressId: Long, progressStatus: ProgressStatus): ResponseProgressDto {
    var progress = this.progressDaoImpl.findById(progressId)
    progress.progressStatus = progressStatus;

    var result = this.progressDaoImpl.updateProgress(progress);
    var responseProgressDto = ResponseProgressDto(result);

    return responseProgressDto;
  }

}