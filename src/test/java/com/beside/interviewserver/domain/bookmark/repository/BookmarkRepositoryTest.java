package com.beside.interviewserver.domain.bookmark.repository;

import com.beside.interviewserver.domain.bookmark.Bookmark;
import com.beside.interviewserver.domain.recruit.Recruit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookmarkRepositoryTest {
    @Autowired private BookmarkRepository repository;

    @Test
    @Transactional
    public void test() {
        List<Bookmark> allByUserId = repository.findAllByUserUserId(1L);
        allByUserId.forEach(bookmark -> {
            Recruit recruit = bookmark.getRecruit();
            System.out.println("recruit = " + recruit.getTitle());
            System.out.println("recruit = " + recruit.getCompany());
        });

    }
}