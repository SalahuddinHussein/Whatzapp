package com.smiledwatermelon.whatzapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    mEmailView=findViewById(R.id.login_email);
    mPasswordView=findViewById(R.id.login_password);

    mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == R.integer.login || actionId == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        }
    });



    }

    private void attemptLogin() {


    }

    public void signInExistingUser(View view) {


    }

    public void registerNewUser(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);


    }
}
