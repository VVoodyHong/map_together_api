package com.mapto.api.app.auth.dto;

import com.mapto.api.common.model.type.LoginType;
import com.mapto.api.common.model.type.OsType;
import com.mapto.api.common.model.type.UserType;
import lombok.Data;

public class AuthDTO {
    @Data
    public static class Create {
        private String loginId;
        private String password;
        private UserType userType;
        private LoginType loginType;
        private OsType osType;
        private String appVersion;
        private Integer osVersion;
        private String deviceId;
    }

    @Data
    public static class Email {
        private String email;
        private String code;
    }
}
