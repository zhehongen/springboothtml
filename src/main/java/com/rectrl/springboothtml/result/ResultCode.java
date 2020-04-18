package com.rectrl.springboothtml.result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huyapeng
 * @date 2019/9/5
 * Email: yapeng.hu@things-matrix.com
 */
public enum ResultCode {
    OK(0, "response.0", "ok"),

    // file
    FILE_FORMAT(14301, "response.14301", "File format valid fail"), // 文件格式校验错误码
    FILE_DATA(14302, "response.14302", "File data valid fail"), // 文件数据校验错误码
    FILE_EMPTY(143012,"response.143012","File is empty"), // 文件为空
    FILE_READ_ERR(143013,"response.143013","File read error"), // 文件读取错误
    FILE_IMPORT_LIMIT(143014,"response.143014","Import exceeded maximum limit"), // 导入支持5000行数据
    FILE_DATA_EMPTY(143015,"response.143015","File data is empty"), // 文件读取数据为空
    FILE_TABLE_HEADER_MISS(143016,"response.143016","Table header field miss: {%s}"), // table header字段缺失
    FILE_TABLE_HEADER_ERR(143017,"response.143017","Table header field error: {%s}"), // table header字段有误
    FILE_GROUP_DATABASE_EMPTY(143018,"response.143018","Please create group on the platform"), // 数据表group记录为空
    FILE_EXTENSION(143019,"response.143019","File format is not supported"), // 文件格式不支持

    FILE_DATA_ROW_ERR(143020,"response.143020","Data row format error"), // 数据行为空或者数据列数缺失
    FILE_SN_EMPTY(143021,"response.143021","The serialNumber is empty"), // 序列号为空
    FILE_SN_REPEAT(143022,"response.143022","Repeated serial number({%s})"), // 序列号重复
    FILE_SN_EXIST(143023,"response.143023","SerialNumber already exists"), // 序列号已存在
    FILE_SN_FORMAT_ERR(143024,"response.143024","SerialNumber format error"), // 序列号格式错误(校验序列号正则：Must be 8-16 bit characters，including digits 0-9 and leters a-z。包括设备序列号和传感器序列号)
    FILE_SENSOR_SN_FORMAT_ERR(143025,"response.143025","Sensor serialNumber format error"), // 序列号格式错误(校验序列号正则：Must be 8-16 bit characters，including digits 0-9 and leters a-z。包括设备序列号和传感器序列号)
    FILE_OVERWRITE_FORMAT_ERR(143026,"response.143026","SimAutoUpdate/SensorAutoUpdate value is 0 or 1"), // overwrite的值只能是0或者1
    FILE_MODEL_EMPTY(143027,"response.143027","Model is empty"), // model为空
    FILE_MODEL_READONLY(143028,"response.143028","Model read only"), // model只读
    FILE_QUANTITY_INCORRECT(143029,"response.143029","Imported quantity is {%s}, does not match the purchased quantity"), // 订单分配数量不正确

    FILE_GROUP_EMPTY(1430210,"response.1430210","Group is empty"), // group为空
    FILE_GROUP_NON_EXIST(1430211,"response.1430211","Group does not exist or is invalid"), // Group不存在
    FILE_MODEL_GROUP_MISMATCH(1430212,"response.1430212","Model and Group do not match"), // model与group的model不匹配
    FILE_ICCID_FORMAT_ERR(1430213,"response.1430213","Sim ICCID format error"), // iccid格式错误(必须为20位数字)
    FILE_DESC_FORMAT_ERR(1430214,"response.1430214","Description format error"), // 描述格式错误（字数必须是0-200）
    FILE_TAG_FORMAT_ERR(1430215,"response.1430215","Tags format error"), // 标签格式错误(Must be 1-50 characters, including digits 0-9, letters a-z and A-Z, characters“-”， “.",  "_","@"， "$" and blank space；一个设备不超过20个tag)
    FILE_ATTR_FORMAT_ERR(1430216,"response.1430216","Attribute format error"), // 属性格式错误(Must be 1-50 characters, including digits 0-9, letters a-z and A-Z, characters“-”， “.",  "_", "@"，"$" and blank space)
    FILE_DEVICE_STATUS_FORMAT_ERR(1430217,"response.1430217","deviceStatus format error"), // 设备状态应为数字
    FILE_COMMON_FORMAT_ERR(1430218,"response.1430218","{%s} format error"), // 设备状态应为数字
    FILE_PRODUCT_NAME_NON_EXIST(1430219,"response.1430219","productName does not exist"), // productName不存在

    FILE_COMPANY_NAME_NON_EXIST(1430220,"response.1430220","companyName does not exist"), // companyName不存在
    FILE_REASSIGN_COMPANIES_FOR_NON_IDLE_DEVICE(1430221,"response.1430221","company reassignment is not allowed for non-idle device"), // companyName不存在
    FILE_PRODUCT_NAME_EMPTY(1430222,"response.1430222","The productName is empty"), // 产品名称为空
    FILE_SN_NOT_EXIST(1430223,"response.1430223","SerialNumber not exists"), // 序列号不存在
    FILE_ICCID_EMPTY(1430224,"response.1430224","The iccid is empty"), // 序列号为空
    FILE_ICCID_REPEAT(1430225,"response.1430225","Repeated iccid ({%s})"), // 序列号重复
    FILE_ICCID_NOT_EXIST(1430226,"response.1430226","iccid not exists"), // 序列号不存在
    FILE_LABEL_HAS_BEEN_USED_BY_OTHER_COMPANY(1430227,"response.1430227","label has been used by other company"), // label被别的公司使用
    FILE_DEVICE_MODEL_NOT_MATCH(1430228,"response.1430228","device_model_not_match"), // 设备型号与订单要求不一致

    PARTIAL_CONTENT(206, "response.206", "The server successfully handled some GET requests"),
    BAD_REQUEST(400, "response.400", "bad request body"),
    UNAUTHORIZED(401, "response.401", "unauthorized, please try login"),
    PAYMENT_REQUIRED(402, "response.402", "payment required"),
    FORBIDDEN(403, "response.403", "permission denied, please try again"),
    NOT_FOUND(404, "response.404", "record not found"),
    USER_NOT_FUND(4041, "response.4041", "user not found"),
    ACCOUNT_NOT_FOUND(4042, "response.4042", "account not found"),
    COMPANY_NOT_FOUND(4043, "response.4043", "company not found"),
    REGISTER_INFO_NOT_FOUND(4044, "response.4044", "user's register information not found,please try register again"),
    CLUSTER_NOT_FOUND(4045, "response.4045", "cluster not found !!!"),
    ORDER_NOT_FOUND(4046, "response.4046", "order not found"),
    SERVICE_ORDER_DETAILS_NOT_FOUND(4047, "response.4047", "service order details not found"),
    BILLING_ADDRESS_NOT_FOUND(4048, "response.4048", "billing address not found"),
    SERVER_NOT_FOUND(4049, "response.4049", "server not found"),

    DEVICE_MODEL_NOT_FOUND(40412, "response.40412", "device_model_not_found"),
    DEVICE_PRODUCT_ID_NOT_FOUND(40413, "response.40413", "device_product_id_not_found"),
    DEVICE_STATUS_NOT_FOUND(40414, "response.40414", "device_status_not_found"),
    DEVICE_NOT_FOUND(40415, "response.40415", "device_not_found"),
    RMA_TYPE_NOT_FOUND(40416, "response.40416", "rma_type_not_found"),
    RMA_RECORD_NOT_FOUND(40417, "response.40417", "rma_record_not_found"),
    DELIVERY_TYPE_NOT_FOUND(40418, "response.40418", "delivery_type_not_found"),
    DEVICE_ORDER_DETAIL_NOT_FOUND(40419, "response.40419", "device_order_detail_not_found"),
    ORDER_LOGISTICS_INFO_NOT_FOUND(40420, "response.40420", "order_logistics_info_not_found"),
    SIM_CARD_SIZE_NOT_FOUND(40421, "response.40421", "simCard_size_not_found"),
    SIM_CARD_ORDER_DETAIL_NOT_FOUND(40422, "response.40422", "simCard_order_detail_not_found"),

    RMA_WORK_ORDER_DETAIL_NOT_FOUND(40423, "response.40423", "rma_work_order_detail_not_found"),
    RMA_RETURN_LIST_EMPTY(40424, "response.40424", "rma_return_list_empty"),
    DEVICE_NOT_FOUND_BY_SN(40425, "response.40425", " SerialNumber: {%s} not exists"),
    WORK_ORDER_NOT_FOUND(40426, "response.40426", "work_order_not_found"),
    WORK_ORDER_LOGISTICS_INFO_NOT_FOUND(40427, "response.40427", "work_order_logistics_info_not_found"),
    NEW_SN_EQUAL_OLD_SN(40428, "response.40428", " SerialNumber: {%s} old is equal to new"),
    NOTICE_NOT_FOUND(40429, "response.40429", " notice_not_found"),
    PRICING_STRATEGY_NOT_FOUND(40430, "response.40430", " pricing_strategy_not_found"),
    BILLING_NOT_FOUND(40431, "response.40431", " billing_not_found"),
    DEFAULT_BILLING_DISCOUNT_NOT_FOUND(40432, "response.40432", " default_billing_discount_not_found"),
    COMPANY_ORDER_DETAILS_NOT_FOUND(40433, "response.40433", " company_order_details_not_found"),
    BILLING_DISCOUNT_NOT_FOUND(40434, "response.40434", " Billing_discount_not_found"),

    BOM_NOT_FOUND(40435, "response.40435", "bom not found"),
    PARENT_BOM(40436, "response.40436", "parentBom can not be deleted"),

    PASSWORD_ERROR(405, "response.405", "password error"),
    CURRENT_PASSWORD_ERROR(4051, "response.4051", "current password error"),
    NEW_PASSWORD_SAME(4052, "response.4052", "new password is the same as old one"),
    ACCOUNT_INACTIVATED(406, "response.406", "account inactivated,please activate your account before login!"),
    DUPLICATE_ERROR(407, "response.407", "record duplicate error"),
    DUPLICATE_WEB_SITE(4071, "response.4071", "web site already exists,please change anther one !"),
    DUPLICATE_SERVER_NAME(4072, "response.4072", "server name already exists,please change anther one !"),
    DUPLICATE_UNFINISHED_ORDER(4073, "response.4073", "already exists a unfinished order"),
    DUPLICATE_OPERATION(4074, "response.4074", "duplicate operation"),
    DUPLICATE_COMPANY_NAME(4075, "response.4075", "company name already exists in this cluster"),
    DUPLICATE_COMPANY_SITE_PREFIX(4076, "response.4076", "company site prefix already exists in this cluster"),
    DUPLICATE_COMPANY_ADMIN_EMAIL(4077, "response.4077", "admin email already exists in this cluster"),

    DUPLICATE_SN(4078, "response.4078", "duplicate sn"),
    DUPLICATE_BATCH(4079, "response.4079", "duplicate batch"),
    DUPLICATE_BOM_NAME(40791, "response.40791", "response.40791"),
    DUPLICATE_BOM_ID(40792, "response.40792", "response.40792"),


    INVALID_DATA(408, "response.408", "invalid data"),
    INVALID_WEB_SITE(4081, "response.4081", "invalid web site"),
    INVALID_ACTIVATION_CODE(4082, "response.4082", "activation code invalid"),
    INVALID_TOKEN(4083, "response.4083", "invalid token"),
    INVALID_VERIFICATION_CODE(4084, "response.4084", "invalid verification code"),
    SITE_PREFIX_SHOULD_NOT_BE_NOT_EMPTY(4085, "response.4085", "site prefix must be not empty"),
    SITE_PREFIX_IS_FORBIDDEN(4086, "response.4086", "site prefix is forbidden !"),
    COMPANY_CODE_SHOULD_NOT_BE_NOT_EMPTY(4087, "response.4087", "company code must be not empty!"),
    BOM_NAME_SHOULD_NOT_BE_EMPTY(4088, "response.4088", "product name must be not empty!"),
    BOM_PRODUCTID_SHOULD_NOT_BE_EMPTY(4089, "response.4089", "product ID must be not empty!"),
    BOM_PRODUCTID_OR_NAME_TOO_LONG(40891, "response.40891", "bom productID or name too long"),
    RELATION_BOM_CAN_NOT_BE_DELETE(40892, "response.40892", "RELATION_BOM_CAN_NOT_BE_DELETE"),
    BOM_DESCRIPTION_TOO_LONG(40893, "response.40893", "bom description too long"),


    ORDER_OPT_ERROR(409, "response.409", "order operation error"),
    COMPANY_BIND_ERROR(410, "response.410", "company binding customer failure"),
    ACCOUNT_ALREADY_ACTIVATED(411, "response.411", "account already activated !!!"),
    USER_ALREADY_CREATE_COMPANY(412, "response.412", "user already created a company !!!"),
    COMPANY_INACTIVE(413, "response.413", "company not activated !!!"),
    ADMIN_EMAIL_NOT_MATCH_COMPANY(414, "response.414", "admin email not match company email domain !!!"),
    MAIL_IS_SENT_TOO_FREQUENTLY(415, "response.415", "Mail is sent too frequently, please try late after three minutes!!!"),
    MODIFY_DOMAIN_FAILED(416, "response.416", "modify domain failed !"),
    GET_DOMAINS_FAILED(4161, "response.4161", "get domains failed!"),
    USER_ALREADY_REGISTERED(417, "response.417", "user already registered"),
    COMPANY_IS_NOT_DISTRIBUTOR(418, "response.418", "company is not a distributor"),
    COMPANY_IS_NOT_ORDINARY(419, "response.419", "company is not a ordinary company"),
    UNSUPPORTED_OPERATION(420, "response.420", "unsupported operation !"),
    NO_CLUSTER_SELECTED(421, "response.421", "no cluster selected !"),
    INCORRECT_USERNAME_OR_PASSWORD(422, "response.422", "Incorrect username or password"),
    CAN_NOT_MODIFY_OR_DELETE_A_SENT_NOTICE(423, "response.423", "can not modify or delete a sent notice"),
    NO_COMPANY_SELECTED(424, "response.424", "Incorrect username or password"),
    SEND_TIME_CAN_NOT_EMPTY(425, "response.425", "send time can not empty"),
    CAN_NOT_MODIFY_DISCOUNTS_FOR_SUBSIDIARIES(426, "response.426", "can not modify subsidiary's discount"),
    INTERNAL_SERVER_ERROR(500, "response.500", "internal server error"),
    NOT_IMPLEMENTED(501, "response.501", "not implement"),
    BAD_GATEWAY(502, "response.502", "bad gateway"),
    SERVICE_UNAVAILABLE(503, "response.503", " {%s} service is not available"),
    GATEWAY_TIMEOUT(504, "response.504", "request time out"),
    HTTP_VERSION_NOT_SUPPORT(505, "response.505", "unsupported http request"),
    TMX_REMOTE_CALL_FAILED(506, "response.506", "TMX remote call failed !"),
    SERVER_IS_BUSY(507, "response.507", "The server is busy. Please try later"),
    CMP_REMOTE_CALL_FAILED(508, "response.508", "cmp remote call failed"),

    VALID_FAIL(601, "response.601", "request parameter valid error {%s}"), // 请求参数校验错误

    INVALID_START_END_DATA(701, "response.701", "{%s} should great than {%s}"),
    FAIL(9999, "response.9999", "fail"),
    ;

    ResultCode(Integer code, String msgId, String defaultMsg) {
        this.code = code;
        this.messageId = msgId;
        this.message = defaultMsg;
    }


    private final static Map<Integer, ResultCode> map = new HashMap<>();

    static {
        ResultCode[] values = ResultCode.values();
        for (ResultCode value : values) {
            map.put(value.getCode(), value);
        }
    }


    public static ResultCode valueOf(Integer code) {
        return map.get(code);
    }

    public boolean containsCode(Integer code) {
        return map.containsKey(code);
    }

    /**
     * 状态编码
     */
    private Integer code;
    /**
     * 状态默认信息
     */
    private String message;
    /**
     * 消息模板Id，用于国际化的时候将默认信息通过模板信息转换
     */
    private String messageId;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageId() {
        return messageId;
    }
}
