package com.purutal.dateconverteractivity.DateConverter;

import androidx.annotation.IntRange;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Model {

    private int day;
    private int year;
    private int month;
    private int dayOfWeek;
    private String engDate;

    public String getEngDate() {
        return engDate;
    }

    public void setEngDate(String engDate) {
        this.engDate = engDate;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(@IntRange(from = 1, to = 7) int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Model() {
        GregorianCalendar date = new GregorianCalendar();
        day = date.get(GregorianCalendar.DAY_OF_MONTH);
        month = date.get(GregorianCalendar.MONTH) + 1;
        year = date.get(GregorianCalendar.YEAR);
        dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
    }

    public Model(@IntRange(from=1970,to=2090) int year,
                 @IntRange(from = 0, to = 12) int month,
                 @IntRange(from = 1, to = 32) int day) {
        this.year = year;
        this.month = month;
        this.day = day;

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

}