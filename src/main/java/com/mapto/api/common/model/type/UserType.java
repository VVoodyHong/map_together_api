package com.mapto.api.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    USER("USER", "유저"),
    ADMIN("ADMIN", "관리자");

    private final String role;
    private final String description;
}
