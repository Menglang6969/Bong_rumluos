package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.constant.AppProperties;
import com.menglang.bong_rumluos.Bong_rumluos.dto.file.FileMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.file.FileRequest;
import com.menglang.bong_rumluos.Bong_rumluos.dto.file.FileResponse;
import com.menglang.bong_rumluos.Bong_rumluos.services.file.FileService;
import com.menglang.bong_rumluos.Bong_rumluos.services.storage.StorageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;
    private final FileMapper fileMapper;
    private final StorageService storageService;
    private final AppProperties appProperties;

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<FileResponse> upload(@RequestPart MultipartFile file){
        log.info("invoke file upload.....");
        return ResponseEntity.ok(fileMapper.toFileResponse(fileService.upload(file),appProperties));
    }

    @PostMapping(value = "uploads", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<FileResponse>> uploads(@RequestPart List<MultipartFile> files){
        log.info("invoke file batch upload.....");
        return ResponseEntity.ok(fileMapper.toListFileResponse(fileService.batchUpload(files),appProperties));
    }

    @GetMapping("/load/{filename}")
    public void loadFile(@PathVariable String filename, HttpServletResponse response) {
        this.storageService.loadFile(filename, response);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<FileResponse>> findAll(
            @RequestParam(name = "page",defaultValue = "1") int page,
            @RequestParam(name = "page-size",defaultValue = "10") int size,
            @RequestParam(name="is-trash",defaultValue = "false") boolean isTrash,
            @RequestParam(name = "query",defaultValue = "") String query
    ){
        return ResponseEntity.ok(fileMapper.toListFileResponse(fileService.findAll(page,size,isTrash,query).stream().toList(),appProperties));
    }

    @PatchMapping("/update-file-name/{id}")
    public ResponseEntity<FileResponse> updateFileName(@PathVariable("id") Long id, @RequestBody FileRequest fileRequest){
        return ResponseEntity.ok(fileMapper.toFileResponse(fileService.updateFileName(id,fileRequest),appProperties));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<FileResponse> deleteFile(@PathVariable("id") Long id){
        return ResponseEntity.ok(fileMapper.toFileResponse(fileService.delete(id),appProperties));
    }

    @PatchMapping("/restore/{id}")
    public ResponseEntity<FileResponse> restoreFile(@PathVariable("id") Long id){
        return ResponseEntity.ok(fileMapper.toFileResponse(fileService.restore(id),appProperties));
    }

    @DeleteMapping("/delete-from-trash/{id}")
    public ResponseEntity<FileResponse> deleteFromTrash(@PathVariable("id") Long id){
        return ResponseEntity.ok(fileMapper.toFileResponse(fileService.deleteFromTrash(id),appProperties));
    }


}
