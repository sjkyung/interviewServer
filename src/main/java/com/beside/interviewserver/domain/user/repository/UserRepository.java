package com.beside.interviewserver.domain.user.repository;

import com.beside.interviewserver.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPlatformId(String platformId);
    Optional<User> findByNick(String nick);

    boolean existsUserByEmail(String email);
}
