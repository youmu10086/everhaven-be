package com.cyf.everhavenbe.exception;

import com.cyf.everhavenbe.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 忽略静态资源未找到的异常（如 favicon.ico），避免打印 ERROR 日志
    @ExceptionHandler(NoResourceFoundException.class)
    public Object handleNoResourceFoundException(NoResourceFoundException e, HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        
        // 如果 Accept 头不包含 application/json，或者明确包含图片/通用类型
        if (acceptHeader != null && !acceptHeader.contains("application/json")) {
            // 对于非 JSON 请求，返回 null 让 Spring 处理（通常会导致 404 响应），
            // 避免尝试将 Result 对象转换为不支持的媒体类型
            return null;
        }
        
        logger.debug("静态资源未找到：{}", e.getResourcePath());
        return Result.error(404, "资源未找到: " + e.getResourcePath());
    }

    // 处理 @Pattern 等注解在方法参数上的校验失败（Spring 6.2+）
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Result<Void> handleHandlerMethodValidation(HandlerMethodValidationException e) {
        logger.warn("方法参数校验失败：{}", e.getMessage());

        // 提取第一个校验失败的信息 (使用 Spring 6.2+ 推荐的 getParameterValidationResults 替代已弃用的 getAllValidationResults)
        String message = e.getParameterValidationResults().stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("参数校验失败");

        return Result.error(message);
    }

    // 处理简单类型参数校验失败（兼容旧版本）
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolation(ConstraintViolationException e) {
        logger.warn("约束违反：{}", e.getMessage());
        String message = e.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .findFirst()
                .orElse("参数校验失败");
        return Result.error(message);
    }

    // 处理 @RequestBody 参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        logger.warn("请求体校验失败：{}", e.getMessage());
        String message = e.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("参数校验失败");
        return Result.error(message);
    }

    // 兜底异常处理
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        logger.error("系统异常：", e);
        String message = e.getMessage();
        // 增加更详细的错误原因输出到前端，方便调试
        String detailMessage = e.getClass().getSimpleName() + ": " + (StringUtils.hasLength(message) ? message : "未捕获的系统异常");
        return Result.error(detailMessage);
    }
}