package com.example.foodmanagement.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.foodmanagement.models.User;
import com.example.foodmanagement.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;

    public UserViewModel(Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public LiveData<User> getUser(String username, String password) {
        return repository.getUser(username, password);
    }
}
