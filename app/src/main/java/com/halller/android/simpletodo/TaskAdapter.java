package com.halller.android.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
    private List<Task> mTaskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        mContext = context;
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
        Task item = mTaskList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private Task mTask;
        private CheckBox mItemCheckBox;
        private TextView mItemTextView;

        public TaskHolder(View itemView) {
            super(itemView);
            mItemCheckBox = (CheckBox) itemView.findViewById(R.id.task_item_check_box);
            mItemTextView = (TextView) itemView.findViewById(R.id.task_item_text_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = TaskActivity.newIntent(mContext, mTask.getId());
                    mContext.startActivity(intent);
                }
            });
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


    }
}
