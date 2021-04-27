package com.ayoyo.merchant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ayoyo.merchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity2 extends AppCompatActivity {
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnLogin)
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);
        ButterKnife.bind(this);

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity2.this, RegisterActivity2.class);
            startActivity(intent);

        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity2.this, OtpActivity.class);
            startActivity(intent);

        });

    }
}