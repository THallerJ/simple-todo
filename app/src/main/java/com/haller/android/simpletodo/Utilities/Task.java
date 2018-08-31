package com.haller.android.simpletodo.Utilities;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Task implements Serializable {

    private static final String TAG = "ToDoItem";
    private UUID mUUID;
    private String mTaskDetails;
    private long mTimeAddedMillis;
    private String mDueDateString;

    public Task(String taskDetails) {
        mUUID = UUID.randomUUID();
        mTaskDetails = taskDetails;
        mTimeAddedMillis = System.currentTimeMillis();
    }

    public Task(String taskDetails, UUID uuid, long timeAddedMillis, String dueDateString) {
        mTaskDetails = taskDetails;
        mUUID = uuid;
        mTimeAddedMillis = timeAddedMillis;
        mDueDateString = dueDateString;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID uuid) {
        mUUID = uuid;
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

    public String getDueDate() {
        return mDueDateString;
    }

    public void setDueDate(String dueDate) {
        mDueDateString = dueDate;
    }

    public boolean hasDueDate() {
        return mDueDateString != null;
    }

    // convert the date string into another format
    public String convertDateFormat(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());

        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(mDueDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFormat.format(convertedDate);
    }
}
