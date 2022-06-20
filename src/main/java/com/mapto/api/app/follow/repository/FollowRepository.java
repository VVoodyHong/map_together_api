package com.mapto.api.app.follow.repository;

import com.mapto.api.app.follow.entity.Follow;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FollowRepository extends CrudRepository<Follow, Long>, FollowRepositoryCustom {
    void deleteByFromUserIdxAndToUserIdx(Long fromUserIdx, Long toUserIdx);
    Optional<Follow> findByFromUserIdxAndToUserIdx(Long fromUserIdx, Long toUserIdx);
    Long countByFromUserIdx(Long fromUserIdx);
    Long countByToUserIdx(Long toUserIdx);
}
