package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.entity.PlaceFile;
import org.springframework.data.repository.CrudRepository;

public interface PlaceFileRepository extends CrudRepository<PlaceFile, Long> {
    void deleteByPlaceIdxAndFileIdx(Long placeIdx, Long fileIdx);
}
