package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.branch.BranchModel;

import java.util.ArrayList;
import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder> {
    private List<BranchModel> branches;
    private int selectedPosition = -1;
    private final OnBranchSelectedListener listener;

    public BranchAdapter(List<BranchModel> branches, OnBranchSelectedListener listener) {
        this.branches = branches != null ? branches : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public BranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_branch_item_fragment, parent, false);
        return new BranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchViewHolder holder, int position) {
        BranchModel branch = branches.get(position);
        holder.bind(branch, position);
    }

    @Override
    public int getItemCount() {
        return branches.size();
    }

    public class BranchViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iconBranch;
        private final TextView tvBranchName;
        private final TextView tvBranchAddress;
        private final TextView tvBranchPhone;
        private final TextView tvBranchEmail;

        public BranchViewHolder(@NonNull View itemView) {
            super(itemView);
            iconBranch = itemView.findViewById(R.id.iconBranch);
            tvBranchName = itemView.findViewById(R.id.tvBranchName);
            tvBranchAddress = itemView.findViewById(R.id.tvBranchAddress);
            tvBranchPhone = itemView.findViewById(R.id.tvBranchPhone);
            tvBranchEmail = itemView.findViewById(R.id.tvBranchEmail);

            itemView.setOnClickListener(v -> {
                try {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        selectedPosition = position;
                        notifyDataSetChanged();
                        if (listener != null) {
                            listener.onBranchSelected(branches.get(position));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        public void bind(BranchModel branch, int position) {
            iconBranch.setImageResource(R.drawable.kopi_icon);
            tvBranchName.setText(branch.getBranchName());
            tvBranchAddress.setText(branch.getBranchAddress());
            tvBranchPhone.setText("SĐT: " + branch.getBranchPhone());
            tvBranchEmail.setText("Email: " + branch.getBranchEmail());

            // Highlight selected item if needed
            itemView.setSelected(position == selectedPosition);
        }
    }

    public void setBranches(List<BranchModel> branches) {
        this.branches.clear();
        if (branches != null) {
            this.branches.addAll(branches);
        }
        notifyDataSetChanged();
    }

    public interface OnBranchSelectedListener {
        void onBranchSelected(BranchModel branch);
    }
}