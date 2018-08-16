package com.halller.android.simpletodo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

public class TaskFragment extends Fragment {

    private static final String TAG = "TaskFragment";
    private static final String ARG_TASK= "task";

    private Task mTask;
    private TaskEditText mTaskEditText;
    private TextView mTaskTextView;
    private TaskListManager mTaskListManager;

    public static TaskFragment newInstance(Task task) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, task);

        TaskFragment frag = new TaskFragment();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask = (Task) getArguments().getSerializable(ARG_TASK);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_fragment, container, false);

        mTaskEditText = (TaskEditText) view.findViewById(R.id.task_edit_text);
        mTaskEditText.setText(mTask.getTaskDetails());
        mTaskEditText.setSelection(mTaskEditText.getText().length());
        mTaskEditText.setDefaultText(mTask.getTaskDetails());

        mTaskTextView = (TextView) view.findViewById(R.id.task_text_view);
        mTaskTextView.setText("Insert Stuff");

        mTaskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.d(TAG, "onEditorAction: method called");
                    mTaskEditText.resetState();
                    mTaskEditText.hideKeyboard(getActivity());
                    return true;
                } else {
                    return false;
                }
            }
        });

        return view;
    }

    public void setTaskListManager(TaskListManager taskListMangager){
        mTaskListManager = taskListMangager;
    }
}

