package com.example.dennis.medical;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText Email;
    private EditText Password;
    private TextView Signin;
    private TextView forgotpassword;
    private Button LoginBtn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressBar progressBar;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//To do//
                            return;
                        }

// Get the Instance ID token//
                        String token = task.getResult().getToken();
                        String msg = getString(R.string.fcm_token, token);
                        Log.d(TAG, msg);

                    }
                });



        mAuth = FirebaseAuth.getInstance();
        Email = (EditText) findViewById(R.id.LoginEmail);
        Password = (EditText) findViewById(R.id.LoginPassword);
//        LoginBtn = (Button) findViewById(R.id.LoginBtn);
        Signin = (TextView) findViewById(R.id.sign_inoptions);
//        forgotpassword = (TextView) findViewById(R.id.forgotpassword);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        findViewById(R.id.LoginBtn).setOnClickListener(this);
        findViewById(R.id.sign_inoptions).setOnClickListener(this);
        findViewById(R.id.forgotpassword).setOnClickListener(this);


    }

    private void Login() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please enter a valid email");
            Email.requestFocus();
            return;
        }
        if (password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, Dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    //checkemailverfication();
                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkemailverfication(){
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if (emailflag){
            finish();
            startActivity(new Intent(MainActivity.this, Dashboard.class));
        }else{
            Toast.makeText(MainActivity.this, "Verify your Email", Toast.LENGTH_LONG).show();
            mAuth.signOut();
        }
    }



    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.LoginBtn:
                Login();
                break;

            case R.id.forgotpassword:
                startActivity(new Intent(MainActivity.this, forgotpassword.class));
                break;

            case R.id.sign_inoptions:
                startActivity(new Intent(MainActivity.this, Signup.class));
                break;
        }
    }



}