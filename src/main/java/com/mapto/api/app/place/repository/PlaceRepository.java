package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.entity.Place;
import org.springframework.data.repository.CrudRepository;

public interface PlaceRepository extends CrudRepository<Place, Long> {
}
