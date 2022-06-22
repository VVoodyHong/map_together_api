package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.entity.PlaceTag;
import org.springframework.data.repository.CrudRepository;

public interface PlaceTagRepository extends CrudRepository<PlaceTag, Long> {
    void deleteByPlaceIdxAndTagIdx(Long placeIdx, Long tagIdx);
}
