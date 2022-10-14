package io.github.ponderyao.droc.util;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ReflectionUtils：
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ReflectionUtils {

    /**
     * 类属性拷贝：将源对象的普通成员变量值赋予目标类的静态成员变量
     * 
     * @param targetClazz 目标类
     * @param sourceObj 源对象
     * @param ignoreFields 忽略属性集
     */
    public static void copyPropertiesAsStatics(Class<?> targetClazz, Object sourceObj, List<String> ignoreFields) {
        Set<String> ignoreFieldSet = new HashSet<>(ignoreFields);
        Field[] fields = targetClazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (ignoreFieldSet.contains(fieldName)) {
                    continue;
                }
                Field targetField = sourceObj.getClass().getDeclaredField(fieldName);
                targetField.setAccessible(true);
                Object targetValue = targetField.get(sourceObj);
                field.set(null, targetValue);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取类静态属性并转化为 map
     * 
     * @param targetClazz 目标类
     * @param ignoreFields 忽略属性集
     * @return map
     */
    public static Map<String, Object> staticPropertiesToMap(Class<?> targetClazz, List<String> ignoreFields) {
        Map<String, Object> resultMap = new LinkedHashMap<>(16);
        Set<String> ignoreFieldSet = new HashSet<>(ignoreFields);
        Field[] fields = targetClazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (ignoreFieldSet.contains(fieldName)) {
                    continue;
                }
                Object fieldValue = field.get(null);
                resultMap.put(fieldName, fieldValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
    
}
