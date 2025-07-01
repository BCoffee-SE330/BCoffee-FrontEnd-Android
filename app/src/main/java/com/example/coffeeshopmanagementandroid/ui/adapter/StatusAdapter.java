package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.domain.model.OrderStatusModel;
import com.example.coffeeshopmanagementandroid.ui.component.OrderStatusButton;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    private final List<OrderStatusModel> statuses;
    private int selectedPosition = -1;
    private OnStatusSelectedListener onStatusSelectedListener;

    public interface OnStatusSelectedListener {
        void onStatusSelected(String status);
    }
    public void setOnStatusSelectedListener(OnStatusSelectedListener listener) {
        this.onStatusSelectedListener = listener;
    }

    public StatusAdapter(List<OrderStatusModel> statuses) {
        this.statuses = statuses != null ? statuses : new ArrayList<>();
    }

    @NonNull
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderStatusButton orderStatusButton = new OrderStatusButton(parent.getContext());
        return new ViewHolder(orderStatusButton);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, int position) {
        OrderStatusModel status = statuses.get(position);
        holder.orderStatusButton.setText(statuses.get(position));
        holder.orderStatusButton.setSelected(position == selectedPosition);
        holder.orderStatusButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == selectedPosition) {
                // Bỏ chọn nếu đang được chọn
                selectedPosition = -1;
                notifyItemChanged(pos);
                if (onStatusSelectedListener != null) {
                    onStatusSelectedListener.onStatusSelected(null);
                }
            } else {
                // Chọn chip mới và bỏ chọn chip cũ (nếu có)
                int previous = selectedPosition;
                selectedPosition = pos;
                if (previous != -1) {
                    notifyItemChanged(previous);
                }
                notifyItemChanged(pos);
                if (onStatusSelectedListener != null) {
                    onStatusSelectedListener.onStatusSelected(status.getStatusName());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        OrderStatusButton orderStatusButton;

        public ViewHolder(@NonNull OrderStatusButton itemView) {
            super(itemView);
            orderStatusButton = itemView;
        }
    }
}
