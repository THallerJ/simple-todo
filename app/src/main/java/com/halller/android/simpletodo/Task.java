package com.halller.android.simpletodo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {

    private static final String TAG = "ToDoItem";
    private UUID mId;
    private String mItemDetails;
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

    public String getItemDetails() {
        return mItemDetails;
    }

    public void setItemDetails(String listItem) {
        mItemDetails = listItem;
    }

    public Date getDateTimeAdded() {
        return mDateTimeAdded;
    }

    public void setDateTimeAdded(Date dateTimeAdded) {
        mDateTimeAdded = dateTimeAdded;
    }

}
