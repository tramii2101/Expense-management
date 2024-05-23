package com.example.expensemanagement.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BarChartView extends View {
    private Paint paint;
    private Paint labelPaint;
    private int textSize;
    private int colors[];
    private long data[];
    private String labels[];

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setColors(int[] colors) {
        this.colors = colors;
        invalidate();
    }

    public void setData(long[] data) {
        this.data = data;
        invalidate();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        labelPaint.setTextSize(textSize);
        invalidate();
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
        invalidate();
    }

    void init() {
        paint = new Paint();

        labelPaint = new Paint();
        labelPaint.setTextSize(16);
        labelPaint.setColor(getResources().getColor(android.R.color.darker_gray));
        labelPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int barHeight = height / data.length;

        long maxValue = 0;

        for (long value : data) {
            if (value > maxValue) {
                maxValue = value;
            }
        }

        for (int i = 0; i < data.length; i++) {
            paint.setColor(colors[i]);
            int barWidth = (int) ((data[i] / (float) maxValue) * height * 0.8); // Chiều rộng của cột
            int left = 0;
            int top = i * barHeight;
            int right = barWidth;
            int bottom = top + barHeight;

            // Vẽ cột
            canvas.drawRect(left, top, right, bottom, paint);

            if (labels != null && labels.length > i) {
                // Vẽ nhãn trục y
                canvas.drawText(labels[i] + (i + 1), left + 30, top + (float) barHeight / 2 + (float) textSize / 2, labelPaint);
            }
        }

        // Vẽ nhãn trục x
        for (int j = 0; j <= maxValue; j += 10) {
            int xPos = (int) ((j / (float) maxValue) * width * 0.8);
            canvas.drawText(String.valueOf(j), xPos, height - 10, labelPaint);
        }
    }


}
