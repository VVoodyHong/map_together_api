package com.mapto.api.app.place.dto;

import com.mapto.api.app.file.dto.FileDTO;
import com.mapto.api.app.placecategory.entity.PlaceCategory;
import com.mapto.api.app.tag.dto.TagDTO;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.RequestPage;
import com.mapto.api.common.model.type.PlaceCategoryType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlaceDTO {

    @Data
    public static class Basic {
        private Long idx;
        private User user;
        private PlaceCategory category;
        private String name;
        private String address;
        private String description;
        private Double favorite;
        private BigDecimal lat;
        private BigDecimal lng;
        private String representImg;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }

    @Data
    public static class Simple {
        private Long idx;
        private Long userIdx;
        private String userNickname;
        private String userProfileImg;
        private Long placeCategoryIdx;
        private String placeCategoryName;
        private PlaceCategoryType placeCategoryType;
        private String name;
        private String address;
        private String description;
        private Double favorite;
        private BigDecimal lat;
        private BigDecimal lng;
        private String representImg;
        private Long likeCount;
        private Long replyCount;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }

    @Data
    public static class Create {
        private Long categoryIdx;
        private String name;
        private String address;
        private String description;
        private List<String> tags = new ArrayList<>();
        private Double favorite;
        private BigDecimal lat;
        private BigDecimal lng;
    }

    @Data
    public static class Update {
        private Long idx;
        private Long categoryIdx;
        private String name;
        private String address;
        private String description;
        private List<TagDTO.Simple> addTags = new ArrayList<>();
        private List<TagDTO.Simple> deleteTags = new ArrayList<>();
        private List<FileDTO.Simple> deleteFiles = new ArrayList<>();
        private Double favorite;
        private BigDecimal lat;
        private BigDecimal lng;
    }

    @Data
    public static class Search {
        private String keyword;
        private String address;
        private RequestPage requestPage;
    }

    @Data
    public static class Simples {
        private List<Simple> list = new ArrayList<>();
        private Long totalCount;
        private boolean last;
    }
}
