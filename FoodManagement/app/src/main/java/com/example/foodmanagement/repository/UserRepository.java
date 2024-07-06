package com.example.foodmanagement.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.foodmanagement.dao.UserDao;
import com.example.foodmanagement.database.AppDatabase;
import com.example.foodmanagement.models.User;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }

    public LiveData<User> getUser(String username, String password) {
        return userDao.getUser(username, password);
    }
}
