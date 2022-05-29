package com.mapto.api.app.file.dto;

import com.mapto.api.common.model.type.FileType;
import lombok.Data;

public class FileDTO {

    @Data
    public static class Simple {
        private Long idx;
        private String name;
        private FileType type;
        private String url;
    }
}
