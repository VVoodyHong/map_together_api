package com.mapto.api.app.user.repository;

import com.mapto.api.app.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mapto.api.app.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<User> findByKeyword(Pageable pageable, String keyword) {
        List<User> list = jpaQueryFactory
                .selectFrom(user)
                .where(user.name.contains(keyword).or(user.nickname.contains(keyword)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = jpaQueryFactory
                .selectFrom(user)
                .where(user.name.contains(keyword).or(user.nickname.contains(keyword)))
                .fetch()
                .size();

        return new PageImpl<>(list, pageable, totalSize);
    }
}