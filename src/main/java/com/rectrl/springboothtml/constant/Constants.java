package com.rectrl.springboothtml.constant;

/**
 * @author hongen.zhang
 * time: 2019/12/1 16:49
 * email: hongen.zhang@things-matrix.com
 */
public class Constants {

    // csv记录以","分隔
    public static final String DELIMITER = ",";

    // 字符串以"."分隔
    public static final String REGEX = "\\w+(\\.\\w+)|\\s*$";

    // 序列号: 包含数字字符的8-16位字符串
    public static final String SN_PATTERN = "^[a-zA-Z0-9]{8,16}|\\s*$";

    // iccid: 包含数字的20位字符串
    public static final String ICCID_PATTERN = "^\\d{19,20}|\\s*$";

    // autoUpdate: 包含数字的20位字符串
    public static final String OVERWRITE_PATTERN = "0|1|\\s*$";

    // description
    public static final String DESC_PATTERN = "[\\s\\S]{0,200}|\\s*$";

    // 包含数字字母_-.,@$以及空格
    public static final String TAG_PATTERN = "[\\w,-._@$\\s]{1,50}|\\s*$";

    // 包含数字字母_-.@$以及空格
    public static final String ATTR_PATTERN = "[\\w-._@$\\s]{1,50}|\\s*$";


    // 整数
    public static final String INT_PATTERN = "^[0-9]*$";
    // 包含数字字母_-.以及空格,汉字
    public static final String COMMON_CHINA_PATTERN = "[\\w\\W-._\\s]{1,100}|\\s*$";

    // 包含数字字母_-.以及空格
    public static final String COMMON_PATTERN = "[\\w-._\\s]{1,100}|\\s*$";

    //匹配日期 2019.10.10或者2019-10-10或者2019/10/10
    public static final String DATA_PATTERN = "^[1-9]\\d{3}[-./](0[1-9]|1[0-2])[-./](0[1-9]|[1-2][0-9]|3[0-1])$";

    //Authorization请求头
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //Bearer token
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public static final String USER_INFO_HEADER="user-info";

    public static final long BEARER_TOKEN_EXPIRATION = 86400L;

    // Bom包含数字字母_-.以及空格
    public static final String BOM_PATTERN = "[\\w\\W-._\\s]{1,50}|\\s*$";

    // 小数
    public static final String PRICING_PATTERN = "^[0-9]{0,10}(\\.[0-9]{1,2})?|0$";

}
