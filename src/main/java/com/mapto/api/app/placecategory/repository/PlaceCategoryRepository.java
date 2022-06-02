package com.mapto.api.app.placecategory.repository;

import com.mapto.api.app.placecategory.entity.PlaceCategory;
import org.springframework.data.repository.CrudRepository;

public interface PlaceCategoryRepository extends CrudRepository<PlaceCategory, Long> {
}
