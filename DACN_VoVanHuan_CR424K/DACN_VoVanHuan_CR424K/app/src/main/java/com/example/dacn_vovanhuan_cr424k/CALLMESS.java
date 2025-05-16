package com.example.dacn_vovanhuan_cr424k;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CALLMESS extends AppCompatActivity {
    private ImageButton callBtn1, smsBtn, noteBtn;
    private Button btn_url, btn_back1, btn_momedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callmess);

        // Khởi tạo các phần tử giao diện
        callBtn1 = findViewById(R.id.callBtn1);
        smsBtn = findViewById(R.id.smsBtn1);
        noteBtn = findViewById(R.id.noteBtn1);
        btn_url = findViewById(R.id.btn_url);
        btn_back1 = findViewById(R.id.btn_back1);
        btn_momedia = findViewById(R.id.btn_momedia);

        // Xử lý sự kiện cho các nút
        setupCallButton();
        setupSMSButton();
        setupNoteButton();
        setupYouTubeButton();
        setupBackButton();
    }

    private void setupCallButton() {
        callBtn1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0123456789"));
            startActivity(intent);
        });
    }

    private void setupSMSButton() {
        smsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:0123456789"));
            intent.putExtra("sms_body", "QUEN MK");
            startActivity(intent);
        });
    }

    private void setupNoteButton() {
        noteBtn.setOnClickListener(v -> {
            Toast.makeText(this, "BẠN CÓ THỂ ẤN VÀO NÚT CALL ĐỂ LIÊN HỆ CHÚNG TÔI\n" +
                            "NÚT SMS ĐỂ LẤY LẠI MK MỚI\n" +
                            "HOẶC CÓ THỂ VÀO YTB ĐỂ TÌM HIỂU THÊM THÔNG TIN\n" +
                            "NẾU CẦN BẠN VỀ MEDIA THÌ CÓ THỂ MỞ BUTTON MEDIA",
                    Toast.LENGTH_LONG).show();
        });
    }

    private void setupYouTubeButton() {
        btn_url.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com"));
            startActivity(intent);
        });
    }

    private void setupBackButton() {
        btn_back1.setOnClickListener(view -> {
            Intent intent = new Intent(CALLMESS.this, LoginActivity.class);
            startActivity(intent);
        });
        btn_momedia.setOnClickListener(view -> {
            Intent intent = new Intent(CALLMESS.this, MediaActivity.class);
            startActivity(intent);
        });
    }



}
