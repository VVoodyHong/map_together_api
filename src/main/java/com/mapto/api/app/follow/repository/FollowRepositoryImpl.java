package com.mapto.api.app.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}