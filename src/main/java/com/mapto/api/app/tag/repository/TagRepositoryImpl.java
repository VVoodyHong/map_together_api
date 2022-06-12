package com.mapto.api.app.tag.repository;

import com.mapto.api.app.tag.entity.Tag;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mapto.api.app.tag.entity.QTag.tag;
import static com.mapto.api.app.place.entity.QPlaceTag.placeTag;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Tag> findByPlaceIdx(Long placeIdx) {
        return jpaQueryFactory
                .selectFrom(tag)
                .where(tag.idx.in(
                        JPAExpressions.select(placeTag.tag.idx)
                                .from(placeTag)
                                .where(placeTag.place.idx.eq(placeIdx))
                        ))
                .fetch();
    }
}