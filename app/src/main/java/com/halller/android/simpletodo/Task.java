package com.halller.android.simpletodo;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {

    private static final String TAG = "ToDoItem";
    private String mTaskDetails;
    private long mTimeAddedMillis;

    public Task(String taskDetails) {
        mTaskDetails = taskDetails;
        mTimeAddedMillis = System.currentTimeMillis();
    }

    public Task(String taskDetails, long timeAddedMillis) {
        mTaskDetails = taskDetails;
        mTimeAddedMillis = timeAddedMillis;
    }

    public String getTaskDetails() {
        return mTaskDetails;
    }

    public void setTaskDetails(String listItem) {
        mTaskDetails = listItem;
    }

    public long getTimeAddedMillis() {
        return mTimeAddedMillis;
    }

    public void setTimeAddedMillis(long timeAddedMillis) {
        mTimeAddedMillis = timeAddedMillis;
    }
}
