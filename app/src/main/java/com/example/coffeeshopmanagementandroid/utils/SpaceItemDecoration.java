package com.example.coffeeshopmanagementandroid.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceTop = 0;
    private int spaceBottom = 0;
    private int spaceStart = 0;
    private int spaceEnd = 0;
    private boolean isGrid = false;
    private int spanCount = 0; // for Grid

    public SpaceItemDecoration() {
    }

    // Constructor toàn bộ khoảng cách
    public SpaceItemDecoration(int top, int start, int bottom, int end) {
        this.spaceTop = top;
        this.spaceStart = start;
        this.spaceBottom = bottom;
        this.spaceEnd = end;
    }

    public SpaceItemDecoration vertical(int verticalSpace) {
        this.spaceTop = verticalSpace;
        this.spaceBottom = verticalSpace;
        return this;
    }

    public SpaceItemDecoration horizontal(int horizontalSpace) {
        this.spaceStart = horizontalSpace;
        this.spaceEnd = horizontalSpace;
        return this;
    }

    public SpaceItemDecoration setTop(int top) {
        this.spaceTop = top;
        return this;
    }

    public SpaceItemDecoration setBottom(int bottom) {
        this.spaceBottom = bottom;
        return this;
    }

    public SpaceItemDecoration setStart(int start) {
        this.spaceStart = start;
        return this;
    }

    public SpaceItemDecoration setEnd(int end) {
        this.spaceEnd = end;
        return this;
    }

    public SpaceItemDecoration grid(int spanCount, int spacing) {
        this.isGrid = true;
        this.spanCount = spanCount;
        this.spaceTop = spacing;
        this.spaceBottom = spacing;
        this.spaceStart = spacing;
        this.spaceEnd = spacing;
        return this;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        if (isGrid) {
            // Grid Layout
            int column = position % spanCount;

            // Set horizontal space for grid
            outRect.left = column == 0 ? 0 : spaceStart / 2;
            outRect.right = (column == spanCount - 1) ? 0 : spaceStart / 2;

            // Set vertical space for grid
            if (position >= spanCount) {
                outRect.top = spaceTop;
            }

            outRect.bottom = spaceBottom;
        } else {
            // Linear Layout
            if (position == 0) {
                outRect.top = 0;
            } else {
                outRect.top = spaceTop;
            }

            if (position == itemCount - 1) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = spaceBottom;
            }

            outRect.left = spaceStart;
            outRect.right = spaceEnd;
        }
    }
}