package com.menglang.bong_rumluos.Bong_rumluos.dto.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.file.fileSize.FileSizeResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class FileResponse extends BaseAuditDTO {
    @JsonProperty(index = 1)
    private Long id;
    @JsonProperty(index = 2)
    private String name;
    @JsonProperty(index = 3)
    private String originalName;
    @JsonProperty(index = 4)
    private String url;
    @JsonProperty(index = 5)
    private FileSizeResponse size;
    @JsonProperty(index = 6)
    private String type;
    @JsonProperty(index = 7)
    private Boolean isTrash;

    public FileResponse(LocalDate createdAt, String createdBy, LocalDate updatedAt, String updatedBy) {
        super(createdAt, createdBy, updatedAt, updatedBy);
    }
}
