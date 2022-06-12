package com.mapto.api.app.placereply.repository;

import com.mapto.api.app.placereply.entity.PlaceReply;
import org.springframework.data.repository.CrudRepository;

public interface PlaceReplyRepository extends CrudRepository<PlaceReply, Long>, PlaceReplyRepositoryCustom {
    void deleteByIdx(Long idx);
}
