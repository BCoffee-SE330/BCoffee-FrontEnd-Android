package com.example.coffeeshopmanagementandroid.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.coffeeshopmanagementandroid.R;


public class VariantProductChipButton extends LinearLayout {
    private TextView variantProductTextView;
    private boolean isSelected;
    private int normalTextcolor;
    private int selectedTextcolor;

    public VariantProductChipButton(Context context) {
        super(context);
        init(context);
    }

    public VariantProductChipButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VariantProductChipButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public VariantProductChipButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.variant_product_layout, this, true);
        this.variantProductTextView = findViewById(R.id.variantProductTextView);
        normalTextcolor = context.getResources().getColor(R.color.black);
        selectedTextcolor = context.getResources().getColor(R.color.white);

        updateAppearance();
//        setOnClickListener(v -> toggleSelection());
    }


    private void updateAppearance() {
        if(isSelected) {
            variantProductTextView.setBackground(getResources().getDrawable(R.drawable.bg_variant_product_chip_selected));
            variantProductTextView.setTextColor(selectedTextcolor);
        } else {
            variantProductTextView.setBackground(getResources().getDrawable(R.drawable.bg_variant_product_chip));
            variantProductTextView.setTextColor(normalTextcolor);
        }
    }

    public void setSeletected(boolean selected) {
        isSelected = selected;
        updateAppearance();
    }

    public void setText(String variantProduct) {
        variantProductTextView.setText(variantProduct);
    }
}
