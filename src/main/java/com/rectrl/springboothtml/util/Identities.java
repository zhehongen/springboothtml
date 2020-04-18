package com.rectrl.springboothtml.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author huyapeng
 * @date 2019/9/5
 * Email: yapeng.hu@things-matrix.com
 */
public class Identities {

    private static final IdGenerator generator = new SnowFlake();


    private Identities() {
    }


    /**
     * 采用雪花算法生成唯一ID
     *
     * @return 数据库唯一ID
     */
    public static String nextId() {
        return String.valueOf(generator.nextId());
    }

    /**
     * 加上前缀当做序列号
     */
    public static String nextSN(@NotNull String prefix) {
        return prefix + generator.nextId();
    }

    /**
     * 生成随机6位数验证码
     */
    public static String random6Num() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }

    /**
     * 生成随机验证码
     */
    public static String randomCode() {
        return Long.toString(generator.nextId(), 32);
    }


    public interface IdGenerator {
        long nextId();
    }


    public static class SnowFlake implements IdGenerator {


        /**
         * 起始的时间戳
         */
        private final static long START_TIMESTAMP = 1480166465631L;

        /**
         * 每一部分占用的位数
         */
        private final static long SEQUENCE_BIT = 12L; //序列号占用的位数
        private final static long MACHINE_BIT = 8L;   //机器标识占用的位数
        private final static long DATA_CENTER_BIT = 2L;//数据中心占用的位数

        /**
         * 每一部分的最大值
         */
        private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
        private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
        private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

        /**
         * 每一部分向左的位移
         */
        private final static long MACHINE_LEFT = SEQUENCE_BIT;
        private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
        private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

        private long dataCenterId;  //数据中心
        private long machineId;     //机器标识
        private long sequence = 0L; //序列号
        private long lastTimestamp = -1L;//上一次时间戳

        public SnowFlake() {
            this(getDataCenterId(), getWorkerId());
        }

        private static Long getWorkerId() {
            String hostAddress = null;
            try {
                InetAddress addr = InetAddress.getLocalHost();
                hostAddress = addr.getHostAddress();
            } catch (UnknownHostException ignored) {
            }

            return Long.parseLong(StringUtils.substringAfterLast(hostAddress, "."));
        }

        private static Long getDataCenterId() {
            return (long) (Math.random() * 3 + 1);
        }

        public SnowFlake(long dataCenterId, long machineId) {
            if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
                throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
            }
            if (machineId > MAX_MACHINE_NUM || machineId < 0) {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
            this.dataCenterId = dataCenterId;
            this.machineId = machineId;
        }

        /**
         * 产生下一个ID
         *
         * @return
         */
        public synchronized long nextId() {
            long timestamp = System.currentTimeMillis();
            if (timestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            }

            if (timestamp == lastTimestamp) {
                //相同毫秒内，序列号自增
                sequence = (sequence + 1) & MAX_SEQUENCE;
                //同一毫秒的序列数已经达到最大
                if (sequence == 0L) {
                    timestamp = nextTimestamp();
                }
            } else {
                //不同毫秒内，序列号置为0
                sequence = 0L;
            }

            lastTimestamp = timestamp;

            return (timestamp - START_TIMESTAMP) << TIMESTAMP_LEFT //时间戳部分
                    | dataCenterId << DATA_CENTER_LEFT       //数据中心部分
                    | machineId << MACHINE_LEFT             //机器标识部分
                    | sequence;                             //序列号部分
        }

        private long nextTimestamp() {
            long result = System.currentTimeMillis();
            while (result <= lastTimestamp) {
                result = System.currentTimeMillis();
            }
            return result;
        }


    }
}
