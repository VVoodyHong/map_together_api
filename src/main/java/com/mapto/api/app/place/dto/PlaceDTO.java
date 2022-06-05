package com.mapto.api.app.place.dto;

import com.mapto.api.app.placecategory.entity.PlaceCategory;
import com.mapto.api.app.user.entity.User;
import lombok.Data;

import java.math.BigDecimal;

public class PlaceDTO {

    @Data
    public static class Basic {
        private Long idx;
        private User user;
        private PlaceCategory category;
        private String name;
        private String address;
        private String description;
        private BigDecimal lat;
        private BigDecimal lng;
    }

    @Data
    public static class Create {
        private Long categoryIdx;
        private String name;
        private String address;
        private String description;
        private BigDecimal lat;
        private BigDecimal lng;
    }
}
