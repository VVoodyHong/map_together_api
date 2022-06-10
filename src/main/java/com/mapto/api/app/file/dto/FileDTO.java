package com.mapto.api.app.file.dto;

import com.mapto.api.common.model.type.FileType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class FileDTO {

    @Data
    public static class Basic {
        private Long idx;
        private String name;
        private FileType type;
        private String url;
    }

    @Data
    public static class Simple {
        private Long idx;
        private String url;
    }

    @Data
    public static class Simples {
        private List<Simple> list = new ArrayList<>();
    }
}
