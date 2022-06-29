package com.mapto.api.app.user.dto;

import com.mapto.api.app.place.dto.PlaceDTO;
import com.mapto.api.common.model.RequestPage;
import com.mapto.api.common.model.type.LoginType;
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
        private LoginType loginType;
        private BigDecimal lat;
        private BigDecimal lng;
        private BigDecimal zoom;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
        private List<PlaceDTO.Basic> places = new ArrayList<>();
    }

    @Data
    public static class Simple {
        private Long idx;
        private String name;
        private String nickname;
        private String profileImg;
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

    @Data
    public static class Password {
        private String currentPassword;
        private String newPassword;
        private String confirmNewPassword;
    }

    @Data
    public static class Search {
        private String keyword;
        private RequestPage requestPage;
    }

    @Data
    public static class Simples {
        private List<Simple> list = new ArrayList<>();
        private Long totalCount;
        private boolean last;
    }
}
