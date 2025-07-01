package com.example.coffeeshopmanagementandroid.di.repository;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeeshopmanagementandroid.R;
import com.example.coffeeshopmanagementandroid.data.dto.rag.request.QueryRequest;
import com.example.coffeeshopmanagementandroid.data.dto.rag.response.RagResponse;
import com.example.coffeeshopmanagementandroid.data.mapper.RagMapper;
import com.example.coffeeshopmanagementandroid.domain.model.message.MessageModel;
import com.example.coffeeshopmanagementandroid.ui.adapter.MessageAdapter;
import com.example.coffeeshopmanagementandroid.utils.RagServiceManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FloatingBubbleService extends Service {
    private WindowManager windowManager;
    private View bubbleView;
    private View chatView;
    private WindowManager.LayoutParams bubbleParams;
    private WindowManager.LayoutParams chatParams;
    private boolean isChatOpen = false;

    private List<MessageModel> messageList;
    private MessageAdapter messageAdapter;
    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private MaterialButton sendButton;
    private View closeAreaView;
    private WindowManager.LayoutParams closeAreaParams;
    private boolean isDragging = false;
    private boolean isLoading = false;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Initialize message list for chat
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);

        RagServiceManager.getInstance(getApplicationContext());

        try {
            createBubbleView();
            createChatView();
            createCloseAreaView();
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error creating views", e);
            stopSelf();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createBubbleView() {
        try {
            bubbleView = LayoutInflater.from(this).inflate(R.layout.bubble_layout, null);
            ImageView bubbleImage = bubbleView.findViewById(R.id.bubble_image);

            bubbleParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            bubbleParams.gravity = Gravity.TOP | Gravity.START;
            bubbleParams.x = 0;
            bubbleParams.y = 100;

            windowManager.addView(bubbleView, bubbleParams);

            bubbleImage.setOnTouchListener(new View.OnTouchListener() {
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;
                private long touchStartTime;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = bubbleParams.x;
                            initialY = bubbleParams.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            touchStartTime = System.currentTimeMillis();
                            isDragging = false;
                            return true;

                        case MotionEvent.ACTION_MOVE:
                            bubbleParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                            bubbleParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(bubbleView, bubbleParams);

                            // Show close area if dragging for more than 100ms
                            if (!isDragging && System.currentTimeMillis() - touchStartTime > 100) {
                                isDragging = true;
                                showCloseArea();
                            }
                            return true;

                        case MotionEvent.ACTION_UP:
                            // Handle click (short touch)
                            if (System.currentTimeMillis() - touchStartTime < 200 &&
                                    Math.abs(event.getRawX() - initialTouchX) < 10 &&
                                    Math.abs(event.getRawY() - initialTouchY) < 10) {
                                toggleChatView();
                            }

                            // Check if dropped over close area
                            if (isDragging && isOverCloseArea((int)event.getRawX(), (int)event.getRawY())) {
                                hideCloseArea();
                                stopSelf();  // Stop the service
                                return true;
                            }

                            hideCloseArea();
                            isDragging = false;
                            return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error creating bubble view", e);
            throw e;
        }
    }

    private void createChatView() {
        Context contextThemeWrapper = new ContextThemeWrapper(
                this,
                R.style.Theme_CoffeeShopManagementAndroid);

        // Use the themed context for inflation
        LayoutInflater inflater = LayoutInflater.from(contextThemeWrapper);
        chatView = inflater.inflate(R.layout.activity_chat, null);

        // Change WRAP_CONTENT to MATCH_PARENT for height
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            chatParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
        }

        // Set gravity to fill the entire screen
        chatParams.gravity = Gravity.CENTER;

        // Optional: Add background to make it look more like a fullscreen view
        chatView.setBackgroundColor(getResources().getColor(android.R.color.white));

        // Rest of your existing code...
        messagesRecyclerView = chatView.findViewById(R.id.messagesRecyclerView);
        messageEditText = chatView.findViewById(R.id.messageEditText);
        sendButton = chatView.findViewById(R.id.sendButton);

            // Add content description for accessibility
            sendButton.setContentDescription("Send message");

            // Set up recycler view
            messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            messagesRecyclerView.setAdapter(messageAdapter);

            // Add welcome message
            addMessage("assistant ", "Xin chào! Tôi là trợ lý ảo BCoffee. Tôi có thể giúp gì cho bạn?", false);

            // Set up send button
            sendButton.setOnClickListener(v -> {
                String message = messageEditText.getText().toString().trim();
                if (!message.isEmpty()) {
                    sendMessage(message);
                }
            });

            // Add close button
            View headerLayout = chatView.findViewById(R.id.headerLayout);
            if (headerLayout instanceof ViewGroup) {
                ImageView closeButton = new ImageView(this);
                closeButton.setImageResource(R.drawable.close_icon);
                closeButton.setContentDescription("Close chat");
                closeButton.setOnClickListener(v -> toggleChatView());
                ((ViewGroup) headerLayout).addView(closeButton);
            }
    }

    @SuppressLint("InflateParams")
    private void createCloseAreaView() {
        closeAreaView = LayoutInflater.from(this).inflate(R.layout.close_area_layout, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            closeAreaParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }

        closeAreaParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        closeAreaParams.y = 100;  // Distance from bottom
    }

    private void showCloseArea() {
        if (closeAreaView != null && closeAreaView.getWindowToken() == null) {
            windowManager.addView(closeAreaView, closeAreaParams);
        }
    }

    private void hideCloseArea() {
        if (closeAreaView != null && closeAreaView.getWindowToken() != null) {
            windowManager.removeView(closeAreaView);
        }
    }

    private boolean isOverCloseArea(int x, int y) {
        if (closeAreaView == null || closeAreaView.getWindowToken() == null) {
            return false;
        }

        int[] closeAreaLocation = new int[2];
        closeAreaView.getLocationOnScreen(closeAreaLocation);

        int closeAreaLeft = closeAreaLocation[0];
        int closeAreaTop = closeAreaLocation[1];
        int closeAreaRight = closeAreaLeft + closeAreaView.getWidth();
        int closeAreaBottom = closeAreaTop + closeAreaView.getHeight();

        return x >= closeAreaLeft && x <= closeAreaRight && y >= closeAreaTop && y <= closeAreaBottom;
    }

    private void toggleChatView() {
        if (!isChatOpen) {
            // Store current position of bubble
            int previousX = bubbleParams.x;
            int previousY = bubbleParams.y;

            // Move bubble to top-right corner
            bubbleParams.gravity = Gravity.TOP | Gravity.END;
            bubbleParams.x = 16; // Small margin from right
            bubbleParams.y = 80; // Small margin from top
            windowManager.updateViewLayout(bubbleView, bubbleParams);

            // Configure chat view to appear below the bubble
            chatParams.gravity = Gravity.TOP | Gravity.END;
            chatParams.x = 0;
            chatParams.y = bubbleParams.y + 150; // Position below bubble
            chatParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            chatParams.height = WindowManager.LayoutParams.MATCH_PARENT;

            // Show chat view
            windowManager.addView(chatView, chatParams);
            isChatOpen = true;

            // Store position to restore later
            bubbleView.setTag(R.id.bubble_previous_x, previousX);
            bubbleView.setTag(R.id.bubble_previous_y, previousY);
        } else {
            // Close chat view
            if (chatView != null) {
                windowManager.removeView(chatView);
            }

            // Restore previous position if available
            Integer prevX = (Integer) bubbleView.getTag(R.id.bubble_previous_x);
            Integer prevY = (Integer) bubbleView.getTag(R.id.bubble_previous_y);

            if (prevX != null && prevY != null) {
                bubbleParams.gravity = Gravity.TOP | Gravity.START;
                bubbleParams.x = prevX;
                bubbleParams.y = prevY;
                windowManager.updateViewLayout(bubbleView, bubbleParams);
            }

            isChatOpen = false;
        }
    }

    private void sendMessage(String messageText) {
        addMessage("user", messageText, true);
        messageEditText.setText("");

        // Show loading indicator
        setLoading(true);

        // Convert message history for RAG API
        List<QueryRequest.ConversationItem> conversationItems =
                RagMapper.mapMessageListToConversationHistory(messageList, 3);

        // Use the RagServiceManager to call the API
        RagServiceManager.getInstance().sendMessage(
                messageText,
                conversationItems,
                new RagServiceManager.RagCallback() {
                    @Override
                    public void onSuccess(String response) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            addMessage("assistant", response, false);
                            setLoading(false);
                        });
                    }

                    @Override
                    public void onError(String errorMessage) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            addMessage("assistant", "Xin lỗi, đã xảy ra lỗi: " + errorMessage, false);
                            setLoading(false);
                        });
                    }
                });
    }

    private void setLoading(boolean loading) {
        isLoading = loading;

        if (sendButton != null) {
            new Handler(getMainLooper()).post(() -> {
                sendButton.setEnabled(!loading);
                messageEditText.setEnabled(!loading);
            });
        }
    }

    private void addMessage(String role, String message, boolean isSent) {
        messageList.add(new MessageModel(role, message, isSent));
        messageAdapter.notifyItemInserted(messageList.size() - 1);
        messagesRecyclerView.smoothScrollToPosition(messageList.size() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bubbleView != null && bubbleView.isAttachedToWindow()) {
            windowManager.removeView(bubbleView);
        }

        if (chatView != null && isChatOpen) {
            windowManager.removeView(chatView);
        }

        if (closeAreaView != null && closeAreaView.getWindowToken() != null) {
            windowManager.removeView(closeAreaView);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}