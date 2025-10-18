// src/main/java/com/ck/labmanagesystem/config/GlobalExceptionHandler.java
package com.ck.labmanagesystem.config;

import com.ck.labmanagesystem.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数验证失败: {}", errorMessage);
        return ResultVO.error(400, errorMessage);
    }

    @ExceptionHandler(BindException.class)
    public ResultVO<String> handleBindException(BindException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("绑定参数失败: {}", errorMessage);
        return ResultVO.error(400, errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultVO<String> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常", e);
        return ResultVO.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVO<String> handleException(Exception e) {
        log.error("系统异常", e);
        return ResultVO.error("系统异常，请联系管理员");
    }
}
