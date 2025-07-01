package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.OrderItemModel;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.OrderProductViewHolder> {
    private final List<CartItemModel> productList;
    @NonNull
    @Override
    public OrderProductAdapter.OrderProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_layout, parent, false);
        return new OrderProductViewHolder(view);
    }

    public OrderProductAdapter(List<CartItemModel> productList) {
        this.productList = productList != null ? productList : new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductAdapter.OrderProductViewHolder holder, int position) {
        CartItemModel product = productList.get(position);
        holder.bind(product);
    }

    public void updateList(List<CartItemModel> newList) {
        this.productList.clear();
        this.productList.addAll(newList);
        notifyDataSetChanged(); // hoặc dùng DiffUtil nếu bạn muốn tối ưu
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public class OrderProductViewHolder extends RecyclerView.ViewHolder {
        private final ImageView productImageView;
        private final TextView productNameTextView;
        private final TextView variantProductTextView;
        private final TextView priceTextView;
        private final TextView quantityTextView;

        public OrderProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            variantProductTextView = itemView.findViewById(R.id.variantProductTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
        }
        public void bind(CartItemModel product) {
            // Bind dữ liệu vào các thành phần giao diện ở đây
            // Sử dụng Glide để tải ảnh từ URL
            if (product.getProductThumb() != null && !product.getProductThumb().isEmpty()) {
                Glide.with(productImageView.getContext())
                        .load(product.getProductThumb())
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .into(productImageView);
            } else {
                productImageView.setImageResource(R.drawable.placeholder_image);
            }
            productNameTextView.setText(product.getProductName());
            variantProductTextView.setText(product.getVariantTierIdx());
            priceTextView.setText(CurrencyFormat.formatVND(BigDecimal.valueOf(product.getCartDetailUnitPrice())));
            quantityTextView.setText(String.valueOf(product.getCartDetailQuantity()));
        }
    }
}
