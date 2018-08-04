package com.halller.android.simpletodo;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class TaskEditText extends AppCompatEditText {

    private FloatingActionButton mFab;

    public TaskEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TaskEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskEditText(Context context) {
        super(context);
    }

    // Make TaskEditText disappear when user presses back button in nav bar
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            resetState();
        }

        return super.onKeyPreIme(keyCode, event);
    }

    public void setFab(FloatingActionButton fab) {
        this.mFab = fab;
    }

    public void resetState() {
        setText("");
        setVisibility(GONE);

        if (mFab != null) {
            mFab.show();
        }
    }
}
