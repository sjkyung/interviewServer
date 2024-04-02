package com.beside.interviewserver.domain.bookmark.repository;

import com.beside.interviewserver.domain.bookmark.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository  extends JpaRepository<Bookmark,Long> {

    List<Bookmark> findAllByUserUserId(Long userId);
    void deleteByUserUserIdAndRecruit_PositionId(Long userId, String positionId);
}
