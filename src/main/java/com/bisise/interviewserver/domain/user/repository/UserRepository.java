package com.bisise.interviewserver.domain.user.repository;

import com.bisise.interviewserver.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
