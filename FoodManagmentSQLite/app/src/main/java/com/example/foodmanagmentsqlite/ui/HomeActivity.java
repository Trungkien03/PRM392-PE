package com.example.foodmanagmentsqlite.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmanagmentsqlite.R;
import com.example.foodmanagmentsqlite.ui.adapters.FoodAdapter;
import com.example.foodmanagmentsqlite.viewmodels.FoodViewModel;

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
        observeViewModel();

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
            // Clear login state from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Navigate to Login Activity
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void observeViewModel() {
        foodViewModel.getAllFoods().observe(this, foods -> adapter.setFoods(foods));
    }

    @Override
    protected void onResume() {
        super.onResume();
        foodViewModel.refreshData();
    }
}
