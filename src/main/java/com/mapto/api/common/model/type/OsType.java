package com.mapto.api.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OsType {
    ANDROID("ANDROID"),
    IOS("IOS");

    private final String type;
}
