package com.mapto.api.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PlaceCategoryType {
    AIRPLANE("AIRPLANE"),
    BEER("BEER"),
    COFFEE("COFFEE"),
    DESSERT("DESSERT"),
    HEART("HEART"),
    MARKER("MARKER"),
    RICE("RICE"),
    SPORTS("SPORTS"),
    STAR("STAR");

    private final String type;
}
