package com.example.foodmanagement.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodmanagement.models.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("SELECT * FROM food_table ORDER BY name ASC")
    LiveData<List<Food>> getAllFoods();

    @Query("SELECT * FROM food_table WHERE id = :id LIMIT 1")
    Food getFoodById(int id);
}
