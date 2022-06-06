package com.mapto.api.app.place.dto;

import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.user.entity.User;
import lombok.Data;

public class PlaceFileDTO {
    @Data
    public static class Basic {
        private Long idx;
        private Place place;
        private User user;
    }
}
