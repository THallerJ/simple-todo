package com.halller.android.simpletodo;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.ContactsContract;

import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask(){
        String taskDetails = getString(getColumnIndex(DatabaseHelper.COL_DETAILS));

        Task task = new Task(taskDetails);
        return task;
    }

}
