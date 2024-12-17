package com.menglang.bong_rumluos.Bong_rumluos.services.file;

import com.menglang.bong_rumluos.Bong_rumluos.dto.file.FileRequest;
import com.menglang.bong_rumluos.Bong_rumluos.entities.File;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.NotFoundException;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.FileRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private final FileRepository fileRepository;
    private final StorageService storageService;

    @Override
    public File upload(MultipartFile file) throws BadRequestException {
        try {
            String filename = storageService.upload(file);
            File fileRes = File.builder()
                    .size(file.getSize())
                    .type(file.getContentType())
                    .originalName(file.getOriginalFilename())
                    .name(filename)
                    .deletedAt(null)
                    .build();
            return fileRepository.save(fileRes);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<File> batchUpload(List<MultipartFile> files) throws BadRequestException {
        List<File> multiFiles = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                File fileRes = this.upload(file);
                multiFiles.add(fileRes);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return multiFiles;
    }

    @Override
    public Page<File> findAll(int page, int pageSize, boolean isTrash, String query) throws BadRequestException {
        Sort sort=Sort.by(Sort.Direction.DESC,"name");
        Pageable pageable= PageRequest.of(page-1,pageSize,sort);
        return fileRepository.findAllByOriginalNameContainsIgnoreCaseAndDeletedAtIsNull(query,pageable);

    }

    @Override
    public File restore(Long id) throws BadRequestException {
        File fileUpdate = fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File Not found"));
        fileUpdate.setDeletedAt(null);
        try {
            fileRepository.save(fileUpdate);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
        return null;
    }

    @Override
    public File updateFileName(Long id, FileRequest fileRequest) throws BadRequestException {
        try {
            File fileUpdate = fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File Not found"));
            fileUpdate.setOriginalName(fileRequest.fileName());
            log.info(" file request: {}, file update: {}",fileRequest.fileName(),fileUpdate.getOriginalName());
            return fileRepository.save(fileUpdate);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public File delete(Long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File Not Found"));
        try {
            file.setDeletedAt(LocalDateTime.now());
            return fileRepository.save(file);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    @Override
    public File deleteFromTrash(Long id) throws BadRequestException {
        File file = fileRepository.findById(id).orElseThrow(() -> new NotFoundException("File Not Found"));
        try {
            storageService.deleteFile(file.getName());
            fileRepository.delete(file);
            return file;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public File forceDelete(Long id) throws BadRequestException {
        return deleteFromTrash(id);
    }
}
