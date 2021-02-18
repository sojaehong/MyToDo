package com.ssostudio.mytodo.utility;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateManager {

    // 현재 타임스탬프
    public static long getTimestamp(){
        Calendar calendar = Calendar.getInstance();

        Date date = calendar.getTime();
        long timestamp = date.getTime();

        return timestamp;
    }

    public static int[] calendarDayToIntArray(CalendarDay date){
        int year = date.getYear();
        int month = date.getMonth() + 1;
        int day = date.getDay();

        int[] dates = {year, month, day};

        return dates;
    }

    public static String dateTimeZoneFormat(int[] date){
        Calendar calendar = Calendar.getInstance();

        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT);
        format.setTimeZone(calendar.getTimeZone());
        calendar.set(date[0], date[1] - 1, date[2]);
        String formatDate = format.format(calendar.getTime());

        return formatDate;
    }

    public static String dateTimeZoneFormat(long timestamp){
        Calendar calendar = Calendar.getInstance();

        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT);
        format.setTimeZone(calendar.getTimeZone());

        Date date = new Date(timestamp);
        calendar.setTime(date);

        String formatDate = format.format(calendar.getTime());

        return formatDate;
    }

    public static String dateTimeZoneFullFormat(int[] date){
        Calendar calendar = Calendar.getInstance();

        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
        format.setTimeZone(calendar.getTimeZone());
        calendar.set(date[0], date[1] - 1, date[2]);
        String formatDate = format.format(calendar.getTime());

        return formatDate;
    }

    public static String dateTimeZoneFullFormat(long timestamp){
        Calendar calendar = Calendar.getInstance();

        DateFormat format = DateFormat.getDateInstance(DateFormat.FULL);
        format.setTimeZone(calendar.getTimeZone());

        Date date = new Date(timestamp);
        calendar.setTime(date);

        String formatDate = format.format(calendar.getTime());

        return formatDate;
    }

    public static String dateTimeZoneSimpleFormat(long timestamp){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format1.setTimeZone(calendar.getTimeZone());

        Date date = new Date(timestamp);
        calendar.setTime(date);

        String formatDate = format1.format(calendar.getTime());

        return formatDate;
    }


    public static int[] timestampToIntArray(long timestamp){
        Calendar calendar = Calendar.getInstance();

        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT);
        format.setTimeZone(calendar.getTimeZone());

        Date date = new Date(timestamp);
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int[] dates = {year, month, day};

        return dates;
    }

    public static long intArrayToTimestamp(int[] dates){
        Calendar calendar = Calendar.getInstance();
        // ????
//        DateFormat format = DateFormat.getDateInstance(DateFormat.DEFAULT);
//        format.setTimeZone(calendar.getTimeZone());
        //
        calendar.set(dates[0], dates[1] - 1, dates[2]);
        Date date = calendar.getTime();
        long timestamp = date.getTime();

        return timestamp;
    }

    public static long getFreeDateTimestamp(int freeDay){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, freeDay);

        Date date = calendar.getTime();
        long timestamp = date.getTime();

        return timestamp;
    }

    public static long changeDate(int[] dates, int changeDate){

        Calendar calendar = Calendar.getInstance();
        calendar.set(dates[0], dates[1] - 1, dates[2]);
        calendar.add(Calendar.DATE, changeDate);
        Date date = calendar.getTime();
        long timestamp = date.getTime();

        return timestamp;
    }

    public static long dayStartTimestamp(long timestamp){
        Calendar calendar = Calendar.getInstance();

        int[] dateArray = timestampToIntArray(timestamp);

        calendar.set(dateArray[0], dateArray[1] -1, dateArray[2], 0,0,0);

        Date date = calendar.getTime();

        return date.getTime();
    }

    public static long dayEndTimestamp(long timestamp){
        Calendar calendar = Calendar.getInstance();

        int[] dateArray = timestampToIntArray(timestamp);

        calendar.set(dateArray[0], dateArray[1] -1, dateArray[2], 23,59,59);

        Date date = calendar.getTime();

        return date.getTime();
    }

    public static long monthStartTimestamp(int year, int month){
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, 1,0,0,0);

        Date date = calendar.getTime();

        return date.getTime();
    }

    public static long monthEndTimestamp(int year, int month){
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, 1,0,0,0);

        int lastday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.set(year, month - 1, lastday,0,0,0);

        Date date = calendar.getTime();

        Log.d("DateTest" , ""+lastday);

        return date.getTime();
    }

    // 년도 시작 타임 스템프
    public static long yearStartTimestamp(int year){
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, 0,1,0,0,0);

        Date date = calendar.getTime();

        return date.getTime();
    }

    // 년도 마지막 타임 스템프
    public static long yearEndTimestamp(int year){
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, 11,31,0,0,0);

        Date date = calendar.getTime();

        return date.getTime();
    }

    // 년도 타임 스템프
    public static long yearSelectTimestamp(int year){
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, 9,9,0,0,0);

        Date date = calendar.getTime();

        return date.getTime();
    }

    public static long monthSelectTimestamp(int year,int month){
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, 13, 0,0,0);

        Date date = calendar.getTime();

        return date.getTime();
    }

    // 날짜 차이 구하기
    public static long calDateBetweenAandB(long a, long b){
        long calDate = a - b ;
        long calDateDays = calDate / (24*60*60*1000);
        calDateDays = Math.abs(calDateDays);

        return calDateDays;
    }

}
