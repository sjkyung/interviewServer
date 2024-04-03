package com.beside.interviewserver.domain.interview.repository;

import com.beside.interviewserver.domain.interview.Interview;
import com.beside.interviewserver.domain.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    @Query("SELECT interview FROM Interview interview WHERE interview.user.userId = :userId")
    List<Interview> findByUserId(Long userId);
}
