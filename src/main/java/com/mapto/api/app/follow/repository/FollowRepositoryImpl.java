package com.mapto.api.app.follow.repository;

import com.mapto.api.app.follow.entity.Follow;
import com.mapto.api.common.model.type.FollowType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mapto.api.app.follow.entity.QFollow.follow;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Follow> findByUserIdx(Long userIdx, FollowType followType) {
        return jpaQueryFactory
                .selectFrom(follow)
                .where(followType.equals(FollowType.FOLLOWING) ?
                        follow.fromUser.idx.eq(userIdx) :
                        follow.toUser.idx.eq(userIdx))
                .fetch();
    }
}