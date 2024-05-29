package com.example.expensemanagement.utils.converter;

import androidx.room.TypeConverter;

import com.example.expensemanagement.utils.Constant;

import java.text.ParseException;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date fromTimestamp(String value) throws ParseException {
        return value == null ? null : new Date(String.valueOf(Constant.FORMAT.parse(value)));
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {
        return date == null ? null : Constant.FORMAT.format(date);
    }
}