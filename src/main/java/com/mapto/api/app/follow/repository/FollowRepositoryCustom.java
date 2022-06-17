package com.mapto.api.app.follow.repository;

import com.mapto.api.app.follow.entity.Follow;
import com.mapto.api.common.model.type.FollowType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FollowRepositoryCustom {
    List<Follow> findByUserIdx(Long userIdx, FollowType followType);
}
