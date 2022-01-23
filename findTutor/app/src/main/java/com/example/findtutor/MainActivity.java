package com.example.findtutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findtutor.login.LoginActivity;
import com.example.findtutor.tutor.RegisterTutorActivity;
import com.example.findtutor.user.RegisterUserActivity;

public class MainActivity extends AppCompatActivity {
    private Button loginTutorButton;
    private Button registerTutorButton;
    private Button loginUserButton;
    private Button registerUserButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        loginTutorButton = findViewById(R.id.loginTutorButton);
        registerTutorButton = findViewById(R.id.registerTutorButton);
        loginUserButton = findViewById(R.id.loginUserButton);
        registerUserButton = findViewById(R.id.registerUserButton);

        loginTutorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("loginType", "tutor");
            startActivity(intent);
        });

        registerTutorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterTutorActivity.class);
            startActivity(intent);
        });

        loginUserButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.putExtra("loginType", "user");
            startActivity(intent);
        });

        registerUserButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterUserActivity.class);
            startActivity(intent);
        });
    }
}
