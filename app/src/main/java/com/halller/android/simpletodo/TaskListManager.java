package com.halller.android.simpletodo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
* Singleton class used to manage a to do list.
* Can add to the list and retrieve data from the list
*/
public class TaskListManager {

    private static final String TAG = "ListManager";
    private static TaskListManager firstInstance = null;
    private List<Task> mToDoList;

    public static TaskListManager getInstance() {
        if (firstInstance == null) {
            firstInstance = new TaskListManager();
        }

        return firstInstance;
    }

    private TaskListManager() {
        mToDoList = new ArrayList<>();
    }

    public void addTask(Task item){
        mToDoList.add(item);
    }

    public List<Task> getList() {
        // TESTING
        for(int i = 0; i < mToDoList.size(); i++) {
            Log.d(TAG, "getList: " + mToDoList.get(i).getItemDetails());
        }
        return mToDoList;
    }

    public Task getTask(UUID taskId){
       for(Task task : mToDoList) {
           if(taskId.equals(task.getId())){
               return task;
           }
       }

       return null;
    }
}
