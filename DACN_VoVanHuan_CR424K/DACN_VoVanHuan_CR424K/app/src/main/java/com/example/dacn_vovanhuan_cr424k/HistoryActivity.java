package com.example.dacn_vovanhuan_cr424k;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "GameHistoryPrefs";
    private static final String HISTORY_KEY = "history";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView historyTextView = findViewById(R.id.historyTextView);
        Button backButton = findViewById(R.id.backButton);
        Button clearHistoryButton = findViewById(R.id.clearHistoryButton);

        // Lấy lịch sử kết quả từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String history = sharedPreferences.getString(HISTORY_KEY, "");

        // Hiển thị kết quả lịch sử trong TextView
        historyTextView.setText(history.isEmpty() ? "Chưa có kết quả nào." : history);

        // Quay lại màn hình chính
        backButton.setOnClickListener(v -> {
            finish();  // Kết thúc màn hình hiện tại
            startActivity(new Intent(HistoryActivity.this, MainActivity.class));  // Quay lại MainActivity
        });

        // Trong HistoryActivity.java
        clearHistoryButton.setOnClickListener(v -> {
            // Xóa lịch sử và đặt lại số ván
            HistoryActivity.clearHistory(HistoryActivity.this);

            // Cập nhật lại giao diện
            historyTextView.setText("Chưa có kết quả nào.");
        });
    }

    public static void addResultToHistory(String result, android.content.Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Lấy số ván hiện tại từ SharedPreferences, nếu chưa có thì bắt đầu từ 1
        int matchNumber = sharedPreferences.getInt("matchNumber", 1);  // Lấy số ván, mặc định là 1

        // Tạo kết quả mới theo định dạng "Ván x: kết quả"
        String newResult = "Ván " + matchNumber + ": " + result +" Thắng";

        // Lấy lịch sử hiện tại
        String currentHistory = sharedPreferences.getString(HISTORY_KEY, "");

        // Thêm kết quả mới vào lịch sử và lưu lại
        editor.putString(HISTORY_KEY, currentHistory + (currentHistory.isEmpty() ? "" : "\n") + newResult);

        // Cập nhật số ván đấu cho lần tiếp theo
        editor.putInt("matchNumber", matchNumber + 1);  // Tăng số ván lên 1

        // Lưu lại thay đổi
        editor.apply();
    }

    // Phương thức này xóa lịch sử và đặt lại số ván
    public static void clearHistory(android.content.Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Xóa lịch sử kết quả
        editor.putString(HISTORY_KEY, "");

        // Đặt lại số ván đấu về 1
        editor.putInt("matchNumber", 1);

        // Lưu thay đổi
        editor.apply();
    }

}
