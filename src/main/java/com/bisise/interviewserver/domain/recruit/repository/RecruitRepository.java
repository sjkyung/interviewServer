package com.bisise.interviewserver.domain.recruit.repository;

import com.bisise.interviewserver.domain.bookmark.Bookmark;
import com.bisise.interviewserver.domain.recruit.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitRepository extends JpaRepository<Recruit,Long> {
}
