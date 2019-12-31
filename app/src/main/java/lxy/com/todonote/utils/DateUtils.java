package lxy.com.todonote.utils;

import java.text.SimpleDateFormat;

/**
 * Creator : lxy
 * date: 2019/12/31
 */
public class DateUtils {

    public static String getDateString(long time, String... format){
        String dateStr = "yyyy-MM-hh";
        if (format.length > 0){
            dateStr = format[0];
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateStr);
        return sdf.format(time);
    }
}
