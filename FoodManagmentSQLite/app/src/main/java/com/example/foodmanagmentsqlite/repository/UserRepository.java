package com.example.foodmanagmentsqlite.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodmanagmentsqlite.database.AppDatabaseHelper;
import com.example.foodmanagmentsqlite.models.User;

public class UserRepository {
    private SQLiteDatabase database;
    private AppDatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new AppDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public User getUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(AppDatabaseHelper.TABLE_USER, null,
                AppDatabaseHelper.COLUMN_USERNAME + " = ? AND " + AppDatabaseHelper.COLUMN_PASSWORD + " = ?",
                new String[]{username, password}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                User user = new User(
                        cursor.getString(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_PASSWORD))
                );
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(AppDatabaseHelper.COLUMN_ID)));
                cursor.close();
                return user;
            }
            cursor.close();
        }
        return null;
    }

    public void insert(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppDatabaseHelper.COLUMN_USERNAME, user.getUsername());
        values.put(AppDatabaseHelper.COLUMN_PASSWORD, user.getPassword());
        db.insert(AppDatabaseHelper.TABLE_USER, null, values);
    }

    public void update(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AppDatabaseHelper.COLUMN_USERNAME, user.getUsername());
        values.put(AppDatabaseHelper.COLUMN_PASSWORD, user.getPassword());
        db.update(AppDatabaseHelper.TABLE_USER, values, AppDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    public void delete(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(AppDatabaseHelper.TABLE_USER, AppDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }
}
