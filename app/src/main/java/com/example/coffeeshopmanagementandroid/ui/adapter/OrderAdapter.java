package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.OrderModel;
import com.example.coffeeshopmanagementandroid.ui.component.StatusIndicator;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderModel> orders;
    private final OnItemClickListener onItemClick;

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(OrderModel order);
    }

    public OrderAdapter(List<OrderModel> orders, OnItemClickListener onItemClick) {
        this.orders = orders != null ? orders : new ArrayList<>();
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderModel order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderIdTextView;
        private final StatusIndicator statusIndicator;
        private final TextView totalOrderTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
            totalOrderTextView = itemView.findViewById(R.id.totalOrderTextView);
            // Xử lý sự kiện nhấn vào item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    onItemClick.onItemClick(orders.get(position));
                }
            });

        }

        public void bind(OrderModel order) {
            // Bind dữ liệu vào các thành phần giao diện ở đây
            orderIdTextView.setText(order.getOrderId());
            statusIndicator.setStatus(order.getOrderStatus());
            totalOrderTextView.setText(CurrencyFormat.formatVND(order.getOrderTotalCost()));
        }
    }
}
