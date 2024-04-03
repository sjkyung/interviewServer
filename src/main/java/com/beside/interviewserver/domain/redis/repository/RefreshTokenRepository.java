package com.beside.interviewserver.domain.redis.repository;

import com.beside.interviewserver.domain.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
