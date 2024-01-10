package com.example.firebasemymetro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasemymetro.R;
import com.example.firebasemymetro.UserManager.UserManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView skipBtn = findViewById(R.id.skipBtn);
        UserManager userManager = new UserManager(SplashActivity.this);
        skipBtn.setText(userManager.getUserName());
        if(skipBtn.getText()!= ""){
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        Button loginBtn = findViewById(R.id.loginBtn);
        TextView signupBtn = findViewById(R.id.signUpNavText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}