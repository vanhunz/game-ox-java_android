package com.example.dacn_vovanhuan_cr424k;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_win1); // Sử dụng layout win1

        TextView winMessage = findViewById(R.id.winMessage);

        // Hiển thị kết quả ván đấu
        String winner = getIntent().getStringExtra("result");

        if (winner != null) {
            winMessage.setText(winner.equals("Hòa") ? "Hòa!" : winner + " thắng!");
        } else {
            winMessage.setText("Kết quả không xác định!");
        }

        // Button quay lại MainActivity
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(WinActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Kết thúc WinActivity
        });
        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(v -> {
            Intent intent = new Intent(WinActivity.this,HelpActivity.class);
            startActivity(intent);
            finish();  // Kết thúc WinActivity
        });
    }
}
