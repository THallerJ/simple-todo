package com.haller.android.simpletodo.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haller.android.simpletodo.Database.DatabaseHelper;
import com.haller.android.simpletodo.Database.TaskCursorWrapper;

import java.util.ArrayList;
import java.util.List;

public class TaskListManager {

    private static final String TAG = "TaskListManager";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public TaskListManager(Context context) {
        mDbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public void addTask(Task task) {
        mDatabase = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_UUID, task.getUUID().toString());
        values.put(DatabaseHelper.COL_DETAILS, task.getTaskDetails());
        values.put(DatabaseHelper.COL_MILLIS_ADDED, task.getTimeAddedMillis());
        mDatabase.insert(DatabaseHelper.TABLE_NAME, null, values);
        mDatabase.close();
    }

    public void removeTask(Task task) {
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.execSQL("delete from " + DatabaseHelper.TABLE_NAME + " where " +
                DatabaseHelper.COL_DETAILS + "=\"" + task.getTaskDetails() + "\";");
        mDatabase.close();
    }

    public void updateTaskDetails(Task task) {
        mDatabase = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_DETAILS, task.getTaskDetails());
        mDatabase.update(DatabaseHelper.TABLE_NAME, values,
                DatabaseHelper.COL_UUID + " = ?", new String[]{task.getUUID().toString()});
    }

    public void updateDueDate(Task task) {
        mDatabase = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_DUE_DATE, task.getDueDate());
        mDatabase.update(DatabaseHelper.TABLE_NAME, values,
                DatabaseHelper.COL_UUID + " = ?", new String[]{task.getUUID().toString()});
    }

    // Query the database and use the data to populate a Task list
    public List<Task> getList() {
        List<Task> taskList = new ArrayList<>();
        TaskCursorWrapper cursor = queryTaskList(null, null,
                DatabaseHelper.COL_MILLIS_ADDED);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                taskList.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return taskList;
    }

    private TaskCursorWrapper queryTaskList(String where, String[] whereArgs, String orderBy) {
        mDatabase = mDbHelper.getWritableDatabase();
        Cursor cursor = mDatabase.query(
                DatabaseHelper.TABLE_NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                orderBy
        );

        return new TaskCursorWrapper(cursor);
    }
}
