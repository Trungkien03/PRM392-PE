package com.example.foodmanagmentsqlite.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food_management.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_USER = "user";
    public static final String TABLE_FOOD = "food";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_STATUS = "status";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT);";

    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_IMAGE_URL + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_STATUS + " INTEGER);";

    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_FOOD);
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Insert initial users
        ContentValues userValues = new ContentValues();
        userValues.put(COLUMN_USERNAME, "user");
        userValues.put(COLUMN_PASSWORD, "123456");
        db.insert(TABLE_USER, null, userValues);

        // Insert initial foods
        insertFood(db, "Pizza", "Delicious cheese pizza", 9.99, "https://www.allrecipes.com/thmb/fFW1o307WSqFFYQ3-QXYVpnFj6E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/48727-Mikes-homemade-pizza-DDMFS-beauty-4x3-BG-2974-a7a9842c14e34ca699f3b7d7143256cf.jpg", 1);
        insertFood(db, "Burger", "Juicy beef burger", 5.99, "https://www.example.com/burger.jpg", 1);
        insertFood(db, "Sushi", "Fresh salmon sushi", 12.99, "https://www.example.com/sushi.jpg", 1);
    }

    private void insertFood(SQLiteDatabase db, String name, String description, double price, String imageUrl, int status) {
        ContentValues foodValues = new ContentValues();
        foodValues.put(COLUMN_NAME, name);
        foodValues.put(COLUMN_IMAGE_URL, imageUrl);
        foodValues.put(COLUMN_DESCRIPTION, description);
        foodValues.put(COLUMN_PRICE, price);
        foodValues.put(COLUMN_STATUS, status);
        db.insert(TABLE_FOOD, null, foodValues);
    }
}
