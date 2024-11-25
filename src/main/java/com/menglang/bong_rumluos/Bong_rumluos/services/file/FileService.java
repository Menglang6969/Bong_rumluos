package com.menglang.bong_rumluos.Bong_rumluos.services.file;

import com.menglang.bong_rumluos.Bong_rumluos.dto.file.FileRequest;
import com.menglang.bong_rumluos.Bong_rumluos.entities.File;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    public File upload(MultipartFile file) throws BadRequestException;
    public List<File> batchUpload(List<MultipartFile> files) throws BadRequestException;
    public Page<File> findAll(int page,int pageSize,boolean isTrash,String query) throws BadRequestException;
    public File restore(Long id) throws BadRequestException;
    public File updateFileName(Long id, FileRequest fileRequest) throws BadRequestException;
    public File delete(Long id);
    public File deleteFromTrash(Long id) throws BadRequestException;
    public File forceDelete(Long id) throws BadRequestException;
}
