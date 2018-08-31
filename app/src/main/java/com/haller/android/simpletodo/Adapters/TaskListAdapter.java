package com.haller.android.simpletodo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.haller.android.simpletodo.Activities.TaskActivity;
import com.haller.android.simpletodo.Fragments.TaskListFragment;
import com.haller.android.simpletodo.R;
import com.haller.android.simpletodo.Utilities.Task;
import com.haller.android.simpletodo.Utilities.TaskListManager;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskHolder> {

    private static final String TAG = "TaskListAdapter";
    private Context mContext;
    private TaskListManager mTaskListManager;
    private TaskListFragment mTaskListFragment;
    private List<Task> mTaskList;

    public TaskListAdapter(Context context, TaskListFragment taskListFragment, TaskListManager taskListManager) {
        mContext = context;
        mTaskListFragment = taskListFragment;
        mTaskListManager = taskListManager;
        mTaskList = taskListManager.getList();
    }

    public void setList(List<Task> taskList) {
        mTaskList = taskList;
    }

    @NonNull
    @Override
    public TaskListAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new TaskListAdapter.TaskHolder(layoutInflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.TaskHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemViewType(int position) {
        if (mTaskList.get(position).hasDueDate()) {
            return R.layout.task_item_date;
        } else {
            return R.layout.task_item;
        }
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private Task mTask;
        private CheckBox mItemCheckBox;
        private TextView mItemTextView;
        private TextView mDueDateTextView;
        private int mAdapterPosition;

        private Task mDeletedTask;
        private boolean onBind;

        private TaskHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(viewType, parent, false));
            mDueDateTextView = (TextView) itemView.findViewById(R.id.small_due_date_text_view);
            mItemCheckBox = (CheckBox) itemView.findViewById(R.id.task_item_check_box);
            mItemTextView = (TextView) itemView.findViewById(R.id.task_item_text_view);
            mItemCheckBox.setOnCheckedChangeListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = TaskActivity.newIntent(mContext, mTask, mTaskListManager);
                    mContext.startActivity(intent);
                }
            });
        }

        private void bind(Task task) {
            mTask = task;
            mItemTextView.setText(mTask.getTaskDetails());
            onBind = true;
            mItemCheckBox.setChecked(false);
            onBind = false;

            if (mTask.hasDueDate()) {
                String dateString = mTask.convertDateFormat("MMM d");
                mDueDateTextView.setText(dateString);
            }
        }

        private void deleteTask(int position) {
            mDeletedTask = mTaskList.get(position);
            mTaskList.remove(position);
            mTaskListManager.removeTask(mTask);
            notifyItemRangeChanged(position, mTaskList.size());
            notifyItemRemoved(position);
            undoDeletion();
        }

        private void undoDeletion() {
            final Snackbar snackbar = Snackbar.make(mItemCheckBox, mContext.getString(R.string.task_completed),
                    Snackbar.LENGTH_LONG);
            mTaskListFragment.setUndoSnackbar(snackbar);
            snackbar.setAction(mContext.getString(R.string.undo_button), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mDeletedTask != null) {
                        addItem(mDeletedTask, mAdapterPosition);
                        mDeletedTask = null;
                        snackbar.dismiss();
                    }
                }
            });

            snackbar.show();
        }

        private void addItem(Task task, int position) {
            mTaskList.add(position, task);
            mTaskListManager.addTask(task);
            notifyItemInserted(mAdapterPosition);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!onBind) {
                mAdapterPosition = getAdapterPosition();
                deleteTask(mAdapterPosition);
            }
        }
    }
}
