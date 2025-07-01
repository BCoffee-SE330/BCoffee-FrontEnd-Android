package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.address.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private final List<AddressModel> addresses;
    private final OnItemClickListener onItemClickListener;

    public AddressAdapter(List<AddressModel> addresses, OnItemClickListener onItemClickListener) {
        this.addresses = addresses != null ? addresses : new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.bind(addresses.get(position));
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconLocation;
        private final TextView tvAddress;
        private final ImageView iconEditAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            iconLocation = itemView.findViewById(R.id.iconLocation);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            iconEditAddress = itemView.findViewById(R.id.iconEditAddress);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(addresses.get(position));
                }
            });

//            iconEditAddress.setOnClickListener(v -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    // Xử lý click icon sửa đổi riêng biệt
//                    // Ví dụ gọi callback khác nếu bạn thêm, hoặc tạm Toast
//                    Toast.makeText(itemView.getContext(), "Edit clicked", Toast.LENGTH_SHORT).show();
//                }
//            });
        }


        public void bind(AddressModel address) {
            if (address == null) return;
            String twoLineAddress = address.getAddressLine() + "\n" + address.getAddressDistrict() + ", " + address.getAddressCity();
            tvAddress.setText(twoLineAddress);
        }
    }

    public void updateList(List<AddressModel> newList) {
        this.addresses.clear();
        this.addresses.addAll(newList);
        notifyDataSetChanged();
    }



    public interface OnItemClickListener {
        void onItemClick(AddressModel address);
    }
}
