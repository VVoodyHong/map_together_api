package com.mapto.api.app.tag.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class TagDTO {

    @Data
    public static class Basic {
        private Long idx;
        private String name;
    }

    @Data
    public static class Simple {
        private Long idx;
        private String name;
    }

    @Data
    public static class Simples {
        private List<Simple> list = new ArrayList<>();
    }
}
