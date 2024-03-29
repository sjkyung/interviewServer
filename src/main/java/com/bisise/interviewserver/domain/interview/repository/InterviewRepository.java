package com.bisise.interviewserver.domain.interview.repository;

import com.bisise.interviewserver.domain.interview.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
