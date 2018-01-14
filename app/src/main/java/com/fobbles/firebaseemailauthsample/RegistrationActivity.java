package com.fobbles.firebaseemailauthsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btLogin;
    Button btRegister;
    ProgressBar progressBar;
    private FirebaseAuth auth;

    private static final int minPasswordLength = 4;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        progressBar.setVisibility(View.GONE);
        userFound();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btLogin = findViewById(R.id.bt_login);
        btRegister = findViewById(R.id.bt_register);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progressBar);
//        btnResetPassword = findViewById(R.id.btn_reset_password);
//
//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(RegistrationActivity.this, ResetPasswordActivity.class));
//            }
//        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < minPasswordLength) {
                    Toast.makeText(getApplicationContext(), "Password length short", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "User sign up unsuccessful." + task.getResult(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "User sign up successful." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    userFound();

                                }
                            }
                        })
                        .addOnFailureListener(RegistrationActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception ignored) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "User sign up failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    void userFound() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
            intent.putExtra("Email", currentUser.getEmail());
            intent.putExtra("Status", !currentUser.isAnonymous());

            startActivity(intent);
            finish();
        }
    }
}
