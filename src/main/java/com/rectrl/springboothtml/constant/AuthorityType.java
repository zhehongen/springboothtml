package com.rectrl.springboothtml.constant;

/**
 * @author huyapeng
 * @date 2019/11/14
 * Email: yapeng.hu@things-matrix.com
 */
public enum AuthorityType implements CodeEnum {

    /**
     * api访问类型权限
     */
    api(0),
    /**
     * 系统登录类型权限
     */
    system(1),

    /**
     * 操作权限
     */
    action(2),
    /**
     * 数据权限
     */
    data(3),
    ;

    private final Integer code;

    AuthorityType(Integer code) {
        this.code = code;
    }

    public Integer code() {
        return this.code;
    }
}
