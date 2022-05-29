package com.mapto.api.app.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class UserDTO {

    @Data
    public static class Simple {
        private Long idx;
        private String loginId;
        private String name;
        private String nickname;
        private String profileImg;
        private String introduce;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;
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
    }
}
