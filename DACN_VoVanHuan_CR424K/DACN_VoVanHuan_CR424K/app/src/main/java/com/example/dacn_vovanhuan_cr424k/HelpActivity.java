package com.example.dacn_vovanhuan_cr424k;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Button helpButton = findViewById(R.id.btn_back);
        helpButton.setOnClickListener(v -> {
            Intent intent = new Intent(HelpActivity.this,WinActivity.class);
            startActivity(intent);
            finish();  // Kết thúc WinActivity
        });
    }
}