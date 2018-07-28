package com.halller.android.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * Singleton class used to manage a to do list.
 * Can add to the list and retrieve data from the list
 */
public class TaskListManager {

    private static final String TAG = "ListManager";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public TaskListManager(Context context) {
        mDbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public void addTask(Task task) {
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.insert(DatabaseHelper.TABLE_NAME, null, getContentValues(task));
        mDatabase.close();
    }

    public void removeTask(Task item) {
        getList().remove(item);
    }

    public List<Task> getList() {
        List<Task> taskList = new ArrayList<>();
        TaskCursorWrapper cursor = queryTaskList(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                taskList.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return taskList;
    }

    public Task getTask(UUID taskId) {
        for (Task task : getList()) {
            if (taskId.equals(task.getId())) {
                return task;
            }
        }

        return null;
    }

    public int getSize() {
        return getList().size();
    }

    private ContentValues getContentValues(Task task){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_DETAILS, task.getTaskDetails());

        return values;
    }

    private TaskCursorWrapper queryTaskList(String where, String[] whereArgs){
        mDatabase = mDbHelper.getWritableDatabase();
        Cursor cursor = mDatabase.query(
                DatabaseHelper.TABLE_NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null
        );

        return new TaskCursorWrapper(cursor);
    }
}
