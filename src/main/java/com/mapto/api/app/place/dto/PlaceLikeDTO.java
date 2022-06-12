package com.mapto.api.app.place.dto;

import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.user.entity.User;
import lombok.Data;

public class PlaceLikeDTO {
    @Data
    public static class Basic {
        private Long idx;
        private User user;
        private Place place;
    }

    @Data
    public static class Simple {
        private Long idx;
        private Long userIdx;
        private Long placeIdx;
        private boolean like;
        private Integer totalLike;
    }
}
