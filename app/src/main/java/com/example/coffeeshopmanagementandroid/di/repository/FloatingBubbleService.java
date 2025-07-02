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
import android.util.DisplayMetrics;
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

import org.commonmark.renderer.text.TextContentRenderer;

import java.util.ArrayList;
import java.util.List;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;


public class FloatingBubbleService extends Service {
    private WindowManager windowManager;
    private View bubbleView;
    private View chatView;
    private WindowManager.LayoutParams bubbleParams;
    private WindowManager.LayoutParams chatParams;
    private boolean isChatOpen = false;

    private List<MessageModel> messageList; // For API conversation history
    private List<MessageModel> messageUIList; // For UI display (plain text)
    private MessageAdapter messageAdapter;
    private RecyclerView messagesRecyclerView;
    private EditText messageEditText;
    private MaterialButton sendButton;
    private View closeAreaView;
    private WindowManager.LayoutParams closeAreaParams;
    private boolean isDragging = false;
    private boolean isLoading = false;

    // Screen dimensions for boundary checking
    private int screenWidth;
    private int screenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        // Initialize message list for chat
        messageList = new ArrayList<>();
        messageUIList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageUIList);

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

            // Use TYPE_APPLICATION_OVERLAY for Android 8.0+, TYPE_PHONE for older versions
            int layoutFlag;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
            }

            bubbleParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    layoutFlag,
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
                    try {
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
                                // Calculate new position
                                int newX = initialX + (int) (event.getRawX() - initialTouchX);
                                int newY = initialY + (int) (event.getRawY() - initialTouchY);

                                // Apply boundaries to keep bubble on screen
                                newX = Math.max(0, Math.min(newX, screenWidth - bubbleView.getWidth()));
                                newY = Math.max(0, Math.min(newY, screenHeight - bubbleView.getHeight()));

                                bubbleParams.x = newX;
                                bubbleParams.y = newY;

                                // Safely update view layout
                                if (bubbleView != null && bubbleView.getWindowToken() != null) {
                                    windowManager.updateViewLayout(bubbleView, bubbleParams);
                                }

                                // Show close area if dragging for more than 100ms
                                if (!isDragging && System.currentTimeMillis() - touchStartTime > 100) {
                                    isDragging = true;
                                    showCloseArea();
                                }
                                return true;

                            case MotionEvent.ACTION_UP:
                                // Handle click (short touch)
                                long touchDuration = System.currentTimeMillis() - touchStartTime;
                                float touchDistance = Math.abs(event.getRawX() - initialTouchX) +
                                        Math.abs(event.getRawY() - initialTouchY);

                                if (touchDuration < 200 && touchDistance < 20) {
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
                    } catch (Exception e) {
                        Log.e("FloatingBubbleService", "Error in touch handling", e);
                        hideCloseArea();
                        isDragging = false;
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
        try {
            Context contextThemeWrapper = new ContextThemeWrapper(
                    this,
                    R.style.Theme_CoffeeShopManagementAndroid);

            // Use the themed context for inflation
            LayoutInflater inflater = LayoutInflater.from(contextThemeWrapper);
            chatView = inflater.inflate(R.layout.activity_chat, null);

            // Layout params for chat view
            int layoutFlag;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
            }

            chatParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    layoutFlag,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);

            // Set gravity to fill the entire screen
            chatParams.gravity = Gravity.CENTER;

            // Optional: Add background to make it look more like a fullscreen view
            chatView.setBackgroundColor(getResources().getColor(android.R.color.white));

            // Rest of your existing code...
            messagesRecyclerView = chatView.findViewById(R.id.messagesRecyclerView);
            messageEditText = chatView.findViewById(R.id.messageEditText);
            sendButton = chatView.findViewById(R.id.sendButton);

            // Add content description for accessibility
            if (sendButton != null) {
                sendButton.setContentDescription("Send message");
            }

            // Set up recycler view
            if (messagesRecyclerView != null) {
                messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                messagesRecyclerView.setAdapter(messageAdapter);
            }

            // Add welcome message
            addMessage("assistant", "Xin chào! Tôi là trợ lý ảo BCoffee. Tôi có thể giúp gì cho bạn?", false);

            // Set up send button
            if (sendButton != null) {
                sendButton.setOnClickListener(v -> {
                    String message = messageEditText.getText().toString().trim();
                    if (!message.isEmpty()) {
                        sendMessage(message);
                    }
                });
            }

            // Add close button
            View headerLayout = chatView.findViewById(R.id.headerLayout);
            if (headerLayout instanceof ViewGroup) {
                ImageView closeButton = new ImageView(this);
                closeButton.setImageResource(R.drawable.close_icon);
                closeButton.setContentDescription("Close chat");
                closeButton.setOnClickListener(v -> toggleChatView());
                ((ViewGroup) headerLayout).addView(closeButton);
            }
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error creating chat view", e);
        }
    }

    @SuppressLint("InflateParams")
    private void createCloseAreaView() {
        try {
            closeAreaView = LayoutInflater.from(this).inflate(R.layout.close_area_layout, null);

            int layoutFlag;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
            }

            closeAreaParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    layoutFlag,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);

            closeAreaParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            closeAreaParams.y = 100;  // Distance from bottom
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error creating close area view", e);
        }
    }

    private void showCloseArea() {
        try {
            if (closeAreaView != null && closeAreaView.getWindowToken() == null) {
                windowManager.addView(closeAreaView, closeAreaParams);
            }
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error showing close area", e);
        }
    }

    private void hideCloseArea() {
        try {
            if (closeAreaView != null && closeAreaView.getWindowToken() != null) {
                windowManager.removeView(closeAreaView);
            }
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error hiding close area", e);
        }
    }

    private boolean isOverCloseArea(int x, int y) {
        try {
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
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error checking close area", e);
            return false;
        }
    }

    private void toggleChatView() {
        try {
            if (!isChatOpen) {
                // Store current position of bubble
                int previousX = bubbleParams.x;
                int previousY = bubbleParams.y;

                // Move bubble to top-right corner
                bubbleParams.gravity = Gravity.TOP | Gravity.END;
                bubbleParams.x = 16; // Small margin from right
                bubbleParams.y = 80; // Small margin from top

                if (bubbleView != null && bubbleView.getWindowToken() != null) {
                    windowManager.updateViewLayout(bubbleView, bubbleParams);
                }

                // Configure chat view to appear below the bubble
                chatParams.gravity = Gravity.TOP | Gravity.END;
                chatParams.x = 0;
                chatParams.y = bubbleParams.y + 150; // Position below bubble
                chatParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                chatParams.height = WindowManager.LayoutParams.MATCH_PARENT;

                // Show chat view
                if (chatView != null) {
                    windowManager.addView(chatView, chatParams);
                    isChatOpen = true;

                    // Store position to restore later
                    bubbleView.setTag(R.id.bubble_previous_x, previousX);
                    bubbleView.setTag(R.id.bubble_previous_y, previousY);
                }
            } else {
                // Close chat view
                if (chatView != null && chatView.getWindowToken() != null) {
                    windowManager.removeView(chatView);
                }

                // Restore previous position if available
                Integer prevX = (Integer) bubbleView.getTag(R.id.bubble_previous_x);
                Integer prevY = (Integer) bubbleView.getTag(R.id.bubble_previous_y);

                if (prevX != null && prevY != null) {
                    bubbleParams.gravity = Gravity.TOP | Gravity.START;
                    bubbleParams.x = prevX;
                    bubbleParams.y = prevY;

                    if (bubbleView != null && bubbleView.getWindowToken() != null) {
                        windowManager.updateViewLayout(bubbleView, bubbleParams);
                    }
                }

                isChatOpen = false;
            }
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error toggling chat view", e);
        }
    }

    private void sendMessage(String messageText) {
        try {
            addMessage("user", messageText, true);
            if (messageEditText != null) {
                messageEditText.setText("");
            }

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
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error sending message", e);
            setLoading(false);
        }
    }

    private void setLoading(boolean loading) {
        isLoading = loading;

        if (sendButton != null && messageEditText != null) {
            new Handler(getMainLooper()).post(() -> {
                sendButton.setEnabled(!loading);
                messageEditText.setEnabled(!loading);
            });
        }
    }

    public String convertMarkdownToPlainText(String markdown) {
        try {
            Parser parser = Parser.builder().build();
            Node document = parser.parse(markdown);
            TextContentRenderer renderer = TextContentRenderer.builder().build();
            return renderer.render(document).trim();
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error converting markdown", e);
            return markdown; // Return original text if conversion fails
        }
    }

    private void addMessage(String role, String message, boolean isSent) {
        try {
            // Add to messageList for API (raw markdown)
            messageList.add(new MessageModel(role, message, isSent));

            // Convert markdown to plain text for UI
            String plainTextMessage = convertMarkdownToPlainText(message);
            messageUIList.add(new MessageModel(role, plainTextMessage, isSent));

            // Update RecyclerView with messageUIList
            if (messageAdapter != null) {
                messageAdapter.notifyItemInserted(messageUIList.size() - 1);
            }

            if (messagesRecyclerView != null) {
                messagesRecyclerView.smoothScrollToPosition(messageUIList.size() - 1);
            }
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error adding message", e);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (bubbleView != null && bubbleView.getWindowToken() != null) {
                windowManager.removeView(bubbleView);
            }

            if (chatView != null && chatView.getWindowToken() != null) {
                windowManager.removeView(chatView);
            }

            if (closeAreaView != null && closeAreaView.getWindowToken() != null) {
                windowManager.removeView(closeAreaView);
            }
        } catch (Exception e) {
            Log.e("FloatingBubbleService", "Error in onDestroy", e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}