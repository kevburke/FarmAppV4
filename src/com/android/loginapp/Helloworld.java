package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.zxing.client.android.CaptureActivity;
import com.ofix.barcode.R;

import java.util.logging.Level;
import java.util.logging.Logger;

//import com.ofix.barcode.MainActivity;

/***
 * Main layout page .
 *
 *
 */
public class Helloworld extends Activity {
    private static final Logger logger = Logger.getLogger("logger");

    public TextView textView;
    public EditText mEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);


        textView = (TextView) findViewById(R.id.textView);

    }


    public void openCapture() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
        finish();
    }
    public void scanNow(View view) {
        logger.log(Level.INFO, "button works!");
        //Intent intent = new Intent("com.ofix.barcode.MainActivity");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                openCapture();
            }
        }, 2000);
        /*Intent intent = new Intent(Helloworld.this,MainActivity.class);
        startActivity(intent);
        //intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE","");
       // startActivityForResult(intent, 0);
        Log.d("test", "button works!");*/
    }
    public void search(View view){
        mEdit   = (EditText)findViewById(R.id.editText2);
        String jum = mEdit.getText().toString();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Cow",jum);
        editor.apply();
        logger.log(Level.INFO, "Search button works");
        Intent in = new Intent(Helloworld.this, SearchResult.class);
        startActivity(in);

    }
    public void bullList(View view){

        Intent in = new Intent(Helloworld.this, ViewBullList.class);
        startActivity(in);

    }

}


