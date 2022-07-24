package com.tweetbox.uploader.services;

import com.tweetbox.logger.services.LoggerService;
import com.tweetbox.progress.dao.ResponseProgressDto;
import com.tweetbox.progress.entities.Progress;
import com.tweetbox.progress.entities.ProgressStatus;
import com.tweetbox.progress.services.ProgressService;
import com.tweetbox.uploader.dtos.RequestCorrectFile;
import com.tweetbox.uploader.dtos.RequestUnZipDto;
import lombok.SneakyThrows;
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

    public UploaderService(LoggerService loggerService, ProgressService progressService) {
        this.loggerService = loggerService;
        this.progressService = progressService;
    }

    @SneakyThrows
    public String CorrectFile(RequestCorrectFile requestCorrectFile) {
        Path filePath = Paths.get(pathUploadFolder + "/" + requestCorrectFile.getFileName());
        File file = new File(pathUploadFolder + "/" + requestCorrectFile.getFileName());
        if (file.exists() == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 파일");
        } else if (file.length() != requestCorrectFile.getFileSize()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일 사이즈가 다릅니다.");
        }

        return "OK";
    }

    @SneakyThrows
    public ResponseProgressDto Upload(MultipartFile file, Long userId) {
        ResponseProgressDto progress = this.progressService.createProgress(10, ProgressStatus.UPLOAD_START);
        try {
            File newFile = new File(this.pathUploadFolder, file.getOriginalFilename());
            newFile.mkdirs();
            file.transferTo(newFile);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

        return this.progressService.updateProgress(progress.id, ProgressStatus.UPLOAD_END);
    }

    @SneakyThrows
    public ResponseProgressDto UnZip(RequestUnZipDto requestUnZipDto) {
        this.progressService.updateProgress(requestUnZipDto.getProgressId(), ProgressStatus.UNZIP_START);

        String pathUnzipFolder = this.pathUploadFolder + "/" + requestUnZipDto.getUserId();
        Path zipFilePath = Paths.get(this.pathUploadFolder + "/" + requestUnZipDto.getFileName());

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
        return this.progressService.updateProgress(requestUnZipDto.getProgressId(), ProgressStatus.UNZIP_END);
    }
}
