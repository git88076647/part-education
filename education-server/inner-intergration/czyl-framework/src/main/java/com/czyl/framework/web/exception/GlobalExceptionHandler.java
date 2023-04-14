package com.czyl.framework.web.exception;

import com.czyl.common.constant.HttpStatus;
import com.czyl.common.exception.BaseException;
import com.czyl.common.exception.CustomException;
import com.czyl.common.exception.DemoModeException;
import com.czyl.common.utils.StringUtils;
import com.czyl.common.web.domain.AjaxResult;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

/**
 * 全局异常处理器
 *
 * @author tanghx
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public AjaxResult baseException(BaseException e) {
        log.warn("baseException", e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult businessException(CustomException e) {
        log.warn("businessException", e);
        if (StringUtils.isNull(e.getCode())) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public AjaxResult handlerNoFoundException(NoHandlerFoundException e) {
        log.warn("handlerNoFoundException", e);
        return AjaxResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAuthorizationException(AccessDeniedException e) {
        log.warn("handleAuthorizationException", e);
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public AjaxResult handleAccountExpiredException(AccountExpiredException e) {
        log.warn("handleAccountExpiredException", e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public AjaxResult handleUserCodeNotFoundException(UsernameNotFoundException e) {
        log.warn("handleUserCodeNotFoundException", e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(MyBatisSystemException.class)
    public AjaxResult handleMyBatisSystemException(MyBatisSystemException e) {
        log.warn("handleMyBatisSystemException", e);
        Throwable error = e;
        while (error.getCause() != null) {
            error = error.getCause();
        }
        if (error instanceof CustomException) {
            log.warn("handleMyBatisSystemException", e);
        } else {
            log.error("handleMyBatisSystemException", e);
        }
        return AjaxResult.error(error.getMessage());
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public AjaxResult handleBadSqlGrammarException(BadSqlGrammarException e) {
        log.error("handleBadSqlGrammarException", e);
        return AjaxResult.error("SQL语句错误,详情见后台日志");
    }

    /**
     * @Validated 验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e) {
        log.warn("validatedBindException", e);
        StringBuffer sb = new StringBuffer();
        for (int i = e.getAllErrors().size() - 1; i >= 0; i--) {
            ObjectError error = e.getAllErrors().get(i);
            if (error.contains(CustomException.class)) {
                CustomException customException = error.unwrap(CustomException.class);
                sb.append(customException.getMessage()).append(",");
            } else {
                sb.append(error.getDefaultMessage()).append(",");
            }
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 1);
        }
        return AjaxResult.error(sb.toString());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e) {
        log.warn("validExceptionHandler", e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult demoModeException(DemoModeException e) {
        return AjaxResult.error("演示模式，不允许操作");
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public AjaxResult handleRedisConnectionFailureException(RedisConnectionFailureException e) {
        log.error("handleRedisConnectionFailureException", e);
        return AjaxResult.error("无法连接到Redis");
    }

    @ExceptionHandler(RedisConnectionException.class)
    public AjaxResult handleRedisConnectionException(RedisConnectionException e) {
        log.error("handleRedisConnectionException", e);
        return AjaxResult.error("无法连接到Redis");
    }

    @ExceptionHandler(PoolException.class)
    public AjaxResult handlePoolException(PoolException e) {
        log.error("handlePoolException", e);
        if (e.getCause() != null && e.getCause() instanceof RedisConnectionException) {
            return AjaxResult.error("无法连接到Redis");
        }
        return AjaxResult.error(e, "连接池错误");
    }

//	@ExceptionHandler(org.apache.catalina.connector.ClientAbortException.class)
//	public AjaxResult handleClientAbortException(org.apache.catalina.connector.ClientAbortException e) {
//		log.warn("handleClientAbortException", e);
//		return AjaxResult.error("浏览器已断开连接");
//	}

    @ExceptionHandler(TransactionSystemException.class)
    public AjaxResult handleTransactionSystemException(TransactionSystemException e) {
        log.warn("handleTransactionSystemException", e);
        return AjaxResult.error(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public AjaxResult handleIOException(IOException e) {
        if ("你的主机中的软件中止了一个已建立的连接。".equals(e.getMessage())) {
            log.warn("handleIOException", e);
            return AjaxResult.error("浏览器已断开连接");
        }
        log.error("handleIOException", e);
        return AjaxResult.error(e, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error("handleException", e);
        return AjaxResult.error(e, e.getMessage());
    }
}
