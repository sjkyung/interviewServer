package com.bisise.interviewserver.domain.resume.repository;

import com.bisise.interviewserver.domain.resume.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("SELECT resume FROM Resume resume WHERE resume.user.userId = :userId")
    List<Resume> findByUserId(Long userId);
}
