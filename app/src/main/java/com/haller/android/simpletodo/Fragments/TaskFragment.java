package com.haller.android.simpletodo.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haller.android.simpletodo.R;
import com.haller.android.simpletodo.Utilities.Task;
import com.haller.android.simpletodo.Utilities.TaskListManager;
import com.haller.android.simpletodo.Views.TaskEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskFragment extends Fragment {

    private static final String TAG = "TaskFragment";
    private static final String ARG_TASK = "task";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Task mTask;
    private TextView mDateTextView;
    private TaskEditText mTaskEditText;
    private TaskListManager mTaskListManager;
    private LinearLayout mLinearLayout;

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

        mDateTextView = (TextView) view.findViewById(R.id.due_date_text_view);

        if (mTask.getDueDate() == null) {
            mDateTextView.setText(R.string.no_date);
        } else {
            mDateTextView.setText(mTask.getDueDate());
        }

        mTaskEditText = (TaskEditText) view.findViewById(R.id.task_edit_text);
        mTaskEditText.setText(mTask.getTaskDetails());
        mTaskEditText.setSelection(mTaskEditText.getText().length());
        mTaskEditText.setDefaultText(mTask.getTaskDetails());

        mTaskEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (mTaskEditText.hasText()) {
                        mTask.setTaskDetails(mTaskEditText.getText().toString());
                        mTaskListManager.updateTaskDetails(mTask);
                    }

                    mTaskEditText.resetState();
                    mTaskEditText.hideKeyboard(getActivity());
                    return true;
                } else {
                    return false;
                }
            }
        });

        mLinearLayout = (LinearLayout) view.findViewById(R.id.linear_layout_date);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    DatePickerFragment datePicker = new DatePickerFragment();
                    datePicker.setTargetFragment(TaskFragment.this, REQUEST_DATE);
                    datePicker.show(getFragmentManager(), DIALOG_DATE);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

            String dateString = getDateString(date, "MMM d, yyyy");
            mTask.setDueDate(dateString);
            mDateTextView.setText(dateString);
            mTaskListManager.updateDueDate(mTask);
        }
    }

    public String getDateString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    public void setTaskListManager(TaskListManager taskListManager) {
        mTaskListManager = taskListManager;
    }
}

