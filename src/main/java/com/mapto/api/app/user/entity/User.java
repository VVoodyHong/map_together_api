package com.mapto.api.app.user.entity;

import com.mapto.api.app.user.dto.UserDTO;
import com.mapto.api.common.model.DateAudit;
import com.mapto.api.common.model.type.LoginType;
import com.mapto.api.common.model.type.OsType;
import com.mapto.api.common.model.type.UserType;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String loginId;
    private String name;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private LoginType loginType;
    @Enumerated(EnumType.STRING)
    private OsType osType;
    private String appVersion;
    private Integer osVersion;
    private String deviceId;

    @Builder
    public User(
            Long idx,
            String loginId,
            String name,
            String nickname,
            String password,
            UserType userType,
            LoginType loginType,
            OsType osType,
            String appVersion,
            Integer osVersion,
            String deviceId
    ) {
        this.idx = idx;
        this.loginId = loginId;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.userType = userType;
        this.loginType = loginType;
        this.osType = osType;
        this.appVersion = appVersion;
        this.osVersion = osVersion;
        this.deviceId =deviceId;
    }

    public void setName(String name) { this.name = name; }

    public void setNickname(String nickName) {
        this.nickname = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public void setOsType(OsType osType) {
        this.osType = osType;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setOsVersion(Integer osVersion) {
        this.osVersion = osVersion;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public UserDTO.Simple toUserSimpleDTO() {
        return new ModelMapper().map(this, UserDTO.Simple.class);
    }

}
