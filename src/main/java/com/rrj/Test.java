package com.rrj;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test
{
    public static void main(String[] args) throws ParseException {

        String time = "Jun 9, 2022 10:48:00 UTC";

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
        Date date = dateFormat.parse(time);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        System.err.println(sdf.format(calendar.getTime()));
    }
}
