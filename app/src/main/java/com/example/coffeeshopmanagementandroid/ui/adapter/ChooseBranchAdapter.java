package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.branch.BranchModel;

import java.util.List;

public class ChooseBranchAdapter extends RecyclerView.Adapter<ChooseBranchAdapter.ChooseBranchViewHolder> {
    private final List<BranchModel> branchList;
    private int selectedPosition = 0;
    private final OnBranchSelectedListener listener;

    public interface OnBranchSelectedListener {
        void onBranchSelected(BranchModel branch);
    }

    public ChooseBranchAdapter(List<BranchModel> branchList, OnBranchSelectedListener listener) {
        this.branchList = branchList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChooseBranchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.branch_item_layout, parent, false);
        return new ChooseBranchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseBranchViewHolder holder, int position) {
        BranchModel branch = branchList.get(position);
        holder.tvBranchName.setText(branch.getBranchName());
        holder.tvBranchAddress.setText(branch.getBranchAddress());
        holder.rbBranch.setChecked(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onBranchSelected(branchList.get(selectedPosition));
            }
        });

        holder.rbBranch.setOnClickListener(v -> {
            int previousPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
            if (listener != null) {
                listener.onBranchSelected(branchList.get(selectedPosition));
            }
        });
    }

    public BranchModel getSelectedBranch() {
        if (branchList != null && !branchList.isEmpty() && selectedPosition >= 0 && selectedPosition < branchList.size()) {
            return branchList.get(selectedPosition);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return branchList != null ? branchList.size() : 0;
    }

    public void updateList(List<BranchModel> newBranchList) {
        branchList.clear();
        branchList.addAll(newBranchList);
        selectedPosition = 0;
        notifyDataSetChanged();
    }

    public static class ChooseBranchViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBranchName;
        private final TextView tvBranchAddress;
        private final RadioButton rbBranch;

        public ChooseBranchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBranchName = itemView.findViewById(R.id.tvBranchName);
            tvBranchAddress = itemView.findViewById(R.id.tvBranchAddress);
            rbBranch = itemView.findViewById(R.id.rbBranch);
        }
    }
}