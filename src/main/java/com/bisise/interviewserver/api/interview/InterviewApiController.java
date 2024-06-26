package com.bisise.interviewserver.api.interview;

import com.bisise.interviewserver.api.interview.dto.request.QuestionRequest;
import com.bisise.interviewserver.api.interview.dto.request.InterviewResultRequest;
import com.bisise.interviewserver.api.interview.dto.request.StartRequest;
import com.bisise.interviewserver.api.interview.dto.response.ListResponse;
import com.bisise.interviewserver.api.interview.dto.response.StartResponse;
import com.bisise.interviewserver.api.interview.service.InterviewService;
import com.bisise.interviewserver.common.ApiResponseUtil;
import com.bisise.interviewserver.common.BaseResponse;
import com.bisise.interviewserver.common.message.SuccessMessage;
import com.bisise.interviewserver.domain.interview.Interview;
import com.bisise.interviewserver.external.openfeign.openai.OpenAiProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("interview")
@RequiredArgsConstructor
@Tag(name = "Interview Controller", description = "인터뷰 관련 API")
public class InterviewApiController {
    private final InterviewService interviewService;
    private final OpenAiProvider openAiProvider;


    @PostMapping("test")
    public String test(@RequestBody String prompt){
        return openAiProvider.getOpenAiPrompt(prompt);
    }

    @Operation(
            security = @SecurityRequirement(name = "Authorization"),
            summary = "인터뷰 답변 ",
            description = "인터뷰 답볍 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청이 성공했습니다.",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    @PostMapping("start")
    public ResponseEntity<BaseResponse<?>> start(@RequestBody StartRequest request){
        StartResponse startResponse = interviewService.startResponse(request);
        return ApiResponseUtil.success(SuccessMessage.OK,startResponse);
    }

    @Operation(
            security = @SecurityRequirement(name = "Authorization"),
            summary = "인터뷰 질문 ",
            description = "인터뷰 질문 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청이 성공했습니다.",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    @PostMapping("question")
    public ResponseEntity<BaseResponse<?>> question(@RequestBody QuestionRequest request){
        StartResponse startResponse = interviewService.questionResponse(request);
        return ApiResponseUtil.success(SuccessMessage.OK,startResponse);
    }

    @Operation(
            security = @SecurityRequirement(name = "Authorization"),
            summary = "인터뷰 결과 ",
            description = "인터뷰 결과 API",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "요청이 성공했습니다.",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    @PostMapping("result")
    public ResponseEntity<BaseResponse<?>> result(@RequestBody InterviewResultRequest request){
        interviewService.result(request);
        return ApiResponseUtil.success(SuccessMessage.CREATED);
    }


    @Operation(
            security = @SecurityRequirement(name = "Authorization"),
            summary = "인터뷰 리스트(회원)",
            description = "인터뷰 리스트 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청이 성공했습니다.",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    @GetMapping("list")
    public ResponseEntity<BaseResponse<?>> list(Long userId){
        List<ListResponse> list = interviewService.list(userId);
        return ApiResponseUtil.success(SuccessMessage.OK,list);
    }
}

