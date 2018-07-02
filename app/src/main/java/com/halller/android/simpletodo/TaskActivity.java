package com.halller.android.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class TaskActivity extends SingleFragmentActivity {

    private static final String EXTRA_TASK_ID = "com.haller.android.simpletodo.taskid";

    @Override
    protected Fragment createFragment() {
        UUID taskId = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        return TaskFragment.newInstance(taskId);
    }

    public static Intent newIntent(Context context, UUID taskId){
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }
}
