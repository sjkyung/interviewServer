package com.bisise.interviewserver.api.bookmark;

import com.bisise.interviewserver.api.bookmark.service.BookmarkService;
import com.bisise.interviewserver.api.bookmark.dto.request.BookmarkRequest;
import com.bisise.interviewserver.common.ApiResponseUtil;
import com.bisise.interviewserver.common.BaseResponse;
import com.bisise.interviewserver.common.auth.UserId;
import com.bisise.interviewserver.common.message.SuccessMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
            description = "북마크 한 채용공고 가져오기",
            security = @SecurityRequirement(name = "Authorization"))
    @GetMapping
    public ResponseEntity<BaseResponse<?>> getBookmarks(@Parameter(hidden = true) @UserId Long userId) {
        return ApiResponseUtil.success(SuccessMessage.OK,bookmarkService.getBookmarks(userId));
    }

    @Operation(summary = "북마크 한 채용공고 삭제하기",
            description = "북마크 한 채용공고 삭제하기",
            security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/removal")
    public ResponseEntity<BaseResponse<?>> deleteBookmark(@Parameter(hidden = true) @UserId Long userId, String positionId) {
        bookmarkService.deleteBookmark(userId, positionId);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }

    @Operation(summary = "채용공고 북마크",
            description = "채용공고 북마크",
            security = @SecurityRequirement(name = "Authorization"))
    @PostMapping
    public ResponseEntity<BaseResponse<?>> addBookmark(@Parameter(hidden = true) @UserId Long userId,@RequestBody BookmarkRequest recruitRequest) {
        bookmarkService.addBookmark(userId, recruitRequest);
        return ApiResponseUtil.success(SuccessMessage.OK);
    }
}
