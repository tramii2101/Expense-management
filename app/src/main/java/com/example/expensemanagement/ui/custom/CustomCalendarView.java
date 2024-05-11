package com.example.expensemanagement.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

public class CustomCalendarView extends CalendarView implements GestureDetector.OnGestureListener {
    int currentMonth;

    Date date = new Date();
    public CustomCalendarView(@NonNull Context context) {
        super(context);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            assert e1 != null;
            if (e1.getX() < e2.getX()) {
                // Fling to the right (previous month)
                currentMonth--;
                if (currentMonth < Calendar.JANUARY) {
                    currentMonth = Calendar.DECEMBER - 1; // Wrap around to December
                }
            } else {
                // Fling to the left (next month)
                currentMonth++;
                if (currentMonth > Calendar.DECEMBER) {
                    currentMonth = Calendar.JANUARY + 1; // Wrap around to January
                }
            }
        }
        return true;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

}
