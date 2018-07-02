package com.halller.android.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class TaskListFragment extends Fragment {

    private static final String TAG = "TaskListFragment";
    private TaskListManager toDoList;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private EditText mEditText;
    private FloatingActionButton mFab;
    private boolean isActive;

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

        mEditText = (EditText) view.findViewById(R.id.add_item_edit_text);
        addToList();

        mFab = (FloatingActionButton) view.findViewById(R.id.add_task_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setVisibility(View.VISIBLE);
                mFab.setVisibility(View.INVISIBLE);
                mEditText.requestFocus();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
            }
        });

        return view;
    }

    private void initRecyclerView() {
        toDoList = TaskListManager.getInstance();

        if (mAdapter == null) {
            mAdapter = new ListAdapter(toDoList.getList());

            if (getActivity() != null) {
                RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
                mRecyclerView.addItemDecoration(decoration);
            }

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }

    private void addToList() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                // TODO: Create new class that extends EditText. Override onKeyPreIme to hide EditText and Fab when the back button is pressed. In this fragment, replace the editText with the new one
                isActive = true;
                if ((actionId == EditorInfo.IME_ACTION_DONE) && !(mEditText.getText().toString()
                        .trim().length() == 0)) {
                    Task item = new Task();
                    item.setItemDetails(mEditText.getText().toString());
                    toDoList.addTask(item);
                    mEditText.setText("");

                    if (getActivity() != null) {
                        hideKeyboard(getActivity());
                    }

                    mFab.setVisibility(View.VISIBLE);
                    mEditText.setVisibility(View.INVISIBLE);
                    return true;
                }

                return false;
            }
        });

        mEditText.setVisibility(View.INVISIBLE);
    }

    private class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String TAG = "ListHolder";
        private Task mTask;
        private CheckBox mItemCheckBox;
        private TextView mItemTextView;

        public ListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mItemCheckBox = (CheckBox) itemView.findViewById(R.id.task_item_check_box);
            mItemTextView = (TextView) itemView.findViewById(R.id.task_item_text_view);
        }

        public void bind(Task item) {
            mTask = item;
            mItemTextView.setText(mTask.getItemDetails());
            mItemCheckBox.setChecked(mTask.isCompleted());
            mItemCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mTask.setCompleted(b);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent = TaskActivity.newIntent(getActivity(), mTask.getId());
            startActivity(intent);
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ListHolder> {

        private static final String TAG = "ListAdapter";
        private List<Task> mToDoList;

        public ListAdapter(List<Task> items) {
            mToDoList = items;
        }

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            Log.d(TAG, "{onBindViewHolder: called ");
            Task item = mToDoList.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return mToDoList.size();
        }
    }
}
