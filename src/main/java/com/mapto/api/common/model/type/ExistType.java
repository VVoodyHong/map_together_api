package com.mapto.api.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExistType {
    LOGINID("LOGINID"),
    NICKNAME("NICKNAME");

    private final String type;
}
