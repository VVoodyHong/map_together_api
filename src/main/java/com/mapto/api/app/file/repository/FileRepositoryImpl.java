package com.mapto.api.app.file.repository;

import com.mapto.api.app.file.entity.File;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mapto.api.app.file.entity.QFile.file;
import static com.mapto.api.app.place.entity.QPlaceFile.placeFile;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<File> findByPlaceIdx(Long placeIdx) {
        return jpaQueryFactory
                .selectFrom(file)
                .where(file.idx.in(
                        JPAExpressions.select(placeFile.file.idx)
                                .from(placeFile)
                                .where(placeFile.place.idx.eq(placeIdx))
                        ))
                .fetch();
    }

    @Override
    public Optional<File> findFirstByPlaceIdx(Long placeIdx) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(file)
                .where(file.idx.in(
                        JPAExpressions.select(placeFile.file.idx)
                                .from(placeFile)
                                .where(placeFile.place.idx.eq(placeIdx))
                ))
                .fetchFirst());
    }
}