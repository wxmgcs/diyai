package cn.diyai.util;

/**
 * Created by diyai on 2018/7/30.
 */
public class DoubleUtil {
    /**
     * 保留几位小数
     */
    public static String saveDecimals(int cnt, double value) {
        if (cnt == 2)
            return String.format("%.02f", value);
        else if (cnt == 1)
            return String.format("%.01f", value);
        else
            return String.format("%.0f", value);
    }
}
