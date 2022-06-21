package com.mapto.api.app.placereply.repository;

import com.mapto.api.app.placereply.dto.PlaceReplyDTO;
import com.querydsl.core.types.Projections;
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
    public Page<PlaceReplyDTO.Simple> findByPlaceIdx(Pageable pageable, Long placeIdx) {
        List<PlaceReplyDTO.Simple> list = jpaQueryFactory
                .select(Projections.fields(PlaceReplyDTO.Simple.class,
                        placeReply.idx,
                        placeReply.reply,
                        placeReply.user.idx.as("userIdx"),
                        placeReply.user.nickname.as("userNickname"),
                        placeReply.user.profileImg.as("userProfileImg"),
                        placeReply.createAt,
                        placeReply.updateAt))
                .from(placeReply)
                .where(placeReply.place.idx.eq(placeIdx))
                .orderBy(placeReply.idx.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalSize = jpaQueryFactory
                .select(placeReply.count())
                .from(placeReply)
                .where(placeReply.place.idx.eq(placeIdx))
                .fetchFirst();

        return new PageImpl<>(list, pageable, totalSize);
    }
}