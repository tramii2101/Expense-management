package com.example.expensemanagement.ui.custom;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    int span;
    int listSize;

    public SpacesItemDecoration(int space, int span) {
        this.space = space;
        this.span = span;
    }

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = space;
        outRect.right = space;

        if (parent.getChildLayoutPosition(view) % span == 1) {
            outRect.right = space;
            outRect.left = space;
        }

        outRect.bottom = space;
        outRect.top = space;
//        if (parent.getChildLayoutPosition(view) > span && parent.getChildLayoutPosition(view) < (listSize / span) * span) {
//            outRect.bottom = space;
//            outRect.top = space;
//        }


    }
}
