package com.mapto.api.app.sample.repository;

import com.mapto.api.app.sample.entity.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SampleRepositoryCustom {
    Page<Sample> findSamples(String keyword, Pageable pageable);
}
