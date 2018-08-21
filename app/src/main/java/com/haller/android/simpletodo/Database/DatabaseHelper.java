package com.haller.android.simpletodo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private final static String TAG = "Database class";
    private static final String DATABASE_NAME = "tasks.db";

    public static final String TABLE_NAME = "tasklist";
    public static final String COL_UUID = "uuid";
    public static final String COL_DETAILS = "details";
    public static final String COL_MILLIS_ADDED = "date_added";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: called data ");
        db.execSQL("create table " + TABLE_NAME + "( " +
                "_id integer primary key autoincrement, " +
                COL_UUID + ", " +
                COL_DETAILS + " , " +
                COL_MILLIS_ADDED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
