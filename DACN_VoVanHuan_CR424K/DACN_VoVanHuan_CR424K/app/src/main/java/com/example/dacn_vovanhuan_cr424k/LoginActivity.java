package com.example.dacn_vovanhuan_cr424k;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME = "admin"; // Tài khoản cứng
    private static final String PASSWORD = "123456"; // Mật khẩu cứng

    private EditText tk, mk;
    private CheckBox luutk;
    private Button dangnhap, btn_quenmk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tk = findViewById(R.id.tk);
        mk = findViewById(R.id.mk);
        luutk = findViewById(R.id.luutk);
        dangnhap = findViewById(R.id.dangnhap);
        btn_quenmk = findViewById(R.id.btn_quenmk);

        // Kiểm tra trạng thái ghi nhớ
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            String savedUsername = sharedPreferences.getString("savedUsername", "");
            String savedPassword = sharedPreferences.getString("savedPassword", "");
            if (USERNAME.equals(savedUsername) && PASSWORD.equals(savedPassword)) {
                navigateToMainActivity();
            }
        }

        // Xử lý sự kiện nút Đăng nhập
        dangnhap.setOnClickListener(view -> {
            String username = tk.getText().toString().trim();
            String password = mk.getText().toString().trim();

            if (username.equals(USERNAME) && password.equals(PASSWORD)) {
                saveLoginState(true, username, password);
                navigateToMainActivity();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện nút Quên Mật Khẩu
        btn_quenmk.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, CALLMESS.class);
            startActivity(intent);
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveLoginState(boolean isLoggedIn, String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isLoggedIn", isLoggedIn);

        if (luutk.isChecked()) {
            editor.putBoolean("rememberMe", true);
            editor.putString("savedUsername", username);
            editor.putString("savedPassword", password);
        } else {
            editor.putBoolean("rememberMe", false);
        }

        editor.apply();
    }
}