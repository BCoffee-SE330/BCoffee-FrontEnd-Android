package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;
import com.example.coffeeshopmanagementandroid.utils.helper.CurrencyFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder> {

    private final List<CartItemModel> products;
    private final OnMinusQuantityListener onMinusQuantity;
    private final OnDeleteProductListener onDeleteProduct;
    private final OnPlusQuantityListener onPlusQuantity;
    private final OnItemClickListener onItemClickListener;

    public ProductCartAdapter(List<CartItemModel> products, OnMinusQuantityListener onMinusQuantity, OnDeleteProductListener onDeleteProduct, OnPlusQuantityListener onPlusQuantity, OnItemClickListener onItemClickListener) {
        this.products = products != null ? products : new ArrayList<>();
        this.onMinusQuantity = onMinusQuantity;
        this.onDeleteProduct = onDeleteProduct;
        this.onPlusQuantity = onPlusQuantity;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProductCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_layout, parent, false);
        return new ProductCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public interface OnMinusQuantityListener {
        void onMinusQuantity(CartItemModel product);
    }

    public interface OnPlusQuantityListener {
        void onPlusQuantity(CartItemModel product);
    }

    public interface OnDeleteProductListener {
        void onDeleteProduct(CartItemModel product);
    }

    public interface OnItemClickListener {
        void onItemClick(CartItemModel product);
    }

    public void updateList(List<CartItemModel> newList) {
        this.products.clear();
        this.products.addAll(newList);
        notifyDataSetChanged(); // hoặc dùng DiffUtil nếu bạn muốn tối ưu
    }

    public int getItemPosition(CartItemModel targetProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getVariantId() == targetProduct.getVariantId()) {
                return i;
            }
        }
        return -1; // không tìm thấy
    }

    public class ProductCartViewHolder extends RecyclerView.ViewHolder {
        private final TextView productNameTextView;
        private final TextView variantProductTextView;
        private final TextView priceTextView;
        private final TextView quantityTextView;
        private final ImageButton minusButton;
        private final Button deleteButton;
        private final ImageButton plusButton;
        private final ImageView productImageView;


        public ProductCartViewHolder(@NonNull View itemView) {
            super(itemView);
            this.productNameTextView = itemView.findViewById(R.id.productNameTextView);
            this.variantProductTextView = itemView.findViewById(R.id.variantProductTextView);
            this.priceTextView = itemView.findViewById(R.id.priceTextView);
            this.quantityTextView = itemView.findViewById(R.id.quantityTextView);
            this.minusButton = itemView.findViewById(R.id.minusButton);
            this.deleteButton = itemView.findViewById(R.id.deleteButton);
            this.plusButton = itemView.findViewById(R.id.plusButton);
            this.productImageView = itemView.findViewById(R.id.productImageView);

            this.minusButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    onMinusQuantity.onMinusQuantity(products.get(position));
                }
            });

            this.deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    onDeleteProduct.onDeleteProduct(products.get(position));
                }
            });

            this.plusButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    onPlusQuantity.onPlusQuantity(products.get(position));
                }
            });

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(products.get(position));
                }
            });
        }
        public void bind(CartItemModel product){
            productNameTextView.setText(product.getProductName());
            variantProductTextView.setText(product.getVariantTierIdx());
            priceTextView.setText(CurrencyFormat.formatVND(BigDecimal.valueOf(product.getCartDetailUnitPriceAfterDiscount())));
            quantityTextView.setText(String.valueOf(product.getCartDetailQuantity()));

            if (product.getProductThumb() != null && !product.getProductThumb().isEmpty()) {
                Glide.with(productImageView.getContext())
                        .load(product.getProductThumb())
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .into(productImageView);
            } else {
                productImageView.setImageResource(R.drawable.placeholder_image);
            }
        }
    }
}
