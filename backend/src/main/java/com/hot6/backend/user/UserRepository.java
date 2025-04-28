package com.hot6.backend.user;


import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByIdx(Long idx);

    Optional<User> findByUserType(UserType userType);

    boolean existsByNickname(String nickname);
}
