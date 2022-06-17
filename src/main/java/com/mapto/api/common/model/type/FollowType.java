package com.mapto.api.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FollowType {
    FOLLOWING("FOLLOWING"),
    FOLLOWER("FOLLOWER");

    private final String type;
}
