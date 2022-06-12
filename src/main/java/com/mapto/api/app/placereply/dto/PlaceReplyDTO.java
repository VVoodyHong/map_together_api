package com.mapto.api.app.placereply.dto;

import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.user.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlaceReplyDTO {

    @Data
    public static class Basic {
        private Long idx;
        private String reply;
        private Place place;
        private User user;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }

    @Data
    public static class Simple {
        private Long idx;
        private String reply;
        private Long userIdx;
        private String userNickname;
        private String userProfileImg;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
    }

    @Data
    public static class Create {
        private String reply;
        private Long placeIdx;
    }

    @Data
    public static class Simples {
        private List<Simple> list = new ArrayList<>();
        private Long totalCount;
        private boolean last;
    }
}
