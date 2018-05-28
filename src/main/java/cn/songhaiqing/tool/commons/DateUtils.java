package cn.songhaiqing.tool.commons;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String dateToLongString(Date date){
        if(date == null)
            return null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
    
}
