package com.rectrl.springboothtml.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author hongen.zhang
 * time: 2019/12/4 16:49
 * email: hongen.zhang@things-matrix.com
 */
@SuppressWarnings("DuplicatedCode")
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_POINT = "yyyy.MM.dd";
    public static final String DATE_PATTERN_SLASH = "yyyy/MM/dd";
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String ATT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss+00:00";

    /**
     * 解析日期
     */
    public static Date parseDate(String dateStr) {
        SimpleDateFormat format = null;
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        String _dateStr = dateStr.trim();
        try {
            if (_dateStr.matches("\\d{1,2}[A-Z]{3}")) {
                _dateStr = _dateStr
                        + (Calendar.getInstance().get(Calendar.YEAR) - 2000);
            }
            // 01OCT12
            if (_dateStr.matches("\\d{1,2}[A-Z]{3}\\d{2}")) {
                format = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);
            } else if (_dateStr.matches("\\d{1,2}[A-Z]{3}\\d{4}.*")) {// 01OCT2012
                // ,01OCT2012
                // 1224,01OCT2012
                // 12:24
                _dateStr = _dateStr.replaceAll("[^0-9A-Z]", "")
                        .concat("000000").substring(0, 15);
                format = new SimpleDateFormat("ddMMMyyyyHHmmss", Locale.ENGLISH);
            } else {
                try {
                    new SimpleDateFormat(TIMESTAMP_PATTERN).parse(_dateStr);
                    format = new SimpleDateFormat(TIMESTAMP_PATTERN);
                } catch (ParseException e) {
                    StringBuffer sb = new StringBuffer(_dateStr);
                    String[] tempArr = _dateStr.split("\\s+");
                    tempArr = tempArr[0].split("-|\\/");
                    if (tempArr.length == 3) {
                        if (tempArr[1].length() == 1) {
                            sb.insert(5, "0");
                        }
                        if (tempArr[2].length() == 1) {
                            sb.insert(8, "0");
                        }
                    }
                    _dateStr = sb.append("000000").toString().replaceAll("[^0-9]",
                            "").substring(0, 14);
                    if (_dateStr.matches("\\d{14}")) {
                        format = new SimpleDateFormat("yyyyMMddHHmmss");
                    }
                }
            }

            Date date = format.parse(_dateStr);
            return date;
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
        }
    }

    /**
     * 解析日期字符串转化成日期格式
     */
    public static Date parseDate(String dateStr, String... pattern) {
        try {
            SimpleDateFormat format = null;
            if (StringUtils.isBlank(dateStr)) {
                return null;
            }

            if (Objects.nonNull(pattern)) {
                for (String s : pattern) {
                    format = new SimpleDateFormat(s);
                    return format.parse(dateStr);
                }
            }
            return parseDate(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
        }
    }


    /**
     * 解析日期字符串转化成日期格式
     */
    public static Date parseDate(String dateStr, String pattern) {
        try {
            SimpleDateFormat format = null;
            if (StringUtils.isBlank(dateStr)) {
                return null;
            }
            if (StringUtils.isNotBlank(pattern)) {
                format = new SimpleDateFormat(pattern);
                return format.parse(dateStr);
            }
            return parseDate(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
        }
    }

    public static long parseDateString2Long(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
            return format.parse(dateStr).getTime();
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
        }
    }

    /**
     * 根据日期字符串获取时间戳--"yyyy-MM-dd"
     */
    public static long parseDateStringToLong(String dateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
            return format.parse(dateStr).getTime();
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateStr + "]");
        }
    }

    /**
     * 根据日期字符串获取时间戳--"yyyy-MM-dd HH:mm:ss"
     */
    public static long parseDateTimeStringToLong(String dateTimeStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(TIMESTAMP_PATTERN);
            return format.parse(dateTimeStr).getTime();
        } catch (Exception e) {
            throw new RuntimeException("无法解析日期字符[" + dateTimeStr + "]");
        }
    }


    public static String getDateTime(String pattern) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat(pattern);
        return simpledateformat.format(new Date());
    }

    /**
     * 获取一天开始时间
     */
    public static Date getDayBegin(Date date) {
        String format = DateFormatUtils.format(date, DATE_PATTERN);
        return parseDate(format.concat(" 00:00:00"));
    }

    /**
     * 获取一天结束时间
     */
    public static Date getDayEnd(Date date) {
        String format = DateFormatUtils.format(date, DATE_PATTERN);
        return parseDate(format.concat(" 23:59:59"));
    }

    /**
     * 获取一天开始时间 string
     */
    public static String getDayBeginForString(String date) {
        Date date1 = parseDate(date);
        String format = DateFormatUtils.format(date1, DATE_PATTERN);
        return format.concat(" 00:00:00");
    }

    /**
     * 获取一天结束时间 string
     */
    public static String getDayEndForString(String date) {
        Date date1 = parseDate(date);
        String format = DateFormatUtils.format(date1, DATE_PATTERN);
        return format.concat(" 23:59:59");
    }

    /**
     * 时间戳格式转换为日期（年月日）格式
     */
    public static Date timestamp2Date(Date date) {
        return formatDate(date, DATE_PATTERN);
    }

    /**
     * 格式化日期格式为：ddMMMyy
     */
    public static String format2ddMMMyy(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);
        return format.format(date).toUpperCase();
    }

    /**
     * 格式化日期格式为：ddMMMyy
     */
    public static String format2ddMMMyy(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);
        return format.format(DateUtils.parseDate(dateStr)).toUpperCase();
    }

    /**
     * 格式化日期字符串
     */
    public static String formatDateStr(String dateStr, String... patterns) {
        String pattern = TIMESTAMP_PATTERN;
        if (patterns != null && patterns.length > 0
                && StringUtils.isNotBlank(patterns[0])) {
            pattern = patterns[0];
        }
        return DateFormatUtils.format(parseDate(dateStr), pattern);
    }

    /**
     * 格式化日期为日期字符串
     */
    public static String formatYYYYMMDD(Date date) {

        return format(date, DateUtils.DATE_PATTERN);
    }


    /**
     * 格式化日期为日期字符串
     */
    public static String format(Date date, String... patterns) {
        if (date == null) {
            return "";
        }
        String pattern = TIMESTAMP_PATTERN;
        if (patterns != null && patterns.length > 0
                && StringUtils.isNotBlank(patterns[0])) {
            pattern = patterns[0];
        }
        return DateFormatUtils.format(date, pattern);
    }

    public static String getCurrentDateTime() {
        return format(new Date(), TIMESTAMP_PATTERN);
    }

    public static String format2DateStr(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 格式化日期为指定格式
     */
    public static Date formatDate(Date orig, String... patterns) {
        String pattern = TIMESTAMP_PATTERN;
        if (patterns != null && patterns.length > 0
                && StringUtils.isNotBlank(patterns[0])) {
            pattern = patterns[0];
        }
        return parseDate(DateFormatUtils.format(orig, pattern));
    }

    /**
     * 比较两个时间相差多少秒
     */
    public static long getDiffSeconds(Date d1, Date d2) {
        return Math.abs((d2.getTime() - d1.getTime()) / 1000);
    }

    /**
     * 比较两个时间相差多少分钟
     */
    public static long getDiffMinutes(Date d1, Date d2) {
        long diffSeconds = getDiffSeconds(d1, d2);
        return diffSeconds / 60;
    }

    /**
     * 比较两个时间相差多少天
     */
    public static long getDiffDay(Date d1, Date d2) {
        long between = Math.abs((d2.getTime() - d1.getTime()) / 1000);
        long day = between / 60 / 60 / 24;
        return (long) Math.floor(day);
    }

    /**
     * 返回传入时间月份的最后一天
     */
    public static Date lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }

    /**
     * 返回传入时间月份的第一天
     */
    public static Date firstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }

    /**
     * 获取两个时间相差月份
     */
    public static int getDiffMonth(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        return (endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR)) * 12
                + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    /**
     * 计算并格式化消耗时间
     */
    public static String formatTimeConsumingInfo(long startPoint) {
        StringBuffer buff = new StringBuffer();
        long totalMilTimes = System.currentTimeMillis() - startPoint;
        int hour = (int) Math.floor(totalMilTimes / (60 * 60 * 1000));
        int mi = (int) Math.floor(totalMilTimes / (60 * 1000));
        int se = (int) Math.floor((totalMilTimes - 60000 * mi) / 1000);
        if (hour > 0) {
            buff.append(hour).append("小时");
        }
        if (mi > 0) {
            buff.append(mi).append("分");
        }
        if (hour == 0) {
            buff.append(se).append("秒");
        }
        return buff.toString();
    }

    /**
     * 判断是否为闰年
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 判断一个时间是否在某段时间区间内--HH:mm-HH:mm
     */
    public static Boolean isBelong(Date date, String beginTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat(TIME_PATTERN);//设置日期格式
        Date now = null;
        Date startDate = null;
        Date endDate = null;
        try {
            now = df.parse(df.format(new Date()));
            startDate = df.parse(beginTime);
            endDate = df.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar nowDate = Calendar.getInstance();
        nowDate.setTime(now);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        if (nowDate.after(begin) && nowDate.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前时间的前一天或者前几天时间 -1为前一天，1为后一天
     */
    public static Date getDate(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        date = calendar.getTime();
        return formatDate(date, DATE_PATTERN);
    }

    /**
     * 获取某个时间段内所有日期
     *
     * @param begin
     * @param end
     * @return
     */
    public static List<String> getDayBetweenDates(String begin, String end) {
        Date dBegin = DateUtils.parseDate(begin, DATE_PATTERN);
        Date dEnd = DateUtils.parseDate(end, DATE_PATTERN);
        List<String> lDate = new ArrayList<String>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        lDate.add(sd.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(sd.format(calBegin.getTime()));
        }
        return lDate;
    }

    /**
     * 获取某个时间段内所有月份
     *
     * @param minDate
     * @param maxDate
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthBetweenDates(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(DateUtils.parseDate(minDate, "yyyy-MM"));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
        max.setTime(DateUtils.parseDate(maxDate, "yyyy-MM"));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(format(curr.getTime(), "yyyy-MM"));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

}
