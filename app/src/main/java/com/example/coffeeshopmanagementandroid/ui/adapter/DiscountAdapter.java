package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.DiscountModel;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder> {
    private List<DiscountModel> discounts;
    private int selectedPosition = -1;
    private final OnDiscountSelectedListener listener;

    public DiscountAdapter(List<DiscountModel> discounts, OnDiscountSelectedListener listener) {
        this.discounts = discounts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_item_layout, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
        DiscountModel discount = discounts.get(position);
        holder.bind(discount, position);
    }

    @Override
    public int getItemCount() {
        return discounts.size();
    }

    public class DiscountViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivDiscountImage;
        private final TextView tvDiscountName;
        private final TextView tvDiscountCondition;
        private final TextView tvDiscountExpiry;


        public DiscountViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDiscountImage = itemView.findViewById(R.id.ivDiscountImage);
            tvDiscountName = itemView.findViewById(R.id.tvDiscountName);
            tvDiscountCondition = itemView.findViewById(R.id.tvDiscountCondition);
            tvDiscountExpiry = itemView.findViewById(R.id.tvDiscountExpiry);

            itemView.setOnClickListener(v -> {
                try {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectedPosition = position;
                        notifyDataSetChanged();
                        if (listener != null) {
                            listener.onDiscountSelected(discounts.get(position));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        public void bind(DiscountModel discount, int position) {
            ivDiscountImage.setImageResource(R.drawable.discount_icon); // Thay bằng hình ảnh thực tế nếu cần
            tvDiscountName.setText(discount.getDiscountName());

            String condition = "Đơn tối thiểu: " + CurrencyFormat.formatVND(discount.getDiscountMinOrderValue());
            tvDiscountCondition.setText(condition);

            DateTimeFormatter dtf = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && discount.getDiscountStartDate() != null) {
                tvDiscountExpiry.setText("Hết hạn: " + discount.getDiscountEndDate().format(dtf));
            }
            itemView.setAlpha(1.0f);
            itemView.setEnabled(true);
        }
    }

    public void setDiscounts(List<DiscountModel> discounts) {
        this.discounts.clear();
        if (discounts != null) {
            this.discounts.addAll(discounts);
        }
        notifyDataSetChanged();
    }

    public interface OnDiscountSelectedListener {
        void onDiscountSelected(DiscountModel discount);
    }
}