package com.tweetbox.uploader.services;

import com.tweetbox.logger.services.LoggerService;
import com.tweetbox.progress.dtos.ResponseProgressDto;
import com.tweetbox.progress.entities.ProgressStatus;
import com.tweetbox.progress.services.ProgressService;
import com.tweetbox.rabbitmq.services.RabbitMQService;
import com.tweetbox.reader.dtos.RequestReadArchive;
import com.tweetbox.uploader.dtos.RequestCorrectFile;
import com.tweetbox.uploader.dtos.RequestUnZipDto;
import lombok.SneakyThrows;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class UploaderService {
  private final String pathUploadFolder = "e:\\Upload";
  private final LoggerService loggerService;
  private final ProgressService progressService;
  private final RabbitMQService rabbitMQService;

  public UploaderService(LoggerService loggerService, ProgressService progressService, RabbitMQService rabbitMQService) {
    this.loggerService = loggerService;
    this.progressService = progressService;
    this.rabbitMQService = rabbitMQService;
  }

  @SneakyThrows
  public ResponseProgressDto CorrectFile(RequestCorrectFile requestCorrectFile) {
    File file = new File(pathUploadFolder + "/" + requestCorrectFile.getFileName());
    if (file.exists() == false) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 파일");
    } else if (file.length() != requestCorrectFile.getFileSize()) {
      this.progressService.updateProgress(requestCorrectFile.getProgressId(), ProgressStatus.FAIL);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일 사이즈가 다릅니다.");
    }

    return this.progressService.updateProgress(requestCorrectFile.getProgressId(), ProgressStatus.FILE_CORRECT);
  }

  @SneakyThrows
  public ResponseProgressDto Upload(MultipartFile file, Long userId) {
    ResponseProgressDto progress = this.progressService.createProgress(10, ProgressStatus.UPLOAD_START);
    String orgFileName = file.getOriginalFilename();
    String extName = orgFileName.substring(orgFileName.lastIndexOf("."), orgFileName.length());
    if(extName.equals(".zip") == false) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "압축파일만 전송할 수 있습니다.");
    }
    String newFileName = userId.toString() + extName;

    try {
      File newFile = new File(this.pathUploadFolder, newFileName);
      newFile.mkdirs();
      file.transferTo(newFile);
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }

    ResponseProgressDto ret = this.progressService.updateProgress(progress.id, ProgressStatus.UPLOAD_END);
    RequestUnZipDto dto = new RequestUnZipDto(userId, newFileName, ret.getId());
    this.rabbitMQService.SendUnzip(dto);

    return ret;
  }

  @SneakyThrows
  @RabbitListener(queues = "tweetbox.queue.unzip")
  public void UnZip(RequestUnZipDto requestUnZipDto) {
    this.loggerService.Info("Received queue unzip user id: {}", requestUnZipDto.userId().toString());
    this.progressService.updateProgress(requestUnZipDto.progressId(), ProgressStatus.UNZIP_START);

    String pathUnzipFolder = this.pathUploadFolder + "/" + requestUnZipDto.userId();
    Path zipFilePath = Paths.get(this.pathUploadFolder + "/" + requestUnZipDto.fileName());

    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath.toFile()))) {
      Files.createDirectories(Paths.get(pathUnzipFolder));
      ZipEntry zipEntry = zis.getNextEntry();
      while (zipEntry != null) {
        String zipFileName = zipEntry.getName();
        Path pathNewFile = Paths.get(pathUnzipFolder + "\\" + zipFileName);
        if (zipFileName.endsWith(File.separator) || zipFileName.endsWith("/")) {
          Files.createDirectories(pathNewFile);
        } else {
          Files.copy(zis, pathNewFile, StandardCopyOption.REPLACE_EXISTING);
        }
        zipEntry = zis.getNextEntry();
      }
      zis.closeEntry();
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw e;
    }
    this.progressService.updateProgress(requestUnZipDto.progressId(), ProgressStatus.UNZIP_END);
    this.rabbitMQService.SendReadArchive(new RequestReadArchive(requestUnZipDto.userId(), requestUnZipDto.fileName(), requestUnZipDto.progressId()));
  }
}
