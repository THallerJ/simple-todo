package com.halller.android.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private static final String TAG = "TaskAdapter";
    private Context mContext;
    private TaskListManager mTaskListManager;
    private List<Task> mTaskList;

    public TaskAdapter(Context context, TaskListManager taskListManager) {
        mContext = context;
        mTaskListManager = taskListManager;
        mTaskList = taskListManager.getList();
    }

    public void setLists(List<Task> taskList){
        mTaskList = taskList;
    }

    @NonNull
    @Override
    public TaskAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskAdapter.TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskHolder holder, int position) {
        Log.d(TAG, "{onBindViewHolder: called ");
        Task task = mTaskList.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        private Task mTask;
        private CheckBox mItemCheckBox;
        private TextView mItemTextView;
        private int mAdapterPosition;
        private Task mDeletedTask;
        private boolean onBind;

        public TaskHolder(View itemView) {
            super(itemView);
            mItemCheckBox = (CheckBox) itemView.findViewById(R.id.task_item_check_box);
            mItemTextView = (TextView) itemView.findViewById(R.id.task_item_text_view);
            mItemCheckBox.setOnCheckedChangeListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = TaskActivity.newIntent(mContext, mTask);
                    mContext.startActivity(intent);
                }
            });
        }

        public void bind(Task task) {
            mTask = task;
            mItemTextView.setText(mTask.getTaskDetails());
            onBind = true;
            mItemCheckBox.setChecked(false);
            onBind = false;
        }

        public void deleteTask(int position) {
            mDeletedTask = mTaskList.get(position);
            mTaskList.remove(position);
            mTaskListManager.removeTask(mTask);
            notifyItemRangeChanged(position, mTaskList.size());
            notifyItemRemoved(position);
            undoDeletion();
        }

        public void undoDeletion() {
            Snackbar snackbar = Snackbar.make(mItemCheckBox, mContext.getString(R.string.task_completed),
                    Snackbar.LENGTH_LONG);
            TaskListFragment.setSnackbar(snackbar);
            snackbar.setAction(mContext.getString(R.string.undo_button), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItem(mDeletedTask, mAdapterPosition);
                }
            });

            snackbar.show();
        }

        public void addItem(Task task, int position) {
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
