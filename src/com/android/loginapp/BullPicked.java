package com.android.loginapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.ofix.barcode.R;

/**
 * Created by Kev on 06/04/2016.
 */
public class BullPicked extends Activity {
    public TextView textView16;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullpicked);
        textView16 = (TextView) findViewById(R.id.textView16);

    }
}