package com.ck.labmanagesystem.config;

import com.ck.labmanagesystem.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
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

    @ExceptionHandler({DuplicateKeyException.class, SQLIntegrityConstraintViolationException.class})
    public ResultVO<String> handleDuplicateKeyException(Exception e) {
        log.warn("数据重复异常: {}", e.getMessage());
        String message = e.getMessage().toLowerCase();
        if (message.contains("username")) {
            return ResultVO.error(400, "用户名已存在");
        } else if (message.contains("student_id")) {
            return ResultVO.error(400, "学号已存在");
        } else if (message.contains("teacher_id")) {
            return ResultVO.error(400, "工号已存在");
        }
        return ResultVO.error(400, "数据已存在，不能重复添加");
    }

    @ExceptionHandler(DataAccessException.class)
    public ResultVO<String> handleDataAccessException(DataAccessException e) {
        log.error("数据库访问异常", e);
        String message = e.getMessage();
        if (message != null) {
            message = message.toLowerCase();
            if (message.contains("data truncation") || message.contains("data too long")) {
                if (message.contains("student_id")) {
                    return ResultVO.error(400, "学号长度超过限制");
                } else if (message.contains("username")) {
                    return ResultVO.error(400, "用户名长度超过限制");
                } else if (message.contains("real_name")) {
                    return ResultVO.error(400, "姓名长度超过限制");
                } else if (message.contains("user_type")) {
                    return ResultVO.error(400, "用户类型长度超过限制");
                }
                return ResultVO.error(400, "输入数据长度超过限制");
            } else if (message.contains("invalid value")) {
                return ResultVO.error(400, "无效的数据值");
            }
        }
        return ResultVO.error(400, "数据库操作失败: " + (e.getMessage() != null ? e.getMessage() : ""));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultVO<String> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常", e);
        String message = e.getMessage();
        if (message != null) {
            // 处理自定义业务异常
            if (message.contains("已存在") || message.contains("不存在")) {
                return ResultVO.error(400, message);
            }
        }
        // 让其他RuntimeException继续传递，避免覆盖DataAccessException
        throw e;
    }

    @ExceptionHandler(Exception.class)
    public ResultVO<String> handleException(Exception e) {
        log.error("系统异常", e);
        return ResultVO.error(500, "系统异常，请联系管理员");
    }
}
