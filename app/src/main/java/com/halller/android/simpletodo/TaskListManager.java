package com.halller.android.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import com.halller.android.simpletodo.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * Singleton class used to manage a to do list.
 * Can add to the list and retrieve data from the list
 */
public class TaskListManager {

    private static final String TAG = "ListManager";
    private List<Task> mTaskList;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public TaskListManager(Context context) {
        mTaskList = new ArrayList<>();
        getTaskList();
        mDbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public void addTask(Task task) {
        mTaskList.add(task);
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase.insert(DatabaseHelper.TABLE_NAME, null, getContentValues(task));
        mDatabase.close();
    }

    public void removeTask(Task item) {
        mTaskList.remove(item);
    }

    public List<Task> getList() {
        return mTaskList;
    }

    public Task getTask(UUID taskId) {
        for (Task task : mTaskList) {
            if (taskId.equals(task.getId())) {
                return task;
            }
        }

        return null;
    }

    public int getSize() {
        return mTaskList.size();
    }

    public void getTaskList(){
        TaskCursorWrapper cursor = queryTaskList(null, null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                mTaskList.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
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
