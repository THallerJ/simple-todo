package com.halller.android.simpletodo;

import android.content.Context;

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

    public TaskListManager(Context context) {
        mTaskList = new ArrayList<>();
    }

    public void addTask(Task item) {
        mTaskList.add(item);
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
}
