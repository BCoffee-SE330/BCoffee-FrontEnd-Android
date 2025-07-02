//package com.example.coffeeshopmanagementandroid.ui.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.coffeeshopmanagementandroid.R;
//import com.example.coffeeshopmanagementandroid.data.mapper.RagMapper;
//import com.example.coffeeshopmanagementandroid.domain.model.message.MessageModel;
//import com.example.coffeeshopmanagementandroid.ui.adapter.MessageAdapter;
//import com.example.coffeeshopmanagementandroid.ui.viewmodel.RagViewModel;
//import com.google.android.material.button.MaterialButton;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import dagger.hilt.android.AndroidEntryPoint;
//
//@AndroidEntryPoint
//public class ChatActivity extends AppCompatActivity {
//
////    private RecyclerView messagesRecyclerView;
////    private MessageAdapter messageAdapter;
////    private List<MessageModel> messageList;
////    private EditText messageEditText;
////    private MaterialButton sendButton;
//////    private ProgressBar progressBar;
////    private RagViewModel ragViewModel;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_chat);
////
////        // Initialize UI components
////        messagesRecyclerView = findViewById(R.id.messagesRecyclerView);
////        messageEditText = findViewById(R.id.messageEditText);
////        sendButton = findViewById(R.id.sendButton);
////
////        // Add progress bar
//////        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);
//////        progressBar = findViewById(R.id.progressBar);
//////        if (progressBar == null) {
//////            // If progressBar doesn't exist in layout, we can create it programmatically
//////            progressBar = new ProgressBar(this);
//////            progressBar.setVisibility(View.GONE);
////            // Add it to the layout - this would need proper layout params
////        //}
////
////        // Initialize ViewModel
////        ragViewModel = new ViewModelProvider(this).get(RagViewModel.class);
////
////        // Setup RecyclerView
////        messageList = new ArrayList<>();
////        messageAdapter = new MessageAdapter(messageList);
////        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
////        messagesRecyclerView.setAdapter(messageAdapter);
////
////        // Add welcome message
////        addMessage(new MessageModel("assistant", "Xin chào! Tôi là trợ lý ảo BCoffee. Tôi có thể giúp gì cho bạn?", false));
////
////        // Set up observers
////        setupObservers();
////
////        // Set up send button click listener
////        sendButton.setOnClickListener(v -> {
////            String message = messageEditText.getText().toString().trim();
////            if (!message.isEmpty()) {
////                sendMessage(message);
////            }
////        });
////    }
////
////    private void setupObservers() {
////        // Observe messages
////        ragViewModel.getMessagesLiveData().observe(this, messages -> {
////            if (messages != null && !messages.isEmpty()) {
////                messageList.clear();
////                messageList.addAll(messages);
////                messageAdapter.notifyDataSetChanged();
////                messagesRecyclerView.smoothScrollToPosition(messageList.size() - 1);
////            }
////        });
////
////        // Observe loading state
////        ragViewModel.getIsLoading().observe(this, isLoading -> {
////            sendButton.setEnabled(!isLoading);
////            messageEditText.setEnabled(!isLoading);
////           // progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
////        });
////    }
////
////    private void sendMessage(String messageText) {
////        MessageModel userMessage = new MessageModel("user", messageText, true);
////        addMessage(userMessage);
////        messageEditText.setText("");
////        ragViewModel.sendMessage(messageText);
////    }
////
////    private void addMessage(MessageModel message) {
////        messageList.add(message);
////        messageAdapter.notifyItemInserted(messageList.size() - 1);
////        messagesRecyclerView.smoothScrollToPosition(messageList.size() - 1);
////        ragViewModel.setMessagesLiveData(new ArrayList<>(messageList));
////    }
//}