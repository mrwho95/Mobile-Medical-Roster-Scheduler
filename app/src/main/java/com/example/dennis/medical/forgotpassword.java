package com.example.dennis.medical;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity{
    ProgressBar progressBar;
    private EditText email;
    private Button resetpassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Forgot Password");

        progressBar = findViewById(R.id.progressbar);
        email = (EditText)findViewById(R.id.Email);
        resetpassword = (Button)findViewById(R.id.resetpasswordBtn);
        mAuth = FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail = email.getText().toString().trim();
                if (useremail.equals("")){
                    Toast.makeText(forgotpassword.this, "Please enter your registered email!", Toast.LENGTH_LONG).show();
                }else {
                    mAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(forgotpassword.this, "Password reset email sent!", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(forgotpassword.this, MainActivity.class));
                            }else {
                                Toast.makeText(forgotpassword.this, "Error in sending password reset email!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

}
