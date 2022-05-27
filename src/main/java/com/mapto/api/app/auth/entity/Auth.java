package com.mapto.api.app.auth.entity;

import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.type.UserType;
import lombok.Data;

import java.io.Serializable;


@Data
public class Auth implements Serializable {
    private Long idx;
    private UserType role = UserType.USER;
    private User user;
}
