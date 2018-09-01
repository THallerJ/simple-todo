package com.haller.android.simpletodo.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.haller.android.simpletodo.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "DatePickerFragment";
    public static final String EXTRA_DATE = "com.haller.android.simpletodo.date";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(), R.style.DarkDatePicker,
                this, year, month, day);

        datePicker.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.remove_date), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onDateSet(datePicker.getDatePicker(), 0, 0, 0);
                datePicker.dismiss();
            }
        });

        return datePicker;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        if (getTargetFragment() == null) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, cal.getTime());

        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }
}
