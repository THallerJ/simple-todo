package com.haller.android.simpletodo.Views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class TaskRecyclerView extends RecyclerView {
    private View emptyView;

    public TaskRecyclerView(Context context) {
        super(context);
    }

    public TaskRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        final Adapter oldAdapter = getAdapter();

        if(oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(emptyObserver);
        }

        super.setAdapter(adapter);

        if(adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        checkIfEmpty();
    }

    public void checkIfEmpty(){
        if(emptyView != null && getAdapter() != null) {
            final boolean emptyViewVisible = getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }
}
