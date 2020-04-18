package com.rectrl.springboothtml.constant;

import java.util.Objects;

/**
 * @author huyapeng
 * @date 2019/11/7
 * Email: yapeng.hu@things-matrix.com
 */
public interface CodeEnum {
    Integer code();

    /**
     * @param enumClass 枚举类型
     * @param code      枚举类型编码
     * @param <E>       限定枚举
     * @return 对应的枚举类型
     */
    static <E extends Enum<?>> E codeOf(Class<E> enumClass, Object code) {
        E[] enumConstants = enumClass.getEnumConstants();
        if (Objects.nonNull(enumConstants))
            for (E e : enumConstants)
                if (e instanceof CodeEnum)
                    if (((CodeEnum) e).code().equals(code))
                        return e;
        return null;
    }
}
