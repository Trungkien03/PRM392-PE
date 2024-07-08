package com.example.bookauthormanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookauthormanagement.adapters.BookAdapter;
import com.example.bookauthormanagement.models.Author;
import com.example.bookauthormanagement.models.Book;
import com.example.bookauthormanagement.viewmodels.AuthorViewModel;
import com.example.bookauthormanagement.viewmodels.BookViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AuthorViewModel authorViewModel;
    private BookViewModel bookViewModel;
    private Spinner spinnerAuthors;
    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerAuthors = findViewById(R.id.spinnerAuthors);
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks);
        findViewById(R.id.button_add_book).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddBookActivity.class));
        });
        findViewById(R.id.button_add_author).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddAuthorActivity.class));
        });

        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(null, new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                // Handle book item click
            }

            @Override
            public void onEditClick(Book book) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                intent.putExtra("bookId", book.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Book book) {
                bookViewModel.deleteBook(book.getId());
                Toast.makeText(MainActivity.this, "Book deleted", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewBooks.setAdapter(bookAdapter);

        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        authorViewModel.getAuthors().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(List<Author> authors) {
                ArrayAdapter<Author> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, authors);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAuthors.setAdapter(adapter);
            }
        });

        bookViewModel.getBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                bookAdapter.setBooks(books);
            }
        });

        spinnerAuthors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Author selectedAuthor = (Author) parent.getSelectedItem();
                bookViewModel.loadBooksByAuthorId(selectedAuthor.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bookViewModel.loadBooks();
            }
        });

        authorViewModel.loadAuthors();
        bookViewModel.loadBooks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        authorViewModel.loadAuthors();
        bookViewModel.loadBooks();
    }
}
