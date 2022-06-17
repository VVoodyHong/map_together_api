package com.mapto.api.app.user.repository;

import com.mapto.api.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryCustom {
    Page<User> findByKeyword(Long userIdx, Pageable pageable, String keyword);
    Page<User> findByIdxInAndKeyword(List<Long> userIdxList, String keyword, Pageable pageable);
}
