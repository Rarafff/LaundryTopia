package com.example.laundryuas.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.laundryuas.Activity.MainActivity;
import com.example.laundryuas.Helper.User;
import com.example.laundryuas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView, nameTextView;
    private Button Btn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // initialising all views through id defined above
        nameTextView = findViewById(R.id.etUsername);
        emailTextView = findViewById(R.id.etEmail);
        passwordTextView = findViewById(R.id.etPassword);
        Btn = findViewById(R.id.btnSignup);

        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
    }

    private void registerNewUser()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //String uid = user.getUid();


        // Take the value of two edit texts in Strings
        String name,email, password;

        name = nameTextView.getText().toString().trim();
        email = emailTextView.getText().toString().trim();
        password = passwordTextView.getText().toString().trim();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if(password.length()< 6 ){
            Toast.makeText(getApplicationContext(),
                            "Min password length should be 6 characters!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            User user = new User(name, email);

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().
                                    getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),
                                                        "Registration successful!",
                                                        Toast.LENGTH_LONG)
                                                .show();
                                    } else {

                                        // Registration failed
                                        Toast.makeText(
                                                        getApplicationContext(),
                                                        "Registration failed!!"
                                                                + " Please try again later",
                                                        Toast.LENGTH_LONG)
                                                .show();


                                    }
                                }
                            });


                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(RegisterActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
}