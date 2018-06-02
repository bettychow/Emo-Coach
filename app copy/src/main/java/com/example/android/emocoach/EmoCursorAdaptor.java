package com.example.android.emocoach;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

public class EmoCursorAdaptor extends CursorAdapter {

    public EmoCursorAdaptor(Context context, Cursor cursor) {
        super(context, cursor, 0);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
