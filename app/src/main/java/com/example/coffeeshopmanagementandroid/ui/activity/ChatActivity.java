package com.example.coffeeshopmanagementandroid.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.domain.model.message.MessageModel;
import com.example.coffeeshopmanagementandroid.ui.adapter.MessageAdapter;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView messagesRecyclerView;
    private MessageAdapter messageAdapter;
    private List<MessageModel> messageList;
    private EditText messageEditText;
    private MaterialButton sendButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize UI components
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
        messageEditText = findViewById(R.id.messageEditText);
        sendButton = findViewById(R.id.sendButton);

        // Add back button to header
        findViewById(R.id.headerLayout).findViewById(android.R.id.content);
        backButton = new ImageButton(this);
        backButton.setImageResource(R.drawable.back_icon);
        backButton.setBackgroundResource(0);
        backButton.setOnClickListener(v -> finish());

        // Setup RecyclerView
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecyclerView.setAdapter(messageAdapter);

        // Add welcome message
        addMessage("Xin chào! Tôi là trợ lý ảo BCoffee. Tôi có thể giúp gì cho bạn?", false);

        // Set up send button click listener
        sendButton.setOnClickListener(v -> {
            String message = messageEditText.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
            }
        });
    }

    private void sendMessage(String messageText) {
        // Add user message to chat
        addMessage(messageText, true);

        // Clear input field
        messageEditText.setText("");

        // Simulate bot response (in a real app, you'd call your AI service here)
        simulateResponse(messageText);
    }

    private void simulateResponse(String userMessage) {
        // Simple response logic - in a real app, this would be your AI service
        String response;

        if (userMessage.toLowerCase().contains("giờ mở cửa") ||
                userMessage.toLowerCase().contains("thời gian")) {
            response = "BCoffee mở cửa từ 7:00 đến 22:00 hàng ngày.";
        } else if (userMessage.toLowerCase().contains("menu") ||
                userMessage.toLowerCase().contains("đồ uống")) {
            response = "Menu của chúng tôi có nhiều loại cà phê, trà, nước ép và bánh ngọt. Bạn có thể xem chi tiết trong mục Sản phẩm.";
        } else if (userMessage.toLowerCase().contains("khuyến mãi") ||
                userMessage.toLowerCase().contains("ưu đãi")) {
            response = "Hiện tại chúng tôi có chương trình giảm 15% cho đơn hàng đầu tiên và tích điểm đổi quà hấp dẫn.";
        } else if (userMessage.toLowerCase().contains("chi nhánh") ||
                userMessage.toLowerCase().contains("cửa hàng")) {
            response = "BCoffee có nhiều chi nhánh trên toàn quốc. Bạn có thể xem danh sách các chi nhánh trong mục Cửa hàng.";
        } else {
            response = "Xin lỗi, tôi chưa hiểu rõ câu hỏi của bạn. Bạn có thể hỏi về menu, khuyến mãi, giờ mở cửa hoặc chi nhánh của chúng tôi.";
        }

        // Add response after a short delay to simulate processing time
        messagesRecyclerView.postDelayed(() -> addMessage(response, false), 800);
    }

    private void addMessage(String message, boolean isSent) {
        messageList.add(new MessageModel(message, isSent));
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        messagesRecyclerView.smoothScrollToPosition(messageList.size() - 1);
    }
}