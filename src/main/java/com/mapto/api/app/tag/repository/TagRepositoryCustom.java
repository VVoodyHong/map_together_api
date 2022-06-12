package com.mapto.api.app.tag.repository;

import com.mapto.api.app.tag.entity.Tag;

import java.util.List;

public interface TagRepositoryCustom {
    List<Tag> findByPlaceIdx(Long placeIdx);
}
