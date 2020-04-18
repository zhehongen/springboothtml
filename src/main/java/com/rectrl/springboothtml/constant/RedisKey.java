package com.rectrl.springboothtml.constant;

/**
 * @author huyapeng
 * @date 2019/9/6
 * Email: yapeng.hu@things-matrix.com
 */
public class RedisKey {

    public static final String AUTH_TOKEN = "auth:token:";

    public static final String AUTH_LOGIN = "auth:login:";

    public static final String RESET_CODE = "email:code:reset:";

    public static final String REGISTER_CODE = "email:code:register:";

    public static final String EMAIL_LIMIT = "email:limit";

    public static final String SEQUENCE_CODE = "sequence:code:";

    public static final String SNAPSHOT = "snapshot:";


    public static String getAuthTokenKey(String token) {
        return AUTH_TOKEN + token;
    }

    public static String getAuthLogin(String userId) {
        return AUTH_LOGIN + userId;
    }

    public static String getResetCode(String email) {
        return RESET_CODE + email;
    }

    public static String getEmailLimit(String email) {
        return EMAIL_LIMIT + email;
    }

    public static String getRegisterCode(String email) {
        return REGISTER_CODE + email;
    }

    public static String getSequenceCode(String code) {
        return SEQUENCE_CODE + code;
    }

    public static String getSNAPSHOT(Object name, Object id) {
        return SNAPSHOT + name + ":" + id;
    }
}
