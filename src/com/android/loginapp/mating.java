package com.android.loginapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import com.ofix.barcode.R;

/**
 * Created by Kev on 12/04/2016.
 */
public class Mating extends Activity implements DateDialog.TheListener{


    TextView textView49;
    private RatingBar ratingBar7;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mating);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String details[] = new String[3];
        ratingBar7 = (RatingBar) findViewById(R.id.ratingBar7);
        double CalfStar;
        details[0] = bundle.getString("1");         //type
        details[1] = bundle.getString("2");
        details[2] = bundle.getString("3");
        CalfStar = bundle.getDouble("4");
        ratingBar7.setRating((int) CalfStar);

        textView49 = (TextView) findViewById(R.id.textView49);
    }
    public void SetDate(View v) {
        DialogFragment picker = new DateDialog();
        picker.show(getFragmentManager(), "datePicker");


    }
    @Override
    public void returnDate(String date) {
        // TODO Auto-generated method stub
        System.out.println("********************************In function return***************************");
        textView49.setText(date);
    }
}