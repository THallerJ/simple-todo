package com.halller.android.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class TaskEditText extends AppCompatEditText {

    private FloatingActionButton mFab;

    // Determines if the TaskEditText should be hidden once the user is done entering text
    private boolean mIsPersistent = true;

    // displayed in TaskEditText when the user presses the back button to exit the view
    private String mDefaultText = "";

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

    public void resetState() {
        setText(mDefaultText);
        setSelection(getText().length());

        if(!mIsPersistent) {
            setVisibility(GONE);
        }

        if (mFab != null) {
            mFab.show();
        }
    }

    // hides the soft keyboard
    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setFab(FloatingActionButton fab) {
        this.mFab = fab;
    }

    public void setPersistence(boolean isPersistent){
        mIsPersistent = isPersistent;
    }

    public void setDefaultText(String defaultText){
        mDefaultText = defaultText;
    }
}
