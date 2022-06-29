package com.mapto.api.app.file.repository;

import com.mapto.api.app.file.entity.File;

import java.util.List;
import java.util.Optional;

public interface FileRepositoryCustom {
    List<File> findByPlaceIdx(Long placeIdx);
    Optional<File> findFirstByPlaceIdx(Long placeIdx);
    List<File> findByUserIdx(Long userIdx);
    void deleteByPlaceIdx(Long placeIdx);
    void deleteAllByUserIdx(Long userIdx);
}
