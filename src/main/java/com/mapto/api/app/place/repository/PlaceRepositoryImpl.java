package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.dto.PlaceDTO;
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

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PlaceDTO.Simple> findByKeywordAndAddress(Long userIdx, Pageable pageable, String keyword, String address) {
        List<PlaceDTO.Simple> list = jpaQueryFactory
                .select(Projections.fields(PlaceDTO.Simple.class,
                        place.idx,
                        place.user.idx.as("userIdx"),
                        place.user.nickname.as("userNickname"),
                        place.user.profileImg.as("userProfileImg"),
                        place.category.idx.as("placeCategoryIdx"),
                        place.category.name.as("placeCategoryName"),
                        place.category.type.as("placeCategoryType"),
                        place.name,
                        place.address,
                        place.description,
                        place.favorite,
                        place.lat,
                        place.lng,
                        place.representImg,
                        place.createAt,
                        place.updateAt))
                .from(place)
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
                .orderBy(place.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalSize = jpaQueryFactory
                .select(place.count())
                .from(place)
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
                .fetchFirst();

        return new PageImpl<>(list, pageable, totalSize);
    }
}