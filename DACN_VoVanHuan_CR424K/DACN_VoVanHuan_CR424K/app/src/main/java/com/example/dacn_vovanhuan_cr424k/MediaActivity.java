package com.example.dacn_vovanhuan_cr424k;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MediaActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 2;
    private static final int REQUEST_AUDIO_RECORD = 3;
    ImageButton cameraButton,videoButton,audioButton,btn_back5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        // Xử lý nút button back lại từ Media sang CALLMESS
        btn_back5 = findViewById(R.id.btn_back5);
        btn_back5.setOnClickListener(view -> {
            Intent intent = new Intent(MediaActivity.this, CALLMESS.class);
            startActivity(intent);
        });
        // Nút mở camera
        cameraButton = findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(v -> openCamera());

        // Nút quay video
        videoButton = findViewById(R.id.videoButton);
        videoButton.setOnClickListener(v -> recordVideo());

        // Nút ghi âm
        audioButton = findViewById(R.id.audioButton);
        audioButton.setOnClickListener(v -> recordAudio());
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Camera không khả dụng!", Toast.LENGTH_SHORT).show();
        }
    }

    private void recordVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        } else {
            Toast.makeText(this, "Video không khả dụng!", Toast.LENGTH_SHORT).show();
        }
    }

    private void recordAudio() {
        Intent recordAudioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        if (recordAudioIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(recordAudioIntent, REQUEST_AUDIO_RECORD);
        } else {
            Toast.makeText(this, "Ghi âm không khả dụng!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri mediaUri = data.getData();
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Toast.makeText(this, "Ảnh đã được chụp: " + mediaUri, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_VIDEO_CAPTURE:
                    Toast.makeText(this, "Video đã được quay: " + mediaUri, Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_AUDIO_RECORD:
                    Toast.makeText(this, "Âm thanh đã được ghi: " + mediaUri, Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Toast.makeText(this, "Hủy thao tác!", Toast.LENGTH_SHORT).show();
        }
    }
}
