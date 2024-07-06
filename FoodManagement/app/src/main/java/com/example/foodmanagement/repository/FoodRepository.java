package com.example.foodmanagement.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foodmanagement.dao.FoodDao;
import com.example.foodmanagement.dao.UserDao;
import com.example.foodmanagement.database.AppDatabase;
import com.example.foodmanagement.models.Food;
import com.example.foodmanagement.models.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FoodRepository {
    private UserDao userDao;
    private FoodDao foodDao;
    private LiveData<List<Food>> allFoods;
    public ExecutorService executorService;

    public FoodRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
        foodDao = db.foodDao();
        allFoods = foodDao.getAllFoods();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Food>> getAllFoods() {
        return allFoods;
    }

    public void insert(Food food) {
        executorService.execute(() -> foodDao.insert(food));
    }

    public void update(Food food) {
        executorService.execute(() -> foodDao.update(food));
    }

    public void delete(Food food) {
        executorService.execute(() -> foodDao.delete(food));
    }

    public Food getFoodById(int id) {
        return foodDao.getFoodById(id);
    }

}
