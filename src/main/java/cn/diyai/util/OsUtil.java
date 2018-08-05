package cn.diyai.util;

public class OsUtil {
    /**
     * 获取当前系统的类型
     * @return
     */
    public static String getOS(){
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 判断当前系统是否是window
     * @return
     */
    public static boolean isWindows(){
        return getOS().indexOf("windows") >=0;
    }
}
