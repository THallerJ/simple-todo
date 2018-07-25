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

public class TaskListFragment extends Fragment {

    private static final String TAG = "TaskListFragment";
    private TaskListManager mTaskListManager;
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private TaskEditText mEditText;
    private FloatingActionButton mFab;

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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        initRecyclerView();

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

    private void initRecyclerView() {
        mTaskListManager = new TaskListManager(getActivity());

        if (mAdapter == null) {
            mAdapter = new TaskAdapter(getActivity(), mTaskListManager);

            if (getActivity() != null) {
                mRecyclerView.addItemDecoration(new TaskListDividerLine(getActivity()));
            }

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void addToList() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) && (mEditText.getText().toString()
                        .trim().length() != 0)) {
                    Task item = new Task();
                    item.setTaskDetails(mEditText.getText().toString());
                    mTaskListManager.addTask(item);
                    mAdapter.notifyItemInserted(mTaskListManager.getSize() - 1);
                    hideKeyboard(getActivity());
                    mFab.show();
                    mEditText.resetState();
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

