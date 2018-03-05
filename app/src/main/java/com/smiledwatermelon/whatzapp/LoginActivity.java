package com.smiledwatermelon.whatzapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {


    // TODO: Add member variables here:
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;



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

    mAuth=FirebaseAuth.getInstance();


    }


//TODO Complete the attempLogin method
    private void attemptLogin() {

        String email=mEmailView.getText().toString();
        String password=mPasswordView.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("whatz", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent=new Intent(LoginActivity.this,MainChatActivity.class);
                            finish();
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("whatz", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            showErrorDialog();
                        }

                        // ...
                    }
                });


    }
    //TODO Complete sign in Method

    public void signInExistingUser(View view) {
attemptLogin();

    }

    public void registerNewUser(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        finish();
        startActivity(intent);


    }

    private void showErrorDialog(){

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Oops")
                .setMessage("Wrong Credentials, please try again! ")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }
}
