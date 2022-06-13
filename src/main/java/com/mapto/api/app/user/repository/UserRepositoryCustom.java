package com.mapto.api.app.user.repository;

import com.mapto.api.app.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    Page<User> findByKeyword(Pageable pageable, String keyword);
}
