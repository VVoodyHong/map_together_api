package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.entity.Place;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mapto.api.app.place.entity.QPlace.place;
import static com.mapto.api.app.place.entity.QPlaceTag.placeTag;
import static com.mapto.api.app.tag.entity.QTag.tag;
import static com.mapto.api.app.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Place> findByKeywordAndAddress(Long userIdx, Pageable pageable, String keyword, String address) {
        List<Place> list = jpaQueryFactory
                .selectFrom(place)
                .where(place.user.idx.ne(userIdx).and(place.address.contains(address)).and(place.name.contains(keyword)
                        .or(place.address.contains(keyword)
                                .or(place.idx.in(JPAExpressions
                                        .select(placeTag.place.idx)
                                        .from(placeTag)
                                        .where(placeTag.tag.idx.in(JPAExpressions
                                                .select(tag.idx)
                                                .from(tag)
                                                .where(tag.name.contains(keyword))
                                        )))
                                )
                        )
                ))
                .orderBy(user.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = jpaQueryFactory
                .selectFrom(place)
                .where((place.name.contains(keyword)
                        .or(place.address.contains(keyword)
                                .or(place.idx.in(JPAExpressions
                                        .select(placeTag.place.idx)
                                        .from(placeTag)
                                        .where(placeTag.tag.idx.in(JPAExpressions
                                                .select(tag.idx)
                                                .from(tag)
                                                .where(tag.name.contains(keyword))
                                        )))
                                )
                        )
                ).and(place.address.contains(address)))
                .orderBy(user.idx.desc())
                .fetch()
                .size();

        return new PageImpl<>(list, pageable, totalSize);
    }
}