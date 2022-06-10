package com.mapto.api.app.file.repository;

import com.mapto.api.app.file.entity.File;

import java.util.List;

public interface FileRepositoryCustom {
    List<File> findByPlaceIdx(Long placeIdx);
}
