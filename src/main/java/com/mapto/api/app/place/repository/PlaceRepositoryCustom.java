package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.dto.PlaceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceRepositoryCustom {
    Page<PlaceDTO.Simple> findByKeywordAndAddress(Long userIdx, Pageable pageable, String keyword, String address);
    Page<PlaceDTO.Simple> findByUserIdx(Long userIdx, String address, Pageable pageable);
}
