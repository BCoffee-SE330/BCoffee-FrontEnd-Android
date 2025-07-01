package com.example.coffeeshopmanagementandroid.ui.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.CategoryModel;

public class CategoryChipButton extends LinearLayout {
    private TextView categoryChipTextView;
    private LinearLayout chipContainer;
    private boolean isSelected;

    private Drawable normalBackgroundcolor;
    private int normalTextcolor;
    private Drawable selectedBackgroundcolor;
    private int selectedTextcolor;

    public CategoryChipButton(Context context) {
        super(context);
        init(context);
    }

    public CategoryChipButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CategoryChipButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chip_category_layout, this, true);
        categoryChipTextView = findViewById(R.id.categoryChipTextView);
        chipContainer = findViewById(R.id.chipContainer);

        normalBackgroundcolor = context.getResources().getDrawable(R.drawable.bg_category_unselected);
        normalTextcolor = context.getResources().getColor(R.color.black);
        selectedBackgroundcolor = context.getResources().getDrawable(R.drawable.bg_category_selected);
        selectedTextcolor = context.getResources().getColor(R.color.white);

        updateAppearance();
        // Xóa dòng này: setOnClickListener(v -> toggleSelection());
    }

    private void updateAppearance() {
        if (isSelected) {
            chipContainer.setBackground(selectedBackgroundcolor);
            categoryChipTextView.setTextColor(selectedTextcolor);
        } else {
            chipContainer.setBackground(normalBackgroundcolor);
            categoryChipTextView.setTextColor(normalTextcolor);
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        updateAppearance();
    }

    public void setText(CategoryModel categoryModel) {
        categoryChipTextView.setText(categoryModel.getCategoryName());
    }
}