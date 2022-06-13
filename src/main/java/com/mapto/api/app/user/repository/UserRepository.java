package com.mapto.api.app.user.repository;

import com.mapto.api.app.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {
    User findByIdx(Long idx);
    User findByLoginId(String loginId);
    User findByNickname(String nickName);
}
