package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.ofix.barcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kev on 04/04/2016.
 */
public class BullSelect extends Activity {
    SQLiteDatabase db;
    TextView textView17;
    List<String> TBullName = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullsel);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String[] details = new String[5];
        details[0] = bundle.getString("1");         //type
        details[1] = bundle.getString("2");         //Breed
        details[2] = bundle.getString("3");         //Ratings
        details[3] = bundle.getString("4");         //CalvingRating
        details[4] = bundle.getString("5");         //Gestation
        TextView textView17 = (TextView) findViewById(R.id.textView17);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter bullAdapter;
        textView17.setText("Filtered by: " + details[0] + "\n" + details[1] + "\n" + details[2] + "\n" + details[3] + "\n" +
                details[4]);

        String path = "ICBF";
        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);


        System.out.println("at the query *********");
        db.beginTransaction();
        if (details[0] == "Terminal") {
            try {
                Cursor cur = db.rawQuery("SELECT * FROM " + details[0] + " WHERE Tbreed=" + details[1] +" AND "+ "TStarsWithin="+ details[2],null);
                cur.moveToFirst();


                TBullName.add(cur.getString(1));


                db.setTransactionSuccessful();


            } catch (SQLException e) {
                //salary.setText("nope");
            } finally {
                db.endTransaction();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    TBullName );

            listView.setAdapter(arrayAdapter);
        } else if (details[0] == "Replacement") {
            try {
                Cursor cur = db.rawQuery("SELECT * FROM " + details[0] + " WHERE Mbreed=" +details[1]+ " AND "+ "MStarsWithin"+ details[2], null);
                cur.moveToFirst();
                db.setTransactionSuccessful();


            } catch (SQLException e) {
                //salary.setText("nope");
            } finally {
                db.endTransaction();
            }
        }


    }
}