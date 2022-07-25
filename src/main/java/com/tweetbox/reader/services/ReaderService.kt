package com.tweetbox.reader.services

import com.tweetbox.logger.services.LoggerService
import com.tweetbox.progress.dao.ResponseProgressDto
import com.tweetbox.progress.entities.ProgressStatus
import com.tweetbox.progress.services.ProgressService
import com.tweetbox.reader.dtos.RequestReadArchive
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.io.File
import kotlin.io.path.Path

@Service
class ReaderService(private var progressService: ProgressService, private var loggerService: LoggerService) {
    private val pathUploadFolder = "e:\\Upload\\"

    private fun getArchiveType(requestReadArchive: RequestReadArchive) {
        if (requestReadArchive.fileName.indexOf(".") > -1) {
            requestReadArchive.fileName =
                requestReadArchive.fileName.substring(0, requestReadArchive.fileName.indexOf("."));
        }

        var fileName = requestReadArchive.fileName;
        var folderFile = File(pathUploadFolder + fileName);
        var normalTypeFile =
            folderFile.listFiles().find { file -> file.name == "index.htm" || file.name == "index.html" };
        var mediaTypeFile = folderFile.listFiles().find { file -> file.name == "tweet.js" };

        if (normalTypeFile != null) {
            this.readTweetArchiveTypeNormal(requestReadArchive);
        } else if (mediaTypeFile != null) {
            this.readTweetArchiveTypeMedia(requestReadArchive);
        } else {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "아카이브 형식이 잘못되었습니다.")
        }
    }

    private fun readTweetArchiveTypeMedia(requestReadArchive: RequestReadArchive) {
        this.progressService.updateProgress(requestReadArchive.progressId, ProgressStatus.READ_START);
    }

    private fun readTweetArchiveTypeNormal(requestReadArchive: RequestReadArchive) {
        this.progressService.updateProgress(requestReadArchive.progressId, ProgressStatus.READ_START);
    }

    fun readTweetArchive(requestReadArchive: RequestReadArchive): ResponseProgressDto {
        this.getArchiveType(requestReadArchive);
        return this.progressService.findProgressById(requestReadArchive.progressId);
    }
}