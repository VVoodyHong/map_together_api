package com.mapto.api.app.user.repository;

import com.mapto.api.app.user.dto.UserDTO;
import com.mapto.api.common.model.type.FollowType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mapto.api.app.user.entity.QUser.user;
import static com.mapto.api.app.follow.entity.QFollow.follow;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<UserDTO.Simple> findByKeyword(Long userIdx, Pageable pageable, String keyword) {
        List<UserDTO.Simple> list = jpaQueryFactory
                .select(Projections.fields(UserDTO.Simple.class,
                        user.idx,
                        user.name,
                        user.nickname,
                        user.profileImg))
                .from(user)
                .where(user.idx.ne(userIdx).and(user.name.contains(keyword).or(user.nickname.contains(keyword))))
                .orderBy(user.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalSize = jpaQueryFactory
                .select(user.count())
                .from(user)
                .where(user.idx.ne(userIdx).and(user.name.contains(keyword).or(user.nickname.contains(keyword))))
                .fetchFirst();

        return new PageImpl<>(list, pageable, totalSize);
    }

    @Override
    public Page<UserDTO.Simple> findByUserIdxAndFollowTypeAndKeyword(Long userIdx, FollowType followType, String keyword, Pageable pageable) {
        NumberPath<Long> joinIdx = followType.equals(FollowType.FOLLOWER) ? follow.fromUser.idx : follow.toUser.idx;
        NumberPath<Long> whereIdx = followType.equals(FollowType.FOLLOWER) ? follow.toUser.idx : follow.fromUser.idx;

        List<UserDTO.Simple> list = jpaQueryFactory
                .select(Projections.fields(UserDTO.Simple.class,
                        user.idx,
                        user.name,
                        user.nickname,
                        user.profileImg))
                .from(user)
                .innerJoin(follow)
                    .on(user.idx.eq(joinIdx))
                .where((whereIdx.eq(userIdx)).and(user.name.contains(keyword).or(user.nickname.contains(keyword))))
                .orderBy(user.nickname.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalSize = jpaQueryFactory
                .select(user.count())
                .from(user)
                .innerJoin(follow)
                    .on(user.idx.eq(joinIdx))
                .where((whereIdx.eq(userIdx)).and(user.name.contains(keyword).or(user.nickname.contains(keyword))))
                .fetchFirst();

        return new PageImpl<>(list, pageable, totalSize);
    }
}