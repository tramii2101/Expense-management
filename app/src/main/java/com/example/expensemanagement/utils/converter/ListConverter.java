package com.example.expensemanagement.utils.converter;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;

public class ListConverter {
    private static final String COMMA = ",";

        @TypeConverter
        public static String arrayListToString(ArrayList<String> arrayList) {
            if (arrayList == null || arrayList.isEmpty()) {
                return null;
            }

            StringBuilder string = new StringBuilder();
            for (String item : arrayList) {
                if (item != arrayList.get(arrayList.size() - 1)) {
                    string.append(item).append(COMMA);
                } else {
                    string.append(item);
                }
            }
            return string.toString();
        }

        @TypeConverter
        public static ArrayList<String> stringToArrayList(String string) {
            if (string == null || string.isEmpty()) {
                return null;
            }

            if (!string.contains(COMMA)) {
                ArrayList<String> list = new ArrayList<>();
                list.add(string);
                return list;
            } else {
                return new ArrayList<>(Arrays.asList(string.split(COMMA)));
            }
        }

}
