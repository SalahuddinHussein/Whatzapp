package com.smiledwatermelon.whatzapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainChatActivity extends AppCompatActivity {


    private String mDisplayName;
    private EditText mTypedMessage;
    private ImageButton mSendButton;
    private ListView mChatListView;


    private DatabaseReference mDatabaseReference;
    private ChatListAdapter mAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        mTypedMessage=findViewById(R.id.messageInput);
        mSendButton=findViewById(R.id.sendButton);
        mChatListView=findViewById(R.id.chat_list_view);

        mDatabaseReference= FirebaseDatabase.getInstance().getReference();

        setDisplayName();

        mTypedMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter=new ChatListAdapter(this,mDatabaseReference,mDisplayName);
        mChatListView.setAdapter(mAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAdapter.cleanUp();
    }

    private void setDisplayName(){

        SharedPreferences pref=getSharedPreferences(RegisterActivity.CHAT_PREFS,MODE_PRIVATE);
        mDisplayName=pref.getString(RegisterActivity.DISPLAY_NAME_KEY,null);
        if(mDisplayName==null)mDisplayName="Anonymous";
    }

    private void sendMessage(){
        Log.d("whatz","message sent!!!");

        String inputMessage=mTypedMessage.getText().toString();
        if(!inputMessage.equals("")){
            InstantMessage  chat=new InstantMessage(inputMessage,mDisplayName);
            mDatabaseReference.child("messages").push().setValue(chat);
            mTypedMessage.setText("");
        }

    }
}
