package com.example.expensemanagement.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DoughnutChartView extends View {

    private Paint paint;
    private Paint labelPaint;
    private int[] colors;
    RectF oval = new RectF();
    private long[] data;

    public DoughnutChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    long sumData() {
        long sum = 0;
        for (long i : data) {
            sum += i;
        }
        return sum;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        invalidate();
//        requestLayout();
    }

    public void setData(long[] data) {
        this.data = data;
        invalidate();
//        requestLayout();
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2;
        float holeRadius = radius * 0.7f;

        oval.set(0, 0, width, height);
        float startAngle = 90f;

        for (int i = 0; i < data.length; i++) {

            paint.setColor(colors[i % colors.length]);

            float sweepAngle = (float) data[i] / sumData() * 360;

            canvas.drawArc(oval, startAngle, sweepAngle, true, paint);

            // calculate the label position
            double angle = Math.toRadians(startAngle + sweepAngle / 2);
            float labelX = (float) (width / 2 + radius * 70 * Math.cos(angle));
            float labelY = (float) (height / 2 + radius * 70 * Math.sin(angle));

//            String percentage = String.format("%.1f %%", data[i]);
//            canvas.drawText(percentage, labelX, labelY, labelPaint);
//            canvas.drawText(labels[i], labelX, labelY + 40, labelPaint);

            // Update the start angle for the next slice
            startAngle += sweepAngle;
        }

        // Draw the hole in the middle
        paint.setColor(Color.WHITE);
        canvas.drawCircle(width / 2, height / 2, holeRadius, paint);
    }

    void init() {
        paint = new Paint();
        labelPaint = new Paint();
        paint.setAntiAlias(true);
//        labelPaint.setAntiAlias(true);
//        labelPaint.setColor(Color.BLACK);
    }
}
