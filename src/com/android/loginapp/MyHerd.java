package com.android.loginapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.ofix.barcode.R;

/**
 * Created by Kev on 18/04/2016.
 */
public class MyHerd extends Activity {
    SQLiteDatabase db;
    SQLiteDatabase db2;
    public static final String MY_PREFS = "SharedPreferences";
    String jumbo;
    String sex;
    String breed;
    String dob;
    String replace;
    String terminal;
    String calfDiff;
    String replaceStar;
    String termStar;
    private RatingBar ratingBar5;
    private RatingBar ratingBar6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myherd);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
       // String barcode = preferences.getString("Cow", "");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        String table = prefs.getString("username", "");
        String path = "ICBF";
        ListView listView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter cowAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);

        String sql ="";
        sql += "SELECT * FROM "+ table;

        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);

        String [] listElements = new String[80];
        System.out.println("at the query *********");
        System.out.println("at the query *********"+table+"*********");
        db.beginTransaction();

        try {
            Cursor cur = db.rawQuery(sql,null);
            // if(cur !=null) {
            if(cur.moveToFirst()) {

                System.out.println("Inside first *********");
                int ii = 0;
                while (!cur.isLast()) {
                    System.out.println("Inside while *********");
                    cowAdapter.add(cur.getString(2));
                    System.out.println("*********"+cur.getString(2));
                    listElements[ii++] = cur.getString(2);
                    cur.moveToNext();
                }
                cowAdapter.add(cur.getString(2));
                listElements[ii] = cur.getString(2);

                db.setTransactionSuccessful();
            }else{
                db.setTransactionSuccessful();
                Toast.makeText(getApplicationContext(),
                        "This selection is not available.",
                        Toast.LENGTH_SHORT).show();
                Intent in = new Intent(MyHerd.this, Helloworld.class);
                startActivity(in);
            }

        } catch (SQLException e) {
            System.out.println("***********In Catch**********");


        } finally {
            db.endTransaction();
        }

        listView.setAdapter(cowAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                db2 = openOrCreateDatabase("ICBF",MODE_PRIVATE, null);

                db2.beginTransaction();
                System.out.println("***************"+position+"***********************");
                try{                                     //type=BllsTerminal name=TBullname
                    Cursor cur =  db2.rawQuery("SELECT * FROM "+table+" WHERE num='" + listElements[position]+"'", null);
                    cur.moveToFirst();
                    jumbo = cur.getString(1);
                    sex = cur.getString(3);
                    dob = cur.getString(4);
                    breed = cur.getString(7);
                    replace =cur.getString(10);
                    terminal =cur.getString(12);
                    calfDiff = cur.getString(15);
                    replaceStar =cur.getString(18);
                    termStar = cur.getString(19);
                   // CarcassWeight = cur.getString(15);
                   // CarcassConform = cur.getString(17);
                    db2.setTransactionSuccessful();
                    System.out.println("****************Data recieved************************");
                    System.out.println("***********************************************");
                    //System.out.println(StarsWithin);
                    //System.out.println(CarcassWeight);
                   // System.out.println("********************************"+CarcassConform+"**********************");
                }catch (SQLException e){
                    //salary.setText("nope");
                }finally {
                    db2.endTransaction();
                }



                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);
                double ownStar;
                //double awayStar;
                double star;
                if(termStar.equals("")) {
                    star = 0;
                }
                else
                    star = Double.parseDouble(termStar);    //star/termStar equals dam tars

                //System.out.println(StarsWithin);
                ownStar = Double.parseDouble(replaceStar);              //ownStar/StarsWithin equals sire star
                //ratingBar5.setRating((int) ownStar);
                //awayStar = Double.parseDouble(termStar);
                //ratingBar6.setRating((int)awayStar);





                ((TextView)popupWindow.getContentView().findViewById(R.id.textView18)).setText(jumbo);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView19)).setText(sex);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView20)).setText(dob);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView21)).setText(breed);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView69)).setText(replace);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView71)).setText(terminal);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView73)).setText(calfDiff);
                ((RatingBar)popupWindow.getContentView().findViewById(R.id.ratingBar5)).setRating((float) ownStar);
                ((RatingBar)popupWindow.getContentView().findViewById(R.id.ratingBar6)).setRating((float) star);
                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();

                    }});

                Button select = (Button)popupView.findViewById(R.id.select);
                select.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent in = new Intent(MyHerd.this, Helloworld.class);
                        startActivity(in);
                    }
                });
                // popupWindow.showAsDropDown(view, 100, 50);
                popupWindow.showAtLocation(view,80,150,180);
            }}
        );
    }


}