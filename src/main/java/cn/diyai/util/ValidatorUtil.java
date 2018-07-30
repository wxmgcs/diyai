package cn.diyai.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by diyai on 2018/7/30.
 */
public class ValidatorUtil {
    /**
     *
     * 判定输入汉字
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;
        }
        return false;
    }


    static final char CN_CHAR_START = '\u4e00';
    static final char CN_CHAR_END   = '\u9fa5';

    /**
     * 是否包含中文字符
     *
     * @param str 要判断的字符串
     * @return 是否包含中文字符
     */
    public static boolean containsChineseChar(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= CN_CHAR_START && chars[i] <= CN_CHAR_END) return true;
        }
        return false;
    }

    /**
     * 参数是否是有效数字 （整数或者小数）
     *
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是数字
     */
    public static boolean isNumber(Object obj) {
        if (obj instanceof Number) return true;
        return isInt(obj) || isDouble(obj);
    }

    /**
     * 参数是否是有效整数
     *
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是整数
     */
    public static boolean isInt(Object obj) {
        if (isNullOrEmpty(obj))
            return false;
        if (obj instanceof Integer)
            return true;
        return obj.toString().matches("[-+]?\\d+");
    }

    /**
     * 字符串参数是否是double
     *
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是double
     */
    public static boolean isDouble(Object obj) {
        if (isNullOrEmpty(obj))
            return false;
        if (obj instanceof Double || obj instanceof Float)
            return true;
        return compileRegex("[-+]?\\d+\\.\\d+").matcher(obj.toString()).matches();
    }

    /**
     * 编译后的正则表达式缓存
     */
    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();

    /**
     * 编译一个正则表达式，并且进行缓存,如果缓存已存在则使用缓存
     *
     * @param regex 表达式
     * @return 编译后的Pattern
     */
    public static final Pattern compileRegex(String regex) {
        Pattern pattern = PATTERN_CACHE.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            PATTERN_CACHE.put(regex, pattern);
        }
        return pattern;
    }

    /**
     * 对象是否为无效值
     *
     * @param obj 要判断的对象
     * @return 是否为有效值（不为null 和 "" 字符串）
     */
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || "".equals(obj.toString());
    }

    /**
     * 是否是乱码
     * @param strName
     * @return
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }
}
