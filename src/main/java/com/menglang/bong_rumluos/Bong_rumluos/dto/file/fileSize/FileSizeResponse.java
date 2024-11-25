package com.menglang.bong_rumluos.Bong_rumluos.dto.file.fileSize;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileSizeResponse{
       private Long originalValue;
       private Long formatValue;
       private String formatType;
       private String normalized;

}
