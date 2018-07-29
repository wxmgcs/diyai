package cn.diyai.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    static DateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
    public static String localtime(){
        Date date = new Date();
        try {
            return sdf.format(date);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

}
