package com.example.foodmanagement.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmanagement.R;
import com.example.foodmanagement.models.Food;
import com.example.foodmanagement.ui.adapter.FoodAdapter;
import com.example.foodmanagement.viewmodels.FoodViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private FoodViewModel foodViewModel;
    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private Button addButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycler_view);
        addButton = findViewById(R.id.add_food_button);
        logoutButton = findViewById(R.id.logout_button);

        adapter = new FoodAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        foodViewModel.getAllFoods().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                adapter.setFoods(foods);
            }
        });

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AddEditFoodActivity.class);
            startActivity(intent);
        });

        adapter.setOnItemClickListener(food -> {
            Intent intent = new Intent(HomeActivity.this, AddEditFoodActivity.class);
            intent.putExtra("food_id", food.getId());
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            // Xóa trạng thái đăng nhập khỏi SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Điều hướng về màn hình đăng nhập
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
