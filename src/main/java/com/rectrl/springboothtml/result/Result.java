package com.rectrl.springboothtml.result;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rectrl.springboothtml.exception.ServiceException;
import com.rectrl.springboothtml.util.JSONUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


/**
 * @author huyapeng
 * @date 2019/9/5
 * Email: yapeng.hu@things-matrix.com
 */
@Data
@NoArgsConstructor
@Slf4j
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态信息
     */
    @JsonAlias(value = {"message", "msg"})
    private String message;

    /**
     * 返回body
     */
    private T data;


    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /*静态方法,用于快速构建结果*/

    public static <T> Result<T> ok() {
        return new Result<>(ResultCode.OK.getCode(), ResultCode.OK.getMessage(), null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCode.OK.getCode(), ResultCode.OK.getMessage(), data);
    }

    public static <T> Result<T> failure(ResultCode resultEnum) {
        return new Result<>(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    public static <T> Result<T> failure(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> failure(Integer code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> hystrix(String serverName) {
        Integer code = ResultCode.SERVICE_UNAVAILABLE.getCode();
        String message = ResultCode.SERVICE_UNAVAILABLE.getMessage();
        return new Result<>(code, String.format(message, serverName), null);
    }

    /*扩展方法，参考Optional类，用于结果的判断、转换、异常处理等,PS: ifOk和ifPresent 有区别一个表示http成功，一个表示有返回值*/

    /**
     * 判断是否有返回数据
     *
     * @return data
     */
    @JsonIgnore
    public boolean isPresent() {
        return data != null;
    }

    /**
     * 如果有返回值进行消费
     *
     * @return data
     */
    @JsonIgnore
    public void ifPresent(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        if (isPresent())
            consumer.accept(data);
    }

    /**
     * 判断http调用是否成功
     *
     * @return ok
     */
    @JsonIgnore
    public boolean isOk() {
        return ResultCode.OK.getCode().equals(this.code);
    }

    /**
     * 如果Http调用成功调用该方法
     *
     * @param consumer 消费函数
     */
    @JsonIgnore
    public void ifOk(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        if (isOk())
            consumer.accept(data);
    }

    /**
     * http调用成功，或者抛出异常，异常为返回信息
     *
     * @return exception
     */
    @JsonIgnore
    public T okOrThrow() {
        return okOrThrow(() -> new ServiceException(this.code, this.message));
    }

    /**
     * http调用成功，或者抛出异常，覆盖默认异常
     *
     * @return exception
     */
    @JsonIgnore
    public T okOrThrow(Supplier<ServiceException> supplier) {
        if (!isOk())
            throw supplier.get();
        return data;
    }

    /**
     * 没有返回值抛出异常，异常为http调用错误信息
     *
     * @return exception
     */
    @JsonIgnore
    public T orElseThrow() {
        return okOrThrow(() -> new ServiceException(this.code, this.message));
    }


    /**
     * 没有返回值抛出异常，覆盖默认异常信息
     *
     * @return exception
     */
    @JsonIgnore
    public T orElseThrow(Supplier<ServiceException> supplier) {
        if (!isPresent())
            throw supplier.get();
        return data;
    }


    @JsonIgnore
    public T okOrThrowForCmp() {
        return okOrThrowForCmp(() -> new ServiceException(this.code, this.message));
    }

    @JsonIgnore
    public T okOrThrowForCmp(Supplier<ServiceException> supplier) {
        if (!isOk()) {
            log.error("CMP平台通信错误:{}", JSONUtils.toJSON(this));
            throw supplier.get();
        }
        return data;
    }


    @JsonIgnore
    public Result<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (Objects.isNull(data))
            return this;
        else
            return predicate.test(data) ? this : new Result<>(code, message, null);
    }

    @JsonIgnore
    public T orElse(T other) {
        return data != null ? data : other;
    }

    @JsonIgnore
    public <U> Result<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (Objects.isNull(data))
            return new Result<>(code, message, null);
        else
            return new Result<>(code, message, mapper.apply(data));
    }


}
