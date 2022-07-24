package com.tweetbox.uploader.services;

import com.tweetbox.uploader.dtos.RequestCorrectFile;
import com.tweetbox.uploader.dtos.RequestUnZipDto;
import lombok.SneakyThrows;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
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

    @SneakyThrows
    public String CorrectFile(RequestCorrectFile requestCorrectFile) {
        Path filePath = Paths.get(pathUploadFolder + "/" + requestCorrectFile.getFileName());
        File file = new File(pathUploadFolder + "/" + requestCorrectFile.getFileName());
        if (file.exists() == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 파일");
//            throw new BadHttpRequest(new Exception("잘못된 파일"));
        } else if (file.length() != requestCorrectFile.getFileSize()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "파일 사이즈가 다릅니다.");
        }

        return "OK";
    }

    @SneakyThrows
    public String Upload(MultipartFile file) {
        try {
            File newFile = new File(this.pathUploadFolder, file.getOriginalFilename());
            newFile.mkdirs();
            file.transferTo(newFile);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return "ok";
    }

    @SneakyThrows
    public String UnZip(RequestUnZipDto requestUnZipDto) {
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
        return "OK";
    }
}
