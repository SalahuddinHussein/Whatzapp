package com.smiledwatermelon.whatzapp;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    static final String CHAT_PREFS = "ChatPrefs";
    static final String DISPLAY_NAME_KEY = "username";


    private AutoCompleteTextView mRegisterdUserName,mRegisterdEmail;
    private EditText mPassword, mConfirmPassword;
    private Button mRegisterSignUp;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRegisterdUserName=findViewById(R.id.register_username);
        mRegisterdEmail=findViewById(R.id.register_email);
        mPassword=findViewById(R.id.register_password);
        mConfirmPassword=findViewById(R.id.register_confirm_password);
        mRegisterSignUp=findViewById(R.id.register_sign_up_button);



        mConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==R.integer.register_form_finished || actionId== EditorInfo.IME_NULL){
                    attemptRegistration();
                    return true;
                }

                return false;
            }

        });
        mAuth=FirebaseAuth.getInstance();

    }



    public void signUp(View view) {
        attemptRegistration();

    }

    private void attemptRegistration() {

        //reset errors displayed on the form

        mRegisterdEmail.setError(null);
        mPassword.setError(null);


        // Store values at the time of the login attempt.
        String email = mRegisterdEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mRegisterdEmail.setError(getString(R.string.error_field_required));
            focusView = mRegisterdEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mRegisterdEmail.setError(getString(R.string.error_invalid_email));
            focusView = mRegisterdEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();
        }



    }


    private  boolean isEmailValid(String email){
        return email.contains("@");

    }

    private boolean isPasswordValid(String password){

        String cnfrmdPassw =mConfirmPassword.getText().toString();
        return (cnfrmdPassw.equals(password) && password.length()>4);


    }

    private void createFirebaseUser(){

        String email,password;
        email = mRegisterdEmail.getText().toString();
        password=mPassword.getText().toString();

        Log.d("whatz",""+email+password);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("whatz", "createUserWithEmail:success");
                            saveDisplayName();
                            Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                            finish();
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("whatz", "createUserWithEmail:failure"+task.getException());
                            showErrorDialog(task.getException().toString());
                        }

                        // ...
                    }
                });




    }

    private void saveDisplayName(){
        String displayName=mRegisterdUserName.getText().toString();

        SharedPreferences prefs=getSharedPreferences(CHAT_PREFS,0);
        prefs.edit().putString(DISPLAY_NAME_KEY,displayName).apply();


    }

    private void showErrorDialog(String message){

        message=message.substring(message.indexOf("[")+1);
        message=message.substring(0,message.indexOf("]"));

        new AlertDialog.Builder(RegisterActivity.this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();


    }

}
