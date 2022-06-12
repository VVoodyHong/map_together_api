package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.entity.PlaceLike;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaceLikeRepository extends CrudRepository<PlaceLike, Long> {
    PlaceLike findByUserIdxAndPlaceIdx(Long userIdx, Long placeIdx);
    List<PlaceLike> findByPlaceIdx(Long placeIdx);
    void deleteByUserIdxAndPlaceIdx(Long userIdx, Long placeIdx);
}
