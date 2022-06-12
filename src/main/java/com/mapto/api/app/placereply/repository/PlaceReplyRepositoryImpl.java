package com.mapto.api.app.placereply.repository;

import com.mapto.api.app.placereply.entity.PlaceReply;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mapto.api.app.placereply.entity.QPlaceReply.placeReply;

@Repository
@RequiredArgsConstructor
public class PlaceReplyRepositoryImpl implements PlaceReplyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PlaceReply> findByRequestPage(Pageable pageable, Long placeIdx) {
        List<PlaceReply> list = jpaQueryFactory
                .selectFrom(placeReply)
                .where(placeReply.place.idx.eq(placeIdx))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = jpaQueryFactory
                .selectFrom(placeReply)
                .where(placeReply.place.idx.eq(placeIdx))
                .fetch()
                .size();

        return new PageImpl<>(list, pageable, totalSize);
    }
}