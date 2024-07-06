package com.example.foodmanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodmanagement.R;
import com.example.foodmanagement.models.Food;
import com.example.foodmanagement.viewmodels.FoodViewModel;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class AddEditFoodActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText imageUrlEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private SwitchMaterial statusSwitch;
    private Button saveButton;
    private Button deleteButton;
    private ImageView imageView;
    private FoodViewModel foodViewModel;
    private int foodId = -1;
    private Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_food);

        titleEditText = findViewById(R.id.title);
        imageUrlEditText = findViewById(R.id.image_url);
        descriptionEditText = findViewById(R.id.description);
        priceEditText = findViewById(R.id.price);
        statusSwitch = findViewById(R.id.status);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);
        imageView = findViewById(R.id.image_view);

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra("food_id")) {
            setTitle("Edit Food");
            foodId = intent.getIntExtra("food_id", -1);
            // Lấy dữ liệu Food từ ViewModel và cập nhật UI
            foodViewModel.getFoodById(foodId).observe(this, new Observer<Food>() {
                @Override
                public void onChanged(Food food) {
                    if (food != null) {
                        currentFood = food;
                        titleEditText.setText(food.getName());
                        imageUrlEditText.setText(food.getImageUrl());
                        descriptionEditText.setText(food.getDescription());
                        priceEditText.setText(String.valueOf(food.getPrice()));
                        statusSwitch.setChecked(food.isAvailable());
                        loadImage(food.getImageUrl());
                    }
                }
            });
        } else {
            setTitle("Add Food");
            deleteButton.setVisibility(View.GONE); // Hide delete button when adding a new food
        }

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String imageUrl = imageUrlEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            double price = Double.parseDouble(priceEditText.getText().toString());
            boolean status = statusSwitch.isChecked();

            if (title.isEmpty() || description.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (foodId == -1) {
                Food food = new Food(title, imageUrl, description, price, status);
                foodViewModel.insert(food);
            } else {
                Food food = new Food(title, imageUrl, description, price, status);
                food.setId(foodId);
                foodViewModel.update(food);
            }
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            if (currentFood != null) {
                showDeleteConfirmationDialog();
            }
        });

        imageUrlEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Load image based on new URL
                loadImage(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    private void loadImage(String url) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_food_placeholder)
                .error(R.drawable.ic_food_placeholder)
                .into(imageView);
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this food item?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    foodViewModel.delete(currentFood);
                    finish();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
