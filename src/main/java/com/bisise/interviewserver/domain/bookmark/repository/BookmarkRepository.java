package com.bisise.interviewserver.domain.bookmark.repository;

import com.bisise.interviewserver.domain.bookmark.Bookmark;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository  extends JpaRepository<Bookmark,Long> {

    List<Bookmark> findAllByUserUserId(Long userId);
}
