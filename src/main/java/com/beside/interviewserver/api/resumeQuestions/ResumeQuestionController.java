package com.beside.interviewserver.api.resumeQuestions;

import com.beside.interviewserver.api.bookmark.dto.request.BookmarkRequest;
import com.beside.interviewserver.api.resumeQuestions.dto.request.ResumeQuestioRequest;
import com.beside.interviewserver.api.resumeQuestions.dto.response.ResumeQuestionResponse;
import com.beside.interviewserver.api.resumeQuestions.service.ResumeQuestionService;
import com.beside.interviewserver.common.ApiResponseUtil;
import com.beside.interviewserver.common.BaseResponse;
import com.beside.interviewserver.common.dto.PageableRequest;
import com.beside.interviewserver.common.message.SuccessMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/resume-question")
public class ResumeQuestionController {

    private final ResumeQuestionService resumeQuestionService;

    @Operation(summary = "자소서 문항 불러오기",
            description = "자소서 문항 불러오기")
    @GetMapping
    public ResponseEntity<BaseResponse<?>> getResumeQuestions(ResumeQuestioRequest resumeQuestioRequest, PageableRequest pageableRequest) {
        Page<ResumeQuestionResponse> resumeQuestions = resumeQuestionService.getResumeQuestions(resumeQuestioRequest, pageableRequest);
        return ApiResponseUtil.success(SuccessMessage.OK, resumeQuestions);
    }

}
