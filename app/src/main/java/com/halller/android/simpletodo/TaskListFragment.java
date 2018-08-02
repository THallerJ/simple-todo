package com.halller.android.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TaskListFragment extends Fragment {

    private static final String TAG = "TaskListFragment";
    private TaskListManager mTaskListManager;
    private TaskRecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private TaskEditText mEditText;
    private FloatingActionButton mFab;
    private TextView mEmptyTextView;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mEmptyTextView = (TextView) view.findViewById(R.id.empty_list);

        mRecyclerView = (TaskRecyclerView) view.findViewById(R.id.list_recycler_view);
        updateRecyclerView();

        mFab = (FloatingActionButton) view.findViewById(R.id.add_task_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setVisibility(View.VISIBLE);
                mFab.hide();
                mEditText.requestFocus();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
            }
        });

        mEditText = (TaskEditText) view.findViewById(R.id.add_item_edit_text);
        mEditText.setFab(mFab);
        addToList();

        return view;
    }

    private void updateRecyclerView() {
        mTaskListManager = new TaskListManager(getActivity());

        if (mAdapter == null) {
            mAdapter = new TaskAdapter(getActivity(), mTaskListManager);
            mRecyclerView.addItemDecoration(new TaskListDividerLine(getActivity()));
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setEmptyView(mEmptyTextView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mAdapter.setTaskList(mTaskListManager.getList());
            mAdapter.notifyItemInserted(mTaskListManager.getList().size());
        }
    }

    // Use String in mEditText to create new Task and add to list
    private void addToList() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) && (mEditText.getText().toString()
                        .trim().length() != 0)) {
                    Task item = new Task(mEditText.getText().toString());
                    mTaskListManager.addTask(item);
                    hideKeyboard(getActivity());
                    mFab.show();
                    mEditText.resetState();
                    updateRecyclerView();
                    return true;
                } else {
                    mEditText.resetState();
                    hideKeyboard(getActivity());
                }

                return false;
            }
        });
    }
}

