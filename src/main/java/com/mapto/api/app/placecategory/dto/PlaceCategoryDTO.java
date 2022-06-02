package com.mapto.api.app.placecategory.dto;

import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.type.PlaceCategoryType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlaceCategoryDTO {

    @Data
    public static class Basic {
        private Long idx;
        private User user;
        private String name;
        private PlaceCategoryType type;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }

    @Data
    public static class Simple {
        private Long idx;
        private String name;
        private PlaceCategoryType type;
    }

    @Data
    public static class Create {
        private String name;
        private PlaceCategoryType type;
    }

    @Data
    public static class Simples {
        private List<Simple> list = new ArrayList<>();
    }
}
