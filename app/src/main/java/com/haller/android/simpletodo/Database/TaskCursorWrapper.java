package com.haller.android.simpletodo.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.haller.android.simpletodo.Utilities.Task;

import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {

    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String uuidString = getString(getColumnIndex(DatabaseHelper.COL_UUID));
        String taskDetails = getString(getColumnIndex(DatabaseHelper.COL_DETAILS));
        long timeAddedMillis = getLong(getColumnIndex(DatabaseHelper.COL_MILLIS_ADDED));
        String dateDue = getString(getColumnIndex(DatabaseHelper.COL_DUE_DATE));

        return new Task(taskDetails, UUID.fromString(uuidString), timeAddedMillis, dateDue);
    }
}
