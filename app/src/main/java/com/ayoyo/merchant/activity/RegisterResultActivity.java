package com.ayoyo.merchant.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ayoyo.merchant.R;

public class RegisterResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_result);

        findViewById(R.id.btnKembali).setOnClickListener(v -> {
            Intent intent = new Intent(RegisterResultActivity.this, IntroActivity2.class);
            startActivity(intent);
        });
    }
}
