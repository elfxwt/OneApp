package com.example.sophia_xu.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sophia_Xu on 2015/8/25.
 */
public class DateUitls {

    //  Non Aug 31 19:37:03 +0800 2015

    public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
    public static final long ONE_DAY_MILLINONS = 60 * ONE_HOUR_MILLIONS;

    public static String getShortTime(String dateStr){
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);

        try {
            Date date = sdf.parse(dateStr);
            Date curDate = new Date();

            Long durTime = curDate.getTime() - date.getTime();
            int dayStatus = calculateDayStatus(date, curDate);

            if (durTime < ONE_MINUTE_MILLIONS) {
                str = "刚刚";

            } else if (durTime < ONE_HOUR_MILLIONS) {
                str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
            } else if (dayStatus == 0) {
                str = durTime / ONE_HOUR_MILLIONS + "小时前";

            } else if (dayStatus == -1) {
                str = "昨天" + android.text.format.DateFormat.format("HH:mm", date);  // 注意这里的 dateformat 不是Java里的
            } else if(isSameYear(date,curDate) && dayStatus< -1){
                str = android.text.format.DateFormat.format("MM-dd",date).toString();
            }else{
                str = android.text.format.DateFormat.format("yyyy-MM",date).toString();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

            return str;
    }


    public static boolean isSameYear(Date targetTime,Date compareTime){
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);
        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comparYear = compareCalendar.get(Calendar.YEAR);
        return tarYear == comparYear;
    }

    public static int calculateDayStatus(Date targetTime,Date compareTime){
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);
        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);
        return tarDayOfYear - comDayOfYear;

    }

}
