package com.mapto.api.app.tag.repository;

import com.mapto.api.app.tag.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long>, TagRepositoryCustom {
    Optional<Tag> findTagByName(String name);
}
