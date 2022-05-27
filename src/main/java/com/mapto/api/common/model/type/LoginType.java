package com.mapto.api.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {
    DEFAULT("DEFAULT"),
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String type;
}
