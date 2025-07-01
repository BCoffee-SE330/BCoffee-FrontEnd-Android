package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.address.AddressModel;
import com.example.coffeeshopmanagementandroid.domain.model.DiscountModel;
import com.example.coffeeshopmanagementandroid.domain.model.cart.CartItemModel;
import com.example.coffeeshopmanagementandroid.domain.model.payment.PaymentMethodModel;

import java.sql.Timestamp;
import java.util.List;

public class ChooseAddressAdapter extends RecyclerView.Adapter<ChooseAddressAdapter.ChooseAddressViewHolder>{
    private final List<AddressModel> addresses;
    private int selectedPosition = -1;
    private final OnAddressSelectedListener listener;

    public ChooseAddressAdapter(List<AddressModel> addresses, int defaultPosition, ChooseAddressAdapter.OnAddressSelectedListener listener) {
        this.addresses = addresses;
        this.selectedPosition = defaultPosition;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChooseAddressAdapter.ChooseAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choosee_address_item_layout, parent, false);
        return new ChooseAddressAdapter.ChooseAddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseAddressAdapter.ChooseAddressViewHolder holder, int position) {
        AddressModel address = addresses.get(position);
        holder.bind(address, position);
    }

    public void updateList(List<AddressModel> newList) {
        this.addresses.clear();
        this.addresses.addAll(newList);
        notifyDataSetChanged(); // hoặc dùng DiffUtil nếu bạn muốn tối ưu
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public AddressModel getSelectedAddress() {
        if (selectedPosition != -1) {
            return addresses.get(selectedPosition);
        }
        return null;
    }

    public class ChooseAddressViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAddress;
        private final RadioButton rbAddress;

        public ChooseAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            rbAddress = itemView.findViewById(R.id.rbAddress);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && selectedPosition != position) {
                    selectedPosition = position;
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onAddressSelected(addresses.get(position));
                    }
                }
            });
        }

        public void bind(AddressModel address, int position) {
            if (address == null) return;
            String twoLineAddress = address.getAddressLine() + "\n" + address.getAddressDistrict() + ", " + address.getAddressCity();
            tvAddress.setText(twoLineAddress);
            rbAddress.setChecked(position == selectedPosition);
            // Nếu muốn, set iconLocation và iconEditAddress drawable tại đây (hoặc giữ mặc định trong XML)
        }

        private boolean isDiscountApplicable(DiscountModel discount) {
            return true;
        }
    }

    public interface OnAddressSelectedListener {
        void onAddressSelected(AddressModel discount);
    }
}
