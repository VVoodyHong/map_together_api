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
    public Page<User> findByKeyword(Long userIdx, Pageable pageable, String keyword) {
        List<User> list = jpaQueryFactory
                .selectFrom(user)
                .where(user.idx.ne(userIdx).and(user.name.contains(keyword).or(user.nickname.contains(keyword))))
                .orderBy(user.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = jpaQueryFactory
                .selectFrom(user)
                .where(user.idx.ne(userIdx).and(user.name.contains(keyword).or(user.nickname.contains(keyword))))
                .orderBy(user.idx.desc())
                .fetch()
                .size();

        return new PageImpl<>(list, pageable, totalSize);
    }

    @Override
    public Page<User> findByIdxInAndKeyword(List<Long> userIdxList, String keyword, Pageable pageable) {
        List<User> list = jpaQueryFactory
                .selectFrom(user)
                .where(user.idx.in(userIdxList).and(user.name.contains(keyword).or(user.nickname.contains(keyword))))
                .orderBy(user.nickname.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = jpaQueryFactory
                .selectFrom(user)
                .where(user.idx.in(userIdxList).and(user.name.contains(keyword).or(user.nickname.contains(keyword))))
                .orderBy(user.nickname.desc())
                .fetch()
                .size();

        return new PageImpl<>(list, pageable, totalSize);
    }
}