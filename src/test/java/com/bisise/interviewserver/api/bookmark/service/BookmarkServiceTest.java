package com.bisise.interviewserver.api.bookmark.service;

import com.bisise.interviewserver.api.bookmark.dto.request.BookmarkRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookmarkServiceTest {
    @Autowired BookmarkService bookmarkService;

    @Test
    @Transactional
    public void test() {
        bookmarkService.getBookmarks(1L);
    }

    /*@Test
    @Transactional
    public void test2() {
        BookmarkRequest book = new BookmarkRequest(1L,"title", "compay","ex","00:02", "","");

        bookmarkService.addBookmark(book);
    }*/
}