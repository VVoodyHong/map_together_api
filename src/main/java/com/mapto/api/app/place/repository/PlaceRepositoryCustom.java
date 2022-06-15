package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {
    Page<Place> findByKeywordAndAddress(Long userIdx, Pageable pageable, String keyword, String address);
}
