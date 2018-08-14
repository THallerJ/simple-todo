package com.halller.android.simpletodo;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class TaskListFragment extends Fragment {

    private static final String TAG = "TaskListFragment";
    private TaskListManager mTaskListManager;
    private TaskRecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private TaskEditText mEditText;
    private FloatingActionButton mFab;
    private TextView mEmptyTextView;
    private Snackbar mUndoSnackbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task_list, container, false);

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
        mEditText.setPersistence(false);
        addToList();

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int screenHeight = view.getRootView().getHeight();

                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15 || mTaskListManager.getList().size() != 0) {
                    // keyboard is opened
                    mEmptyTextView.setVisibility(View.GONE);

                    if (mUndoSnackbar != null && mEditText.isFocused()) {
                        mUndoSnackbar.dismiss();
                    }
                } else {
                    // keyboard is closed
                    mEmptyTextView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private void updateRecyclerView() {
        mTaskListManager = new TaskListManager(getActivity());

        if (mAdapter == null) {
            mAdapter = new TaskAdapter(getActivity(), this, mTaskListManager);
            mRecyclerView.addItemDecoration(new TaskListDividerLine(getActivity()));
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mAdapter.setLists(mTaskListManager.getList());
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
                    mEditText.hideKeyboard(getActivity());
                    mFab.show();
                    mEditText.resetState();
                    updateRecyclerView();
                    mAdapter.notifyItemInserted(mTaskListManager.getList().size());
                    return true;
                } else {
                    mEditText.resetState();
                    mEditText.hideKeyboard(getActivity());
                }

                return false;
            }
        });
    }

    // Allows Snackbar from TaskAdapter to be dismissed when keyboard is open
    public void setUndoSnackbar(Snackbar snackbar) {
        mUndoSnackbar = snackbar;
    }
}

