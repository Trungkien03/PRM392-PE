package com.example.foodmanagement.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodmanagement.models.Food;
import com.example.foodmanagement.repository.FoodRepository;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {
    private FoodRepository repository;
    private LiveData<List<Food>> allFoods;
    private MutableLiveData<Food> food;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        allFoods = repository.getAllFoods();
        food = new MutableLiveData<>();
    }

    public LiveData<List<Food>> getAllFoods() {
        return allFoods;
    }

    public LiveData<Food> getFoodById(int id) {
        loadFoodById(id);
        return food;
    }

    private void loadFoodById(int id) {
        // Use a background thread to fetch data
        repository.executorService.execute(() -> {
            Food fetchedFood = repository.getFoodById(id);
            food.postValue(fetchedFood);
        });
    }

    public void insert(Food food) {
        repository.insert(food);
    }

    public void update(Food food) {
        repository.update(food);
    }

    public void delete(Food food) {
        repository.executorService.execute(() -> {
            repository.delete(food);
        });
    }
}
