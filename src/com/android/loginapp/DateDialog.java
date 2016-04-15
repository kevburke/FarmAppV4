package com.android.loginapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Kev on 08/04/2016.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{


    TheListener listener;

    public interface TheListener{
        public void returnDate(String date);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c= Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        listener = (TheListener) getActivity();
        return new DatePickerDialog(getActivity(),this,year,month,day);

    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(c.getTime());
//        String date=day+"-"+ (month+1)+"-"+year;

        if (listener != null)
        {
            listener.returnDate(formattedDate);

        }

    }
}