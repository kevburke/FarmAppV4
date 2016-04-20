package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.client.android.CaptureActivity;
import com.ofix.barcode.R;

import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Main layout page .
 *
 *
 */
public class Helloworld extends Activity {
    private static final Logger logger = Logger.getLogger("logger");
    public static final String MY_PREFS = "SharedPreferences";
    public TextView textView;
    public EditText mEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textView = (TextView) findViewById(R.id.textView);
    }

    /**
     * Call Camera Application
     */

    public void openCapture() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Scan button opens camera application and calls barcode files
     * @param view
     */
    public void scanNow(View view) {
        logger.log(Level.INFO, "button works!");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                openCapture();
            }
        }, 500);            //0.5 seconds

    }

    /**
     *Entering jumbo id to select animal
     * @param view
     */
    public void search(View view){
        mEdit   = (EditText)findViewById(R.id.editText2);
        String jum = mEdit.getText().toString();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Cow",jum);
        editor.apply();
        String jumbo= "";
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        String table = prefs.getString("username", "");
        SQLiteDatabase db = this.openOrCreateDatabase("ICBF", MODE_PRIVATE, null);

        db.beginTransaction();
        logger.log(Level.INFO, "Search button works");
        try{
            Cursor cur =  db.rawQuery("SELECT * FROM "+table+" WHERE jumbo="+jum, null);        //error checking
            if (cur.moveToFirst()) {
                jumbo= cur.getString(1);
                db.setTransactionSuccessful();
                //db.endTransaction();


            }
            else {
                db.setTransactionSuccessful();
                mEdit.setText("");
                Toast.makeText(getApplicationContext(),
                        "This Id Does not exist. Try Again",
                        Toast.LENGTH_SHORT).show();
            }

        }catch (SQLException e){

            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
        if(jum.equals(jumbo)){
            Intent in = new Intent(Helloworld.this, SearchResult.class);
            startActivity(in);
        }
    }

    /**
     * Intent to bulllist
     * @param view
     */
    public void bullList(View view){

        Intent in = new Intent(Helloworld.this, ViewBullList.class);
        startActivity(in);

    }

    /**
     * Intent to my herd details
     * @param view
     */
    public void MyHerd(View view) {

        Intent in = new Intent(Helloworld.this, MyHerd.class);
        startActivity(in);
    }
}


