package com.mapto.api.app.placereply.repository;

import com.mapto.api.app.placereply.dto.PlaceReplyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceReplyRepositoryCustom {
    Page<PlaceReplyDTO.Simple> findByPlaceIdx(Pageable pageable, Long placeIdx);
}
