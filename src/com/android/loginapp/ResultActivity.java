package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import com.google.zxing.client.android.CaptureActivity;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(CaptureActivity.BARCODE_ID);
        String parse = message.substring(message.length()-3);


        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(message);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Cow",parse);
        editor.apply();
        // Set the text view as the activity layout
        setContentView(textView);

        Intent in = new Intent(ResultActivity.this, SearchResult.class);
        startActivity(in);
    }
}
