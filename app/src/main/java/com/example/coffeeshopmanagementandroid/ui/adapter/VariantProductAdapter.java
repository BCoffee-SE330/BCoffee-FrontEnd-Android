package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.product.ProductVariantModel;
import com.example.coffeeshopmanagementandroid.ui.component.VariantProductChipButton;

import java.util.List;

public class VariantProductAdapter extends RecyclerView.Adapter<VariantProductAdapter.ViewHolder> {
    private final List<ProductVariantModel> variantsList;
    private int selectedPosition = -1;

    private OnVariantSelectedListener onVariantSelectedListener;

    public interface OnVariantSelectedListener {
        void onVariantSelected(int position);
    }

    public VariantProductAdapter(List<ProductVariantModel> variantsList) {
        this.variantsList = variantsList;
        if (variantsList != null) {
            if (variantsList.size() > 2) {
                selectedPosition = 1;
            } else if (variantsList.size() > 0) {
                selectedPosition = 0;
            }
        }
    }

    @NonNull
    @Override
    public VariantProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng VariantProductChipButton làm layout cho mỗi item
        VariantProductChipButton variantProductChipButton = new VariantProductChipButton(parent.getContext());
        return new ViewHolder(variantProductChipButton);
    }

    @Override
    public void onBindViewHolder(@NonNull VariantProductAdapter.ViewHolder holder, int position) {
        holder.variantProductChipButton.setText(variantsList.get(position).getVariantTierIdx());
        holder.variantProductChipButton.setSeletected(position == selectedPosition);
        holder.variantProductChipButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != selectedPosition) {
                int previous = selectedPosition;
                selectedPosition = pos;
                if (previous != -1) {
                    notifyItemChanged(previous);
                }
                notifyItemChanged(pos);
                if (onVariantSelectedListener != null) {
                    onVariantSelectedListener.onVariantSelected(selectedPosition);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return variantsList.size();
    }

    public String getSelectedVariant() {
        if (selectedPosition != -1) {
            return variantsList.get(selectedPosition).getId();
        }
        return null; // Hoặc giá trị mặc định nếu không có chip nào được chọn
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        VariantProductChipButton variantProductChipButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            variantProductChipButton = (VariantProductChipButton) itemView;
        }
    }

    public void setOnVariantSelectedListener(OnVariantSelectedListener listener) {
        this.onVariantSelectedListener = listener;
    }
}
