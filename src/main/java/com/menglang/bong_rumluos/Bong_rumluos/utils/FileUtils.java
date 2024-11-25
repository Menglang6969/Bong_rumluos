package com.menglang.bong_rumluos.Bong_rumluos.utils;

import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static String saveMultipartFile(MultipartFile file, String path) {
        if (file.getSize() <= 0) throw new BadRequestException("File Not found!");

        try {
            String fileName = UUID.randomUUID().toString();
            String originalFileName = file.getOriginalFilename();

//      Long fileSize = file.getSize();
//      String contentType = file.getContentType();

            String sourceFilename = (originalFileName != null && !originalFileName.isBlank()) ? originalFileName : file.getName();
            String ext = sourceFilename.contains(".") ? sourceFilename.substring(sourceFilename.lastIndexOf(".")) : "";

            Path filePath = !path.isBlank() || !path.isEmpty() ? Paths.get(path) : Paths.get("./"); //create object of Path
            log.info("file path: {} ", filePath);

            if (Files.notExists(filePath)) Files.createDirectories(filePath);
            String fullName = fileName + ext;
            file.transferTo(filePath.resolve(fullName));
            return fullName;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
