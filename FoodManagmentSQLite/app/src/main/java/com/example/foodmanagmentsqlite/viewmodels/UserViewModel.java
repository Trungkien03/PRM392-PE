package com.example.foodmanagmentsqlite.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodmanagmentsqlite.models.User;
import com.example.foodmanagmentsqlite.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private MutableLiveData<User> user;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        user = new MutableLiveData<>();
    }

    public LiveData<User> getUser(String username, String password) {
        loadUser(username, password);
        return user;
    }

    private void loadUser(String username, String password) {
        new Thread(() -> {
            User fetchedUser = repository.getUser(username, password);
            user.postValue(fetchedUser);
        }).start();
    }

    public void insert(User user) {
        new Thread(() -> repository.insert(user)).start();
    }

    public void update(User user) {
        new Thread(() -> repository.update(user)).start();
    }

    public void delete(User user) {
        new Thread(() -> repository.delete(user)).start();
    }
}
