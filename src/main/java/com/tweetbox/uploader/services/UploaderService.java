package com.tweetbox.uploader.services;

import com.tweetbox.uploader.dtos.RequestUnZipDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    private String pathUploadFolder = "e:\\Upload";

    public String Upload(MultipartFile file) {
        try {
            File newFile = new File(this.pathUploadFolder, file.getOriginalFilename());
            newFile.mkdirs();
            file.transferTo(newFile);
        } catch (IOException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
        return "ok";
    }

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
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }
}
