package com.guncat.ecommerce.common.exception;

import com.guncat.ecommerce.verification.exception.CodeNotMatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Application 내 발생하는 Exception 의 전역 처리기.
 *
 * @see ResponseEntityExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler {

    /**
     * Spring 내부 예외 처리 목적.<br/>
     * Spring 내부에서 발생한 Exception 을 일관된 형태로 처리하고 Client 에 응답하기 위함.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatusCode statusCode,
                                                             WebRequest request) {

        String message = "Unexpected error occurred. Please try again.";

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("error", ex.getClass().getSimpleName());
        responseBody.put("status", statusCode.value());

        return super.handleExceptionInternal(ex, responseBody, headers, statusCode, request);
    }

    /**
     * Client 가 입력한 이메일 인증 코드와 세션에 저장된 인증 코드가 일치하지 않을 경우 발생하는 예외 처리기.
     * @param e {@link CodeNotMatchException} 객체.
     */
    @ExceptionHandler(CodeNotMatchException.class)
    public ResponseEntity<String> handleCodeNotMatchException(CodeNotMatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
