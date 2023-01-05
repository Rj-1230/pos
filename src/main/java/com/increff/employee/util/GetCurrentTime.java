package com.increff.employee.util;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Date;

public class GetCurrentTime {
    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strTime = formatter.format(date);
        return strTime;
    }
}
