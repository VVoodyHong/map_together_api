package com.mapto.api.app.follow.dto;

import com.mapto.api.app.user.dto.UserDTO;
import com.mapto.api.app.user.entity.User;
import com.mapto.api.common.model.RequestPage;
import com.mapto.api.common.model.type.FollowType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class FollowDTO {

    @Data
    public static class Basic {
        private Long idx;
        private User fromUser;
        private User toUser;
    }

    @Data
    public static class Simple {
        private Long userIdx;
        private Long userName;
        private String userNickname;
        private String userProfileImg;
    }

    @Data
    public static class Count {
        private Long following;
        private Long follower;
    }

    @Data
    public static class State {
        private boolean follow;
    }

    @Data
    public static class Search {
        private String keyword;
        private RequestPage requestPage;
        private FollowType followType;
        private Long userIdx;
    }

    @Data
    public static class Simples {
        private List<UserDTO.Simple> list = new ArrayList<>();
        private Long totalCount;
        private boolean last;
    }
}
