package com.mapto.api.app.user.repository;

import com.mapto.api.app.user.dto.UserDTO;
import com.mapto.api.common.model.type.FollowType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    Page<UserDTO.Simple> findByKeyword(Long userIdx, Pageable pageable, String keyword);
    Page<UserDTO.Simple> findByUserIdxAndFollowTypeAndKeyword(Long userIdx, FollowType followType, String keyword, Pageable pageable);
}
