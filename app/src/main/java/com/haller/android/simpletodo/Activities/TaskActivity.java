package com.haller.android.simpletodo.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.haller.android.simpletodo.Fragments.TaskFragment;
import com.haller.android.simpletodo.Utilities.Task;
import com.haller.android.simpletodo.Utilities.TaskListManager;

public class TaskActivity extends SingleFragmentActivity {

    private static final String EXTRA_TASK = "com.haller.android.simpletodo.task";
    private static TaskListManager mTaskListManager;

    public static Intent newIntent(Context context, Task task, TaskListManager taskListManager) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(EXTRA_TASK, task);
        mTaskListManager = taskListManager;
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Task task = (Task) getIntent().getSerializableExtra(EXTRA_TASK);
        TaskFragment fragment = TaskFragment.newInstance(task);
        fragment.setTaskListManager(mTaskListManager);
        return fragment;
    }
}
