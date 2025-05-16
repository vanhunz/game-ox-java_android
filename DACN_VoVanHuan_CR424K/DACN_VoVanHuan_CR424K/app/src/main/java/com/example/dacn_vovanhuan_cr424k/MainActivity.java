package com.example.dacn_vovanhuan_cr424k;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = true;
    private int currentPlayer = 1; // 1 - X, 2 - O
    private int[][] gameState = new int[3][3]; // 0 - empty, 1 - X, 2 - O
    private boolean gameActive = true;
    private int moveCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy trạng thái nhạc từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        isMusicPlaying = sharedPreferences.getBoolean("isMusicPlaying", true);

        // Khởi động Service nếu nhạc được bật
        if (isMusicPlaying) {
            startService(new Intent(this, MusicService.class));
        }

        // Cập nhật trạng thái của RadioButton
        RadioButton musicOn = findViewById(R.id.musicOn);
        RadioButton musicOff = findViewById(R.id.musicOff);
        musicOn.setChecked(isMusicPlaying);
        musicOff.setChecked(!isMusicPlaying);

        // Gắn listener cho RadioButton
        musicOn.setOnClickListener(this::onMusicRadioButtonClicked);
        musicOff.setOnClickListener(this::onMusicRadioButtonClicked);
    }
    public void onPlayerRadioButtonClicked(View view) {
        RadioButton radioButton = (RadioButton) view;
        if (radioButton.isChecked()) {
            if (view.getId() == R.id.radioX) {
                // Nếu người chơi chọn X
                currentPlayer = 1; // Người chơi X
                updateStatus("Thằng X đánh");
            } else if (view.getId() == R.id.radioO) {
                // Nếu người chơi chọn O
                currentPlayer = 2; // Người chơi O
                updateStatus("Thằng O đánh");
            }
        }
    }

    public void onMusicRadioButtonClicked(View view) {
        RadioButton radioButton = (RadioButton) view;

        if (radioButton.getId() == R.id.musicOn && !isMusicPlaying) {
            isMusicPlaying = true;
            saveMusicState(true);
            startService(new Intent(this, MusicService.class)); // Bật nhạc
        } else if (radioButton.getId() == R.id.musicOff && isMusicPlaying) {
            isMusicPlaying = false;
            saveMusicState(false);
            stopService(new Intent(this, MusicService.class)); // Tắt nhạc
        }
    }

    private void saveMusicState(boolean state) {
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isMusicPlaying", state);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    // Các hàm liên quan đến trò chơi
    public void buttonClick(View view) {
        if (!gameActive) return;

        Button button = (Button) view;
        String[] tag = button.getTag().toString().split(",");
        int row = Integer.parseInt(tag[0]);
        int col = Integer.parseInt(tag[1]);

        if (gameState[row][col] != 0) return; // Vị trí đã được đánh

        // Đánh dấu nước đi
        gameState[row][col] = currentPlayer;
        moveCount++;
        button.setText(currentPlayer == 1 ? "X" : "O");

        // Kiểm tra thắng/thua/hòa
        if (checkWinner(row, col)) {
            gameActive = false;
            String winner = currentPlayer == 1 ? "X" : "O";

            // Lưu kết quả ván đấu
            saveResult(winner);

            // Hiển thị màn hình chiến thắng
            showWinMessage(winner);
        }
        else if (moveCount >= 9) {
            gameActive = false;
            saveResult("Hòa");
            showWinMessage("Hòa");
        } else {
            // Đổi lượt người chơi
            currentPlayer = currentPlayer == 1 ? 2 : 1;
            updateStatus("Thằng " + (currentPlayer == 1 ? "X" : "O") + " đánh");
        }
    }

    private boolean checkWinner(int row, int col) {
        // Kiểm tra hàng
        if (gameState[row][0] == currentPlayer &&
                gameState[row][1] == currentPlayer &&
                gameState[row][2] == currentPlayer) return true;

        // Kiểm tra cột
        if (gameState[0][col] == currentPlayer &&
                gameState[1][col] == currentPlayer &&
                gameState[2][col] == currentPlayer) return true;

        // Kiểm tra đường chéo
        if (row == col && gameState[0][0] == currentPlayer &&
                gameState[1][1] == currentPlayer &&
                gameState[2][2] == currentPlayer) return true;

        if (row + col == 2 && gameState[0][2] == currentPlayer &&
                gameState[1][1] == currentPlayer &&
                gameState[2][0] == currentPlayer) return true;

        return false;
    }

    private void showWinMessage(String winner) {
        String result = winner.equals("Hòa") ? "Hòa" : (winner + "");

        // Ghi kết quả vào lịch sử
        HistoryActivity.addResultToHistory(result, MainActivity.this);

        // Chuyển sang WinActivity để hiển thị kết quả
        Intent intent = new Intent(MainActivity.this, WinActivity.class);
        intent.putExtra("result", result);
        startActivity(intent);
        finish(); // Kết thúc MainActivity
    }

    public void resetGame(View view) {
        // Lấy trạng thái của RadioButton để xác định người chơi bắt đầu
        RadioButton radioX = findViewById(R.id.radioX);
        RadioButton radioO = findViewById(R.id.radioO);

        if (radioX.isChecked()) {
            currentPlayer = 1; // Người chơi X
            updateStatus("Thằng X đánh");
        } else if (radioO.isChecked()) {
            currentPlayer = 2; // Người chơi O
            updateStatus("Thằng O đánh");
        }

        // Đặt lại trạng thái trò chơi
        gameActive = true;
        moveCount = 0;

        // Reset trạng thái bàn cờ
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameState[i][j] = 0;
                String buttonID = "button" + ((i * 3) + j + 1);
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                if (resID != 0) { // Đảm bảo resID hợp lệ
                    Button button = findViewById(resID);
                    if (button != null) {
                        button.setText("");
                        button.setEnabled(true);
                        button.setTag(i + "," + j);
                    }
                }
            }
        }
    }

    private void updateStatus(String status) {
        TextView statusTextView = findViewById(R.id.status);
        if (statusTextView != null) {
            statusTextView.setText(status);
        }
    }

    // Hàm lưu kết quả vào lịch sử (giả sử bạn đã có class HistoryActivity)
    private void saveResult(String result) {
        // Thêm logic lưu kết quả nếu cần
    }

    public void showHistory(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("latest_result", loadLatestResult());
        startActivity(intent);
    }

    private String loadLatestResult() {
        SharedPreferences sharedPreferences = getSharedPreferences("GameHistory", MODE_PRIVATE);
        return sharedPreferences.getString("latest_result", "Chưa có kết quả.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }


    public void logout(View view) {
        // Xóa trạng thái đăng nhập và ghi nhớ tài khoản
        SharedPreferences sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Xóa tất cả dữ liệu trong SharedPreferences
        editor.apply();

        // Chuyển về LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Kết thúc MainActivity
        finish();
    }
}