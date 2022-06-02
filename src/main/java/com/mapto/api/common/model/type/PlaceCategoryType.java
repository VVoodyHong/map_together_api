package com.mapto.api.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceCategoryType {
    HEART("HEART");

    private final String type;
}
