package cn.diyai.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static final DateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
    private static final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SS");
    private static final SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat format4 = new SimpleDateFormat("yyyy_MM_dd");

    public static String localtime(){
        Date date = new Date();
        try {
            return sdf.format(date);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

    public static String getLocalTime(){
        return format2.format(new Date());
    }

    public static String getLocalTime2(){
        return format3.format(new Date());
    }

    public static String today(){
        return format4.format(new Date());
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static String getTimeStamp(){
        return System.currentTimeMillis()+"";
    }

}
