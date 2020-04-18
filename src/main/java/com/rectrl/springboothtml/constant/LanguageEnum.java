package com.rectrl.springboothtml.constant;


import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 系统支持的语言类型
 *
 * @author huyapeng
 * @date 2019/9/9
 * Email: yapeng.hu@things-matrix.com
 */
public enum LanguageEnum {
    /**
     * 美式英语
     */
    EN_US("en-US"),
    /**
     * 简体中文
     */
    ZH_CN("zh-CN"),
    ;

    private ResourceBundle resourceBundle;

    private String language;

    private final static Pattern pattern = Pattern.compile("\\$\\{\\w+\\}");


    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public String getLanguage() {
        return language;
    }

    LanguageEnum(String language) {
        this.language = language;
        resourceBundle = ResourceBundle.getBundle(this.language.toLowerCase());
    }

    public static LanguageEnum valueOfIgnoreCase(String name) {
        for (LanguageEnum value : values()) {
            if (value.language.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return EN_US;
    }


    public String i18n(String key, String defaultMessage) {
        return resourceBundle.containsKey(key) ? UTF8(resourceBundle.getString(key)) : defaultMessage;
    }

    public String translate(String desc) {
        Matcher m = pattern.matcher(desc);
        while (m.find()) {
            String group = m.group();
            String key = group.substring(2, group.length() - 1);
            desc = desc.replace(group, i18n(key, group));
        }
        return desc;
    }


    /**
     * ISO_8859_1->UTF-8 重新编码
     * java中properties配置文件默认的编码为：ISO-8859-1，是不支持中文的，所以会乱码
     *
     * @param message 信息
     * @return UTF-8编码的字符串
     */
    private String UTF8(String message) {
        return new String(message.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
