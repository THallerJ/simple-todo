package com.halller.android.simpletodo;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;

public class TaskEditText extends AppCompatEditText {


    public TaskEditText(Context context) {
        super(context);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return super.onKeyPreIme(keyCode, event);
    }
}
