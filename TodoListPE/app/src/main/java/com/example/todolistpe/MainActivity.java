package com.example.todolistpe;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolistpe.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTitle, editContent, editDate, editType;
    Button btnAdd, btnUpdate, btnDelete;
    ListView listView;
    SubjectAdapter adapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editTitle = findViewById(R.id.titleEditText);
        editContent = findViewById(R.id.contentEditText);
        editDate = findViewById(R.id.dateEditText);
        editType = findViewById(R.id.typeEditText);
        btnAdd = findViewById(R.id.addButton);
        btnUpdate = findViewById(R.id.updateButton);
        btnDelete = findViewById(R.id.deleteButton);
        listView = findViewById(R.id.listView);

        cursor = myDb.getAllData();
        adapter = new SubjectAdapter(this, cursor, 0);
        listView.setAdapter(adapter);

        addData();
        updateData();
        deleteData();
        viewDetails();
    }

    public void addData() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editTitle.getText().toString(), editContent.getText().toString(), editDate.getText().toString(), editType.getText().toString());
                if (isInserted)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                cursor = myDb.getAllData();
                adapter.changeCursor(cursor);
                clearFields();
            }
        });
    }

    public void updateData() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(
                        cursor.getString(cursor.getColumnIndex("_id")),
                        editTitle.getText().toString(),
                        editContent.getText().toString(),
                        editDate.getText().toString(),
                        editType.getText().toString()
                );
                if (isUpdate)
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();

                cursor = myDb.getAllData();
                adapter.changeCursor(cursor);
                clearFields();
            }
        });
    }

    public void deleteData() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                if (deletedRows > 0)
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

                cursor = myDb.getAllData();
                adapter.changeCursor(cursor);
                clearFields();
            }
        });
    }

    public void viewDetails() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                editTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_2)));
                editContent.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_3)));
                editDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_4)));
                editType.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_5)));
            }
        });
    }

    private void clearFields() {
        editTitle.setText("");
        editContent.setText("");
        editDate.setText("");
        editType.setText("");
    }
}
