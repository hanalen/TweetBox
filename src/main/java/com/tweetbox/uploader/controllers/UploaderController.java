package com.tweetbox.uploader.controllers;

import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("uploader")
public class UploaderController {
    @PostMapping("/upload")
    public String uploadFile(@RequestPart MultipartFile file) throws FileNotFoundException, IOException {

        String path = System.getProperty("user.dir");
        path += "/upload";
        File newFile = new File(path, file.getOriginalFilename());
        newFile.mkdirs();
        file.transferTo(newFile);

        return "Saved";
    }
}
