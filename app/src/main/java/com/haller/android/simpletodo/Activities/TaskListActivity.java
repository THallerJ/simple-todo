package com.haller.android.simpletodo.Activities;

import android.support.v4.app.Fragment;

import com.haller.android.simpletodo.Fragments.TaskListFragment;

public class TaskListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }

}

