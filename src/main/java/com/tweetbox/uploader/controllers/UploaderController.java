package com.tweetbox.uploader.controllers;

import com.tweetbox.uploader.services.UploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("uploader")
public class UploaderController {
    private final UploaderService uploaderService;

    public UploaderController(UploaderService uploaderService) {
        this.uploaderService = uploaderService;
    }

    @GetMapping("/unzip")
    public String unZipFile2(@RequestParam String userId, @RequestParam String fileName) {
        return this.uploaderService.UnZip(userId, fileName);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestPart MultipartFile file) throws FileNotFoundException, IOException {
        return this.uploaderService.Upload(file);

    }
}
