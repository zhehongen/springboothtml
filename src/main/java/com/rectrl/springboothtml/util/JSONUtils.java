package com.rectrl.springboothtml.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by www on 2017/7/15.
 */
public class JSONUtils {
    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        //  默认非空不输出，时间格式
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将 Java 对象转为 JSON 字符串
     */
    public static <T> String toJSON(T obj) {
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Java 转 JSON 出错！", e);
        }
        return jsonStr;
    }

    /**
     * 将 JSON 字符串转为 Java 对象
     */
    public static <T> T toBean(String json, Class<T> typeOfClass) {
        T obj = null;
        if (json != null)
            try {
                obj = objectMapper.readValue(json, typeOfClass);
            } catch (Exception e) {
                logger.error("JSON 转 Java 出错！", e);
            }
        return obj;
    }

    /**
     * 将 JSON 字符串转为 Java 对象
     */
    public static Map<String, Object> toMap(String json) {
        Map<String, Object> obj = null;
        if (StringUtils.isNotBlank(json))
            try {
                obj = objectMapper.readValue(json, new TypeReference<LinkedHashMap<String, Object>>() {
                });
            } catch (Exception e) {
                logger.error("JSON 转 Java 出错！", e);
            }
        return obj;
    }


    /**
     * 将 JSONArray 字符串转为 Java 对象
     */
    public static <T> List<T> toList(String json, Class<T> typeOfClass) {
        List<T> objs = null;
        try {
            objs = objectMapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            logger.error("JSON 转 Java 出错！", e);
        }
        return Objects.isNull(objs) ? Collections.emptyList() : objs;
    }


}
