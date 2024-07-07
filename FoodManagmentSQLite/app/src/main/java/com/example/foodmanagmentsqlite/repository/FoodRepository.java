package com.example.foodmanagmentsqlite.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodmanagmentsqlite.database.AppDatabaseHelper;
import com.example.foodmanagmentsqlite.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodRepository {
    private SQLiteDatabase database;
    private AppDatabaseHelper dbHelper;

    public FoodRepository(Context context) {
        dbHelper = new AppDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public List<Food> getAllFoods() {
        List<Food> foods = new ArrayList<>();
        Cursor cursor = database.query(AppDatabaseHelper.TABLE_FOOD, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Food food = new Food(
                        cursor.getString(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_IMAGE_URL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_PRICE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_STATUS)) == 1
                );
                food.setId(cursor.getInt(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_ID)));
                foods.add(food);
            }
            cursor.close();
        }
        return foods;
    }

    public Food getFoodById(int id) {
        Cursor cursor = database.query(AppDatabaseHelper.TABLE_FOOD, null,
                AppDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Food food = new Food(
                    cursor.getString(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_IMAGE_URL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_PRICE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_STATUS)) == 1
            );
            food.setId(cursor.getInt(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_ID)));
            cursor.close();
            return food;
        }
        return null;
    }

    public void insert(Food food) {
        ContentValues values = new ContentValues();
        values.put(AppDatabaseHelper.COLUMN_NAME, food.getName());
        values.put(AppDatabaseHelper.COLUMN_IMAGE_URL, food.getImageUrl());
        values.put(AppDatabaseHelper.COLUMN_DESCRIPTION, food.getDescription());
        values.put(AppDatabaseHelper.COLUMN_PRICE, food.getPrice());
        values.put(AppDatabaseHelper.COLUMN_STATUS, food.isAvailable() ? 1 : 0);

        database.insert(AppDatabaseHelper.TABLE_FOOD, null, values);
    }

    public void update(Food food) {
        ContentValues values = new ContentValues();
        values.put(AppDatabaseHelper.COLUMN_NAME, food.getName());
        values.put(AppDatabaseHelper.COLUMN_IMAGE_URL, food.getImageUrl());
        values.put(AppDatabaseHelper.COLUMN_DESCRIPTION, food.getDescription());
        values.put(AppDatabaseHelper.COLUMN_PRICE, food.getPrice());
        values.put(AppDatabaseHelper.COLUMN_STATUS, food.isAvailable() ? 1 : 0);

        database.update(AppDatabaseHelper.TABLE_FOOD, values,
                AppDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(food.getId())});
    }

    public void delete(Food food) {
        database.delete(AppDatabaseHelper.TABLE_FOOD,
                AppDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(food.getId())});
    }
}
