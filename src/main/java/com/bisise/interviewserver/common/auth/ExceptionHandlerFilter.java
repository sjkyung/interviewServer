package com.bisise.interviewserver.common.auth;

import com.bisise.interviewserver.common.BaseResponse;
import com.bisise.interviewserver.common.exception.UnauthorizedException;
import com.bisise.interviewserver.common.message.ErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }catch (UnauthorizedException e){
            handleUnAuthorizationException(response, e);
        }catch (Exception ee){
            handleException(response, ee);
        }
    }


    private void handleUnAuthorizationException(HttpServletResponse response, Exception e) throws IOException {
        UnauthorizedException unauthorizedException = (UnauthorizedException) e;
        ErrorMessage errorMessage = unauthorizedException.getErrorMessage();
        HttpStatus httpStatus = errorMessage.getHttpStatus();

        setResponse(response, httpStatus, errorMessage);
    }


    private void setResponse(HttpServletResponse response, HttpStatus httpStatus, ErrorMessage errorMessage) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(httpStatus.value());
        PrintWriter printWriter = response.getWriter();
        printWriter.write(objectMapper.writeValueAsString(BaseResponse.of(errorMessage)));
    }

    private void handleException(HttpServletResponse response,Exception e) throws IOException {
        log.error(" >>> Exception Handle filter ", e);
        setResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.INTERNAL_SERVER_ERROR);
    }
}
