package com.mapto.api.app.user.dto;

import com.mapto.api.app.place.dto.PlaceDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    @Data
    public static class Basic {
        private Long idx;
        private String loginId;
        private String name;
        private String nickname;
        private String profileImg;
        private String introduce;
        private BigDecimal lat;
        private BigDecimal lng;
        private BigDecimal zoom;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private List<PlaceDTO.Basic> places = new ArrayList<>();
    }

    @Data
    public static class Create {
        private String loginId;
        private String password;
    }

    @Data
    public static class Update {
        private String nickname;
        private String name;
        private String profileImg;
        private String introduce;
        private BigDecimal lat;
        private BigDecimal lng;
        private BigDecimal zoom;
    }
}
