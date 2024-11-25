package com.menglang.bong_rumluos.Bong_rumluos.services.storage;

import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.utils.FileUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    public static final String FILE_PATH = System.getProperty("user.dir") + "/uploads";

    public String upload(MultipartFile file) {
        return FileUtils.saveMultipartFile(file, FILE_PATH);
    }

    public void loadFile(String fileName, HttpServletResponse response){
        try{
            Path path= Paths.get(FILE_PATH).resolve(fileName).toAbsolutePath().normalize();
            Resource resource=new UrlResource(path.toUri());
            if(!resource.exists() || !resource.isReadable()){
                throw new NotFoundException("File Not Found");
            }
            response.setHeader(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path));
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"inline;filename="+"\""+fileName+"\"");
            response.setHeader(HttpHeaders.CONTENT_LENGTH,String.valueOf(Files.size(path)));

            FileCopyUtils.copy(resource.getInputStream(),response.getOutputStream());
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public void deleteFile(String fileName){
        Path file=Paths.get(FILE_PATH).resolve(fileName).toAbsolutePath().normalize();
        try{
            if(Files.exists(file)){
                Files.delete(file);
            }
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
