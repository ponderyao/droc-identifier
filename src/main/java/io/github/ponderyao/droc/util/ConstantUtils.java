package io.github.ponderyao.droc.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * ConstantUtils：
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ConstantUtils {

    private static final Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");

    
    public static Map<String, Object> constFieldsToMap(Class<?> clazz) {
        Map<String, Object> fieldMap = new HashMap<>();
        Field[] fields = clazz.getFields();
        Stream.of(fields).forEach(field -> {
            int modifiers = field.getModifiers();
            boolean flag = (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
            if (flag) {
                String fieldName = field.getName();
                String key = underlineToHump(fieldName.toLowerCase());
                try {
                    Object fieldValue = field.get(null);
                    fieldMap.put(key, fieldValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return fieldMap;
    }

    public static String underlineToHump(String str){
        Matcher matcher = UNDERLINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if (matcher.find()) {
            sb = new StringBuffer();
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            matcher.appendTail(sb);
        } else {
            return sb.toString().replaceAll("_", "");
        }
        return underlineToHump(sb.toString());
    }

    public static String humpToUnderline(String str){
        if (StringUtils.isBlank(str)){
            return "";
        }
        str = String.valueOf(str.charAt(0)).toUpperCase().concat(str.substring(1));
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            String word=matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == str.length() ? "" : "_");
        }
        return sb.toString();
    }
    
}
