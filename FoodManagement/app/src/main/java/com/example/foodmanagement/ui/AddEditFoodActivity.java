package com.example.foodmanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodmanagement.R;
import com.example.foodmanagement.models.Food;
import com.example.foodmanagement.viewmodels.FoodViewModel;

public class AddEditFoodActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText imageUrlEditText;
    private EditText descriptionEditText;
    private EditText priceEditText;
    private Switch statusSwitch;
    private Button saveButton;
    private FoodViewModel foodViewModel;
    private int foodId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_food);

        titleEditText = findViewById(R.id.title);
        imageUrlEditText = findViewById(R.id.image_url);
        descriptionEditText = findViewById(R.id.description);
        priceEditText = findViewById(R.id.price);
        statusSwitch = findViewById(R.id.status);
        saveButton = findViewById(R.id.save_button);

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
                        titleEditText.setText(food.getName());
                        imageUrlEditText.setText(food.getImageUrl());
                        descriptionEditText.setText(food.getDescription());
                        priceEditText.setText(String.valueOf(food.getPrice()));
                        statusSwitch.setChecked(food.isAvailable());
                    }
                }
            });
        } else {
            setTitle("Add Food");
        }

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String imageUrl = imageUrlEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            double price;
            try {
                price = Double.parseDouble(priceEditText.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean status = statusSwitch.isChecked();

            if (title.isEmpty() || description.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Food food = new Food(title, imageUrl, description, price, status);
            if (foodId != -1) {
                food.setId(foodId);
                foodViewModel.update(food);
            } else {
                foodViewModel.insert(food);
            }
            finish();
        });
    }
}
