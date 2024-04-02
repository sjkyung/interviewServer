package com.bisise.interviewserver.domain.redis.repository;

import com.bisise.interviewserver.domain.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
