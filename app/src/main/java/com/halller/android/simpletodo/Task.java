package com.halller.android.simpletodo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Task {

    private static final String TAG = "ToDoItem";
    private UUID mId;
    private String mTaskDetails;
    private Date mDateTimeAdded;

    public Task() {
        mDateTimeAdded = Calendar.getInstance().getTime();
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTaskDetails() {
        return mTaskDetails;
    }

    public void setTaskDetails(String listItem) {
        mTaskDetails = listItem;
    }

    public Date getDateTimeAdded() {
        return mDateTimeAdded;
    }

    public void setDateTimeAdded(Date dateTimeAdded) {
        mDateTimeAdded = dateTimeAdded;
    }

}
