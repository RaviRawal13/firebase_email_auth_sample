package com.fobbles.firebaseemailauthsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Ravi on 1/9/2018.
 */

public class HomeActivity extends AppCompatActivity {

    TextView tvEmail;
    TextView tvStatus;
    Button btSignOut;
    ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent() != null && getIntent().getExtras() != null) {
            String email = getIntent().getExtras().getString("Email");
            boolean status = getIntent().getExtras().getBoolean("Status");
            tvEmail.setText(email);
            tvStatus.setText(String.format("Login status: %s", status));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        tvEmail = findViewById(R.id.tv_email);
        tvStatus = findViewById(R.id.tv_status);
        progressBar = findViewById(R.id.progressBar);
        btSignOut = findViewById(R.id.bt_sign_out);

        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(HomeActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
