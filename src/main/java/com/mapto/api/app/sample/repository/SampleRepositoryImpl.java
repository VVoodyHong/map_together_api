package com.mapto.api.app.sample.repository;

import com.mapto.api.app.sample.entity.QSample;
import com.mapto.api.app.sample.entity.Sample;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SampleRepositoryImpl implements SampleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Sample> findSamples(String keyword, Pageable pageable) {
        List<Sample> list = jpaQueryFactory
                .selectFrom(QSample.sample)
                .where(QSample.sample.sampleText.like(keyword))
                .orderBy(QSample.sample.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(list, pageable, list.size());
    }
}
