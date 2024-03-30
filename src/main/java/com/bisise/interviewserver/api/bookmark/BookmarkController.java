package com.bisise.interviewserver.api.bookmark;

import com.bisise.interviewserver.api.bookmark.service.BookmarkService;
import com.bisise.interviewserver.api.bookmark.dto.request.BookmarkRequest;
import com.bisise.interviewserver.common.ApiResponseUtil;
import com.bisise.interviewserver.common.BaseResponse;
import com.bisise.interviewserver.common.message.SuccessMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 한 채용공고",
            description = "북마크 한 채용공고 가져오기")
    @GetMapping
    public ResponseEntity<BaseResponse<?>> getBookmarks(@RequestParam Long userId) {
        return ApiResponseUtil.success(SuccessMessage.OK,bookmarkService.getBookmarks(userId));
    }

    @Operation(summary = "북마크 한 채용공고 삭제하기",
            description = "북마크 한 채용공고 삭제하기")
    @DeleteMapping
    public ResponseEntity<BaseResponse<?>> deleteBookmark(@RequestParam Long userId, String positionId) {
        bookmarkService.deleteBookmark(userId, positionId);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }

    @Operation(summary = "채용공고 북마크",
            description = "채용공고 북마크")
    @PostMapping
    public ResponseEntity<BaseResponse<?>> addBookmark(@RequestBody BookmarkRequest recruitRequest) {
        bookmarkService.addBookmark(recruitRequest);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }
}
