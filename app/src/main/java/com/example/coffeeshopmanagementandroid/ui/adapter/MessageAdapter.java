package com.example.coffeeshopmanagementandroid.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.message.MessageModel;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final List<MessageModel> messages;

    public MessageAdapter(List<MessageModel> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_layout, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        MessageModel message = messages.get(position);
        if (message.isSent()) {
            holder.sentLayout.setVisibility(View.VISIBLE);
            holder.receivedLayout.setVisibility(View.GONE);
            holder.sentTextView.setText(message.getContent());
        } else {
            holder.receivedLayout.setVisibility(View.VISIBLE);
            holder.sentLayout.setVisibility(View.GONE);
            holder.receivedTextView.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout sentLayout, receivedLayout;
        TextView sentTextView, receivedTextView;

        MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            sentLayout = itemView.findViewById(R.id.sent_layout);
            receivedLayout = itemView.findViewById(R.id.received_layout);
            sentTextView = itemView.findViewById(R.id.tv_sent);
            receivedTextView = itemView.findViewById(R.id.tv_received);
        }
    }
}