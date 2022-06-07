package com.mapto.api.app.tag.dto;

import lombok.Data;

public class TagDTO {

    @Data
    public static class Basic {
        private Long idx;
        private String name;
    }
}
