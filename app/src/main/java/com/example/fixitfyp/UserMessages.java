package com.example.fixitfyp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixitfyp.Model.Messages;
import com.example.fixitfyp.ViewHolder.MessagesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class UserMessages extends AppCompatActivity {

    //This Activity was created by me with the help of a YouTube Tutorial - https://youtu.be/745ElNRjJew
    //The tutorial was Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe
    private EditText editTextMessage;
    private TextView txtMessageName;
    private DatabaseReference MessagesRef;
    private RecyclerView recyclerView;
    private String tradeID = "";
    private String tradeName = "";
    private Button btnSave, btnClose;
    private CardView cdMessage;
    String uid;
    FirebaseUser user;

    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        tradeID = getIntent().getStringExtra("tradeId");
        tradeName = getIntent().getStringExtra("tradeName");
        MessagesRef = FirebaseDatabase.getInstance().getReference().child("Trades").child(tradeID).child("Messages").child(uid);

        editTextMessage = (EditText) findViewById(R.id.editTextLeaveMessage);
        cdMessage = (CardView) findViewById(R.id.cardviewMessage);
        btnSave = (Button) findViewById(R.id.btnSaveMessage);
        txtMessageName = (TextView) findViewById(R.id.txtMessageName);
        btnClose = (Button) findViewById(R.id.btnClose);

        recyclerView = findViewById(R.id.recycler_messages);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        txtMessageName.setText("Message to " + tradeName);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMessage();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void saveMessage() {
        String userId = uid;
        String saveCurrentTime, saveCurrentDate;
        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd, MM, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForDate.getTime());
        //Creates a random key for the bookingId
        UUID messageId = UUID.randomUUID();
        DatabaseReference tradeMessageRef = FirebaseDatabase.getInstance().getReference("Trades").child(tradeID).child("Messages");

        HashMap<String, Object> userMessagesMap = new HashMap<>();
        userMessagesMap.put("messageId", messageId.toString());
        userMessagesMap.put("tradeId", tradeID);
        userMessagesMap.put("date", saveCurrentDate);
        userMessagesMap.put("time", saveCurrentTime);
        userMessagesMap.put("userId", userId);
        userMessagesMap.put("message", editTextMessage.getText().toString());

        tradeMessageRef.child(userId).child(messageId.toString()).updateChildren(userMessagesMap);

        DatabaseReference tradeUserMessageRef = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("Messages");

        HashMap<String, Object> userTradeMessagesMap = new HashMap<>();
        userTradeMessagesMap.put("messageId", messageId.toString());
        userTradeMessagesMap.put("tradeId", tradeID);
        userTradeMessagesMap.put("date", saveCurrentDate);
        userTradeMessagesMap.put("time", saveCurrentTime);
        userTradeMessagesMap.put("userId", userId);
        userTradeMessagesMap.put("message", editTextMessage.getText().toString());


        tradeUserMessageRef.child(tradeID).child(messageId.toString()).updateChildren(userTradeMessagesMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    editTextMessage.getText().clear();

                } else {
                    //An error handling message as our function is working properly
                    Toast.makeText(UserMessages.this, "Message not sent, please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //This method is adapted from the above mentioned video
    //(https://youtu.be/745ElNRjJew) Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe
    //START OF YOUTUBE CODE
    @Override
    protected void onStart() {
        super.onStart();
        //This recycler view is a firebase recycler view, it is used to pull the data from my database.
        //The code uses my database reference to pull data from my "Trades" database path
        FirebaseRecyclerOptions<Messages> options =
                new FirebaseRecyclerOptions.Builder<Messages>()
                        .setQuery(MessagesRef, Messages.class)
                        .build();
        FirebaseRecyclerAdapter<Messages, MessagesViewHolder> adapter =
                new FirebaseRecyclerAdapter<Messages, MessagesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MessagesViewHolder messagesViewHolder, int i, @NonNull Messages messages) {
                        messagesViewHolder.txtMessage.setText(messages.getMessage());
                    }

                    @NonNull
                    @Override
                    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        //inflates my cardview containing database data
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_message_layout, parent, false);
                        MessagesViewHolder holder = new MessagesViewHolder(view);
                        return holder;


                    }
                };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        //END OF YOUTUBE CODE
    }

}