package com.menglang.bong_rumluos.Bong_rumluos.dto.file;

import com.menglang.bong_rumluos.Bong_rumluos.constant.AppProperties;
import com.menglang.bong_rumluos.Bong_rumluos.dto.file.fileSize.FileSizeResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.File;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {
    FileMapper INSTANCE= Mappers.getMapper(FileMapper.class);

    @Mapping(target = "isTrash", source = "deletedAt", qualifiedByName = "mapTrash")
    @Mapping(target = "url",source = "name",qualifiedByName = "mapUrl")
    @Mapping(target = "size",source = "size", qualifiedByName = "mapSize")
    FileResponse toFileResponse(File file, @Context AppProperties appProperties);

    List<FileResponse> toListFileResponse(List<File> files,@Context AppProperties appProperties);
    @Named("mapUrl")
    default  String mapUrl(String fileName,@Context AppProperties appProperties){
        return appProperties.getApiUrl()+fileName;
    }

    @Named("mapSize")
    default FileSizeResponse mapSize(Long size){
        FileSizeResponse fileSizeResponse=new FileSizeResponse();
        Long sizeKb = size / 1024;
        String fileFormatType = "KB";
        fileSizeResponse.setFormatType(fileFormatType);
        fileSizeResponse.setFormatValue(sizeKb);
        fileSizeResponse.setNormalized(sizeKb + " " + fileFormatType);
        fileSizeResponse.setOriginalValue(size);
        return fileSizeResponse;
    }

    @Named("mapTrash")
    default boolean mapTrash(Date deletedAt) {
        return deletedAt != null;
    }
}
