package com.example.foodmanagmentsqlite.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodmanagmentsqlite.models.Food;
import com.example.foodmanagmentsqlite.repository.FoodRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodViewModel extends AndroidViewModel {
    private FoodRepository foodRepository;
    private MutableLiveData<List<Food>> allFoods;
    private MutableLiveData<Food> selectedFood;
    private ExecutorService executorService;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        foodRepository = new FoodRepository(application);
        allFoods = new MutableLiveData<>();
        selectedFood = new MutableLiveData<>();
        executorService = Executors.newSingleThreadExecutor();
        refreshData();
    }

    public LiveData<List<Food>> getAllFoods() {
        return allFoods;
    }

    public LiveData<Food> getFoodById(int id) {
        loadFoodById(id);
        return selectedFood;
    }

    public void insert(Food food) {
        executorService.execute(() -> {
            foodRepository.insert(food);
            refreshData();
        });
    }

    public void update(Food food) {
        executorService.execute(() -> {
            foodRepository.update(food);
            refreshData();
        });
    }

    public void delete(Food food) {
        executorService.execute(() -> {
            foodRepository.delete(food);
            refreshData();
        });
    }

    private void loadFoodById(int id) {
        executorService.execute(() -> {
            Food fetchedFood = foodRepository.getFoodById(id);
            selectedFood.postValue(fetchedFood);
        });
    }

    public void refreshData() {
        executorService.execute(() -> {
            List<Food> foods = foodRepository.getAllFoods();
            allFoods.postValue(foods);
        });
    }
}
