package com.menglang.bong_rumluos.Bong_rumluos.dto.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.menglang.bong_rumluos.Bong_rumluos.dto.BaseAuditDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.file.fileSize.FileSizeResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonPropertyOrder({"id", "name", "originalName", "url", "size", "type", "isTrash", "createdAt", "createdBy", "updatedAt", "updatedBy"})
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

    public FileResponse(Long id, LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        super(id, createdAt, createdBy, updatedAt, updatedBy);
    }
}
