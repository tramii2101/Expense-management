package com.example.expensemanagement.utils.converter;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(String value) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return value == null ? null : new Date(String.valueOf(sdf.parse(value)));
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return date == null ? null : sdf.format(date);

    }
}