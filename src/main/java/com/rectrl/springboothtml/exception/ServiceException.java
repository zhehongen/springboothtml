package com.rectrl.springboothtml.exception;


import com.rectrl.springboothtml.result.ResultCode;

public class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;

    /**
     * 根异常，保持异常链
     */
    private Throwable caused;


    public ServiceException(ResultCode resultEnum, Throwable caused) {
        super(caused);
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }


    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServiceException(ResultCode enumType) {
        super(enumType.getMessage());
        this.code = enumType.getCode();
        this.message = enumType.getMessage();
    }

    public ServiceException(ResultCode enumType, Object... args) {
        super(String.format(enumType.getMessage(), args));
        this.code = enumType.getCode();
        this.message = String.format(enumType.getMessage(), args);
    }

    public ServiceException(Integer code, Throwable caused) {
        super(caused);
        this.code = code;
        this.caused = caused;
    }

    public ServiceException(Integer code, String message, Throwable caused) {
        super(message, caused);
        this.code = code;
        this.message = message;
        this.caused = caused;
    }


    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Throwable getCaused() {
        return caused;
    }
}
