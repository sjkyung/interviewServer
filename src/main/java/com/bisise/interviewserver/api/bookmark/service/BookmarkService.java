package com.bisise.interviewserver.api.bookmark.service;

import com.bisise.interviewserver.api.bookmark.dto.response.BookmarkResponse;
import com.bisise.interviewserver.api.bookmark.dto.request.BookmarkRequest;
import com.bisise.interviewserver.domain.bookmark.Bookmark;
import com.bisise.interviewserver.domain.bookmark.repository.BookmarkRepository;
import com.bisise.interviewserver.domain.recruit.Recruit;
import com.bisise.interviewserver.domain.recruit.repository.RecruitRepository;
import com.bisise.interviewserver.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final RecruitRepository recruitRepository;

    @Transactional
    public List<BookmarkResponse> getBookmarks(Long userId) {
        List <BookmarkResponse> bookmarks =
                bookmarkRepository.findAllByUserUserId(userId).stream().map(bookmark ->
                    BookmarkResponse.of(bookmark.getRecruit())
                ).toList();
        return bookmarks;
    }

    @Transactional
    public void addBookmark(BookmarkRequest bookmarkRequest) {
        Recruit recruit = recruitRepository.save(
                Recruit.createRecruit(bookmarkRequest.title(),
                        bookmarkRequest.company(),
                        bookmarkRequest.experienceRange(),
                        bookmarkRequest.deadline(),
                        bookmarkRequest.thumbnail(),
                        bookmarkRequest.positionId()
                )
        );
        // 파라미터는 검증이 안되었는데 바로 insert? --> findById ?
        // JWT토큰에서 빼낸다면 ? --> 검증된 회원 ..
        User user = User.createById(bookmarkRequest.userId());
        // 유저정보 , 채용공공정보 , 채용공고 정보 인서트
        bookmarkRepository.save(Bookmark.createBookmark(user, recruit));
    }
}
