package com.example.lab_4a_memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database fields
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memopad.db";
    private static final String TABLE_MEMOS = "memos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";

    // Create Table Statement
    public static final String QUERY_CREATE_MEMOS_TABLE = "CREATE TABLE " + TABLE_MEMOS +
            " (" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TEXT + " text)";

    // Select Queries
    public static final String QUERY_GET_ALL_MEMOS = "SELECT * FROM " + TABLE_MEMOS;
    public static final String QUERY_GET_MEMO = "SELECT * FROM " + TABLE_MEMOS + " WHERE " + COLUMN_ID + " = ?";

    // Delete Queries
    public static final String QUERY_DELETE_MEMOS_TABLE = "DROP TABLE IF EXISTS " + TABLE_MEMOS;
    public static final String QUERY_DELETE_MEMO = COLUMN_ID + " = ?";

    // Constructor
    public DatabaseHandler(Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_MEMOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DELETE_MEMOS_TABLE);
        onCreate(db);
    }

    public void addMemo(Memo newMemo) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, newMemo.getMemoText());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEMOS, null, values);
        db.close();
    }
    public void deleteMemo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMOS, QUERY_DELETE_MEMO, new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteAllMemos() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMOS, null, null);
    }
    public Memo getMemo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_MEMO, new String[]{ String.valueOf(id) });
        Memo memo = null;

        if (cursor.moveToFirst()) {
            int newId = cursor.getInt(0);
            String newText = cursor.getString(1);
            memo = new Memo(newId, newText);
            cursor.close();
        }
        db.close();

        return memo;
    }

    public ArrayList<Memo> getAllMemosAsList() {
        Log.i("Database", "DB handler getAllMemosAsList");
        ArrayList<Memo> MemosList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_ALL_MEMOS, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                MemosList.add( getMemo(id) );
            }
            while ( cursor.moveToNext() );
        }
        cursor.close();
        db.close();

        return MemosList;
    }
}