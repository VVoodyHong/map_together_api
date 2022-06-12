package com.mapto.api.app.placereply.repository;

import com.mapto.api.app.placereply.entity.PlaceReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlaceReplyRepositoryCustom {
    Page<PlaceReply> findByRequestPage(Pageable pageable, Long placeIdx);
}
