package com.example.bookauthormanagement.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookauthormanagement.dao.BookDao;
import com.example.bookauthormanagement.models.Book;

import java.util.List;

public class BookViewModel extends AndroidViewModel {
    private BookDao bookDao;
    private MutableLiveData<List<Book>> booksLiveData;

    public BookViewModel(@NonNull Application application) {
        super(application);
        bookDao = new BookDao(application);
        booksLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Book>> getBooks() {
        return booksLiveData;
    }

    public void loadBooks() {
        List<Book> books = bookDao.getAllBooks();
        booksLiveData.setValue(books);
    }

    public void loadBooksByAuthorId(int authorId) {
        List<Book> books = bookDao.getBooksByAuthorId(authorId);
        booksLiveData.setValue(books);
    }

    public void insertBook(Book book) {
        bookDao.insertBook(book);
        loadBooks();
    }

    public void updateBook(Book book) {
        bookDao.updateBook(book);
        loadBooks();
    }

    public void deleteBook(int bookId) {
        bookDao.deleteBook(bookId);
        loadBooks();
    }
}
