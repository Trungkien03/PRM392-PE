package com.example.todolistpe;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.todolistpe.database.DatabaseHelper;

public class SubjectAdapter extends CursorAdapter {

    public SubjectAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView titleTextView = view.findViewById(R.id.textViewTitle);
        TextView contentTextView = view.findViewById(R.id.textViewContent);
        TextView dateTextView = view.findViewById(R.id.textViewDate);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_2));
        String content = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_3));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_4));

        titleTextView.setText(title);
        contentTextView.setText(content);
        dateTextView.setText(date);
    }
}
