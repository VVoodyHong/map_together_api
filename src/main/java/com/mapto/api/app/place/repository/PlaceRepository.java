package com.mapto.api.app.place.repository;

import com.mapto.api.app.place.entity.Place;
import com.mapto.api.app.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaceRepository extends CrudRepository<Place, Long> {
    List<Place> findByUser(User user);
}
