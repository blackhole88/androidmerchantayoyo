package com.ayoyo.merchant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ayoyo.merchant.R;

public class OtpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        ImageView btnNext = findViewById(R.id.btnNext);
        EditText etPhone = findViewById(R.id.editTextPhone);
        EditText etPhone2 = findViewById(R.id.editTextPhone2);

        etPhone2.requestFocus();
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(OtpActivity.this, OtpSmsActivity2.class);
            intent.putExtra("phone",  etPhone.getText().toString()+etPhone2.getText().toString());
            startActivity(intent);
        });


    }
}