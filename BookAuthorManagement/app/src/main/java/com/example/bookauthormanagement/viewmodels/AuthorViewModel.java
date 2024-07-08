package com.example.bookauthormanagement.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookauthormanagement.dao.AuthorDao;
import com.example.bookauthormanagement.models.Author;

import java.util.List;

public class AuthorViewModel extends AndroidViewModel {
    private AuthorDao authorDao;
    private MutableLiveData<List<Author>> authorsLiveData;

    public AuthorViewModel(@NonNull Application application) {
        super(application);
        authorDao = new AuthorDao(application);
        authorsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Author>> getAuthors() {
        return authorsLiveData;
    }

    public void loadAuthors() {
        List<Author> authors = authorDao.getAllAuthors();
        authorsLiveData.setValue(authors);
    }

    public void insertAuthor(Author author) {
        authorDao.insertAuthor(author);
        loadAuthors();
    }

    public void updateAuthor(Author author) {
        authorDao.updateAuthor(author);
        loadAuthors();
    }

    public void deleteAuthor(int authorId) {
        authorDao.deleteAuthor(authorId);
        loadAuthors();
    }
}
