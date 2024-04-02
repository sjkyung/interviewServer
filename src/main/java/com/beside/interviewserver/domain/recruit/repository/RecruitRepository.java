package com.beside.interviewserver.domain.recruit.repository;

import com.beside.interviewserver.domain.bookmark.Bookmark;
import com.beside.interviewserver.domain.recruit.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitRepository extends JpaRepository<Recruit,Long> {
}
