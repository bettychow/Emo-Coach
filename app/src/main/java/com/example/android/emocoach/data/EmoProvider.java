package com.example.android.emocoach.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class EmoProvider extends ContentProvider {

    public static final String LOG_TAG = EmoProvider.class.getSimpleName();

    private EmoDbHelper mDbHelper;

    private static final int EMOS = 100;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(EmoContract.CONTENT_AUTHORITY, EmoContract.PATH_EMOS, EMOS);
    }

    @Override
    public boolean onCreate() {

        mDbHelper = new EmoDbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case EMOS:
                cursor = database.query(
                        EmoContract.EmoEntry.TABLE_EMOS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case EMOS:
                return insertEmo(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    public Uri insertEmo(Uri uri, ContentValues values) {
        String emo = values.getAsString(EmoContract.EmoEntry.COLUMN_EMO_TYPE);
        if(emo == null) {
            throw new IllegalArgumentException("Please select an emotion");
        }

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        // TODO: Insert a new pet into the pets database table with the given ContentValues

        long id = database.insert(EmoContract.EmoEntry.TABLE_EMOS, null, values);

        if(id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the pet content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
