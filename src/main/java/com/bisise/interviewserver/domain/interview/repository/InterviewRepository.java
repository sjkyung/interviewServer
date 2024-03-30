package com.bisise.interviewserver.domain.interview.repository;

import com.bisise.interviewserver.domain.interview.Interview;
import com.bisise.interviewserver.domain.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByUserId(Long userId);
}
