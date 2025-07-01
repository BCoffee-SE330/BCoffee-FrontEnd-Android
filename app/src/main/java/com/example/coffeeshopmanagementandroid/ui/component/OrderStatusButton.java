package com.example.coffeeshopmanagementandroid.ui.component;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.OrderStatusModel;

public class OrderStatusButton extends LinearLayout {
    private TextView tvStatusOrder;
    private View vChooseStatusOrder;
    private boolean isSelected;
    private int normalDividerBackgroundcolor;
    private int normalTextcolor;
    private int selectedBackgroundcolor;
    private int selectedDividerTextcolor;
    public OrderStatusButton(Context context) {
        super(context);
        init(context);
    }

    public OrderStatusButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public  OrderStatusButton (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.status_item_layout, this, true);
        tvStatusOrder = findViewById(R.id.tvStatusOrder);
        vChooseStatusOrder = findViewById(R.id.vChooseStatusOrder);

        normalDividerBackgroundcolor = Color.TRANSPARENT;
        normalTextcolor = context.getResources().getColor(R.color.stroke);
        selectedBackgroundcolor = context.getResources().getColor(R.color.primary_400);
        selectedDividerTextcolor = context.getResources().getColor(R.color.primary_400);

        updateAppearance();
    }

    private void updateAppearance() {
        if (isSelected) {
            vChooseStatusOrder.setBackgroundColor(selectedBackgroundcolor);
            tvStatusOrder.setTextColor(selectedDividerTextcolor);
        } else {
            vChooseStatusOrder.setBackgroundColor(normalDividerBackgroundcolor);
            tvStatusOrder.setTextColor(normalTextcolor);
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        updateAppearance();
    }

    public void setText(OrderStatusModel orderStatusModel) {
        tvStatusOrder.setText(orderStatusModel.getStatusName());
    }
}
