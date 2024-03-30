package com.bisise.interviewserver.api.resume;

import com.bisise.interviewserver.api.interview.dto.request.StartRequest;
import com.bisise.interviewserver.api.interview.dto.response.StartResponse;
import com.bisise.interviewserver.api.resume.dto.request.ResultRequest;
import com.bisise.interviewserver.api.resume.dto.response.AnalysisResponse;
import com.bisise.interviewserver.api.resume.service.ResumeService;
import com.bisise.interviewserver.common.ApiResponseUtil;
import com.bisise.interviewserver.common.BaseResponse;
import com.bisise.interviewserver.common.message.SuccessMessage;
import com.bisise.interviewserver.domain.resume.Resume;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("resume")
@RequiredArgsConstructor
@Tag(name = "Resume Controller", description = "자소서 관련 API")
public class ResumeApiController {
    private final ResumeService resumeService;


    @Operation(
            security = @SecurityRequirement(name = "Authorization"),
            summary = "자소서 답변 ",
            description = "자소서 답볍 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청이 성공했습니다.",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    @PostMapping("start")
    public ResponseEntity<BaseResponse<?>> start(@RequestBody StartRequest request){
        AnalysisResponse analysisResponse = resumeService.analysisResponse(request);
        return ApiResponseUtil.success(SuccessMessage.OK,analysisResponse);
    }


    @Operation(
            security = @SecurityRequirement(name = "Authorization"),
            summary = "자소서 결과 ",
            description = "자소서 결과 API",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "요청이 성공했습니다.",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    @PostMapping("result")
    public ResponseEntity<BaseResponse<?>> result(@RequestBody ResultRequest request){
        resumeService.result(request);
        return ApiResponseUtil.success(SuccessMessage.CREATED);
    }


    @Operation(
            security = @SecurityRequirement(name = "Authorization"),
            summary = "자소서 리스트(회원)",
            description = "자소서 리스트 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "요청이 성공했습니다.",
                            content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    @GetMapping("list")
    public ResponseEntity<BaseResponse<?>> list(Long userId){
        List<Resume> list = resumeService.list(userId);
        return ApiResponseUtil.success(SuccessMessage.OK,list);
    }
}
