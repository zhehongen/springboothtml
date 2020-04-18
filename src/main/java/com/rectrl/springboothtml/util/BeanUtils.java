package com.rectrl.springboothtml.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BeanUtils {

    private final static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {
    }

    //使用缓存提高效率
    private static final Map<String, BeanCopier> copierCaches = new ConcurrentHashMap<>();


    public static <S, T> List<T> copy(List<S> sources, Supplier<T> supplier) {
        return sources.stream().map(s -> copy(s, supplier.get(), false)).collect(Collectors.toList());
    }

    public static <S, T> T copy(S source, Supplier<T> supplier) {
        return copyNotNull(source, supplier.get());
    }

    public static <S, T> T copy(S source, T target) {
        return copy(source, target, false);
    }


    public static <S, T> T copyNotNull(S source, T target) {
        return copy(source, target, true);
    }

    public static <S, T> T copy(S source, T target, boolean notNull) {
        if (source == null) return target;
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        if (notNull) {
            BeanMap targetMap = BeanMap.create(target);
            copier.copy(source, target, (value, typeOfClass, methodName) -> Objects.nonNull(value) ? value : targetMap.get(getPropertyName(String.valueOf(methodName))));
        } else {
            copier.copy(source, target, (value, typeOfClass, methodName) -> value);
        }

        return target;
    }

    private static String getPropertyName(String methodName) {//setAge ---> age
        char[] newChar = new char[methodName.length() - 3];
        System.arraycopy(methodName.toCharArray(), 3, newChar, 0, methodName.length() - 3);
        newChar[0] = Character.toLowerCase(newChar[0]);
        return String.valueOf(newChar);
    }


    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String copierKey = generateKey(sourceClass, targetClass);
        BeanCopier copier = copierCaches.get(copierKey);
        if (copier == null) {
            copier = BeanCopier.create(sourceClass, targetClass, true);
            copierCaches.put(copierKey, copier);
        }
        return copier;
    }


    private static String generateKey(Class<?> sourceClass, Class<?> targetClass) {
        return sourceClass.toString() + targetClass.toString();
    }
}
