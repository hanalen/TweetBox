package com.tweetbox.reader.services

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.tweetbox.logger.services.LoggerService
import com.tweetbox.progress.dtos.ResponseProgressDto
import com.tweetbox.progress.entities.ProgressStatus
import com.tweetbox.progress.services.ProgressService
import com.tweetbox.reader.dtos.RequestReadArchive
import com.tweetbox.tweet.data.Tweet
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.io.File
import java.io.FileInputStream

@Service
class ReaderService(private var progressService: ProgressService, private var loggerService: LoggerService) {
  private val pathUploadFolder = "e:\\Upload\\";
  private val normalTypePath = "\\data\\js\\tweets";
  private val mediaTypeOffset = 25;
  private val normalTypeOffset = 33;

  enum class ArchiveType {
    NORMAL_OLD,
    MEDIA,
  }

  private fun getArchiveType(requestReadArchive: RequestReadArchive): ArchiveType {
    var fileName = requestReadArchive.fileName;
    var folderFile = File(pathUploadFolder + fileName);
    var normalTypeFile =
      folderFile.listFiles().find { file -> file.name == "index.htm" || file.name == "index.html" };
    var mediaTypeFile = folderFile.listFiles().find { file -> file.name == "tweet.js" };

    if (normalTypeFile != null) {
      return ArchiveType.NORMAL_OLD;
    } else if (mediaTypeFile != null) {
      return ArchiveType.MEDIA;
    } else {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, "아카이브 형식이 잘못되었습니다.");
    }
  }

  private fun readTweetArchiveTypeMedia(requestReadArchive: RequestReadArchive): ArrayList<Tweet> {
    this.progressService.updateProgress(requestReadArchive.progressId, ProgressStatus.READ_START);
    var tweetArrayList = ArrayList<Tweet>();
    var folderFile = File(pathUploadFolder + requestReadArchive.fileName);
    var tweetFiles =
      folderFile.listFiles().filter { file -> file.name.indexOf("tweet") == 0 && file.name.indexOf(".js") > -1 }
    for (currentFile in tweetFiles) {
      try {
        FileInputStream(currentFile).use {
          var byteArray = ByteArray(mediaTypeOffset);
          it.read(byteArray, 0, mediaTypeOffset);
          val objectMapper = ObjectMapper();
          val tweets: ArrayList<Tweet> =
            objectMapper.readValue(it, object : TypeReference<ArrayList<Tweet>>() {})
          tweetArrayList.addAll(tweets);
        }
      } catch (e: Error) {
        throw e;
      }
    }
    this.progressService.updateProgress(requestReadArchive.progressId, ProgressStatus.READ_END);

    return tweetArrayList;
  }

  private fun readTweetArchiveTypeNormal(requestReadArchive: RequestReadArchive): ArrayList<Tweet> {
    this.progressService.updateProgress(requestReadArchive.progressId, ProgressStatus.READ_START);

    var tweetArrayList = ArrayList<Tweet>();
    var folderFile = File(pathUploadFolder + requestReadArchive.fileName + normalTypePath);
    var tweetFiles =
      folderFile.listFiles().filter { file -> file.name.indexOf(".js") > -1 }
    for (currentFile in tweetFiles) {
      try {
        FileInputStream(currentFile).use {
          var byteArray = ByteArray(normalTypeOffset);
          it.read(byteArray, 0, normalTypeOffset);
          val objectMapper = ObjectMapper();
          val tweets: ArrayList<Tweet> =
            objectMapper.readValue(it, object : TypeReference<ArrayList<Tweet>>() {})
          tweetArrayList.addAll(tweets);
        }
      } catch (e: Error) {
        throw e;
      }
    }
    this.progressService.updateProgress(requestReadArchive.progressId, ProgressStatus.READ_END);

    return tweetArrayList;
  }

  @RabbitListener(queues = ["tweetbox.queue.read-archive"])
  fun readTweetArchive(requestReadArchive: RequestReadArchive) {
    this.progressService.updateProgress(requestReadArchive.progressId, ProgressStatus.READ_START);
    var archiveType = this.getArchiveType(requestReadArchive);
    var tweetArrayList: ArrayList<Tweet>;
    if (archiveType == ArchiveType.NORMAL_OLD) {
      tweetArrayList = this.readTweetArchiveTypeNormal(requestReadArchive);
    } else if (archiveType == ArchiveType.MEDIA) {
      tweetArrayList = this.readTweetArchiveTypeMedia(requestReadArchive);
    }
    this.progressService.updateProgress(requestReadArchive.progressId, ProgressStatus.READ_END);
  }
}