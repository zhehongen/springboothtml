package com.rectrl.springboothtml.exception;


import com.rectrl.springboothtml.result.Result;
import com.rectrl.springboothtml.result.ResultCode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;


/**
 * Created by www on 2017/7/25.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);


    /**
     * 其他异常处理
     *
     * @return
     */
    @ExceptionHandler({Exception.class})
    public Result handleOthersException(Exception e) {
        //  打印异常日志
        logger.error("==GlobalExceptionHandler-->exception:" + e);
        e.printStackTrace();
        if (e instanceof ServiceException) {
            //  自定义异常
            ServiceException ex = (ServiceException) e;
            return Result.failure(ex.getCode(), ex.getMessage());
        } else if (e instanceof AccessDeniedException) {
            //  用户权限不足
            return Result.failure(ResultCode.FORBIDDEN);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            return Result.failure(ResultCode.BAD_REQUEST);
        } else if (e instanceof MethodArgumentNotValidException) {
            return Result.failure(ResultCode.BAD_REQUEST);
        } else if (e instanceof BindException) {
            return Result.failure(ResultCode.BAD_REQUEST);
        } else if (e instanceof IllegalArgumentException) {
            return Result.failure(ResultCode.BAD_REQUEST.getCode(), "参数错误：" + e.getMessage());
        } else if (e instanceof DeadlockLoserDataAccessException) {
            return Result.failure(ResultCode.BAD_REQUEST.getCode(), "您操作太频繁了，请稍后操作");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return Result.failure(ResultCode.BAD_REQUEST.getCode(), "请求方式错误");
        } else if (e instanceof DuplicateKeyException) {
            return Result.failure(ResultCode.DUPLICATE_ERROR);
        } else if (e instanceof DataIntegrityViolationException) {
            return Result.failure(ResultCode.BAD_REQUEST);
        }
        //  其他未知异常都认为是服务器错误
        return Result.failure(ResultCode.INTERNAL_SERVER_ERROR);
    }


}
