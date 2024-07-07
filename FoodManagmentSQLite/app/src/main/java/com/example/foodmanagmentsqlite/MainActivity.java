package com.example.foodmanagmentsqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodmanagmentsqlite.ui.HomeActivity;
import com.example.foodmanagmentsqlite.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Giả sử bạn có phương thức để kiểm tra trạng thái đăng nhập của người dùng
        if (isUserLoggedIn()) {
            // Nếu người dùng đã đăng nhập, điều hướng đến HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            // Nếu người dùng chưa đăng nhập, điều hướng đến LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        finish();
    }

    private boolean isUserLoggedIn() {
        // Kiểm tra trạng thái đăng nhập của người dùng
        // Bạn có thể sử dụng SharedPreferences hoặc một phương thức khác để lưu trạng thái đăng nhập
        // Ví dụ với SharedPreferences:
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}