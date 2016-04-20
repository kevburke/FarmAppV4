package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.ofix.barcode.R;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Kev on 25/03/2016.
 */

public class SearchResult extends Activity {

    String jumbo;
    String num;
    String sex;
    String dob;
    String name;
    String status;
    String breed;
    String dam;
    String sire;
    String replacement;
    String replacement_maternal;
    String terminal;
    String replacement_maternal_prog;
    String dairy;
    String calving_diff;
    String trait_reliability;
    String replaceStar;
    String termStar;
    String dairyStar;
    String docileStar;
    String carcassWeighStar;
    String carcassConformStar;
    String daughterMilkStar;
    String daughterCalvIntStar;
    String replacement_index;
    String docility_index;
    String docility_reliability;
    String carcassWeiIndx;
    String carcassWeightRel;
    String daughter_Calving_Diff;
    String daughter_Milk_index;
    String daughter_milk_rel;
    String carcass_conform_index;
    String carcass_conform_rel;
    String daughter_calving_rel;
    String daughter_calv_int;
    String daughter_calv_int_rel;

    SQLiteDatabase db;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView7;
    TextView textView6;
    TextView textView91;
    TextView textView77;
    TextView textView79;
    TextView textView83;
    TextView textView85;
    TextView textView87;
    TextView textView89;

    private RatingBar ratingBar;
    private RatingBar ratingBar9;
    private static final Logger logger = Logger.getLogger("logger");

    public static final String MY_PREFS = "SharedPreferences";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        ratingBar9 = (RatingBar)findViewById(R.id.ratingBar9);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String barcode = preferences.getString("Cow", "");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        String table = prefs.getString("username", "");
        String path = "ICBF";
        db = this.openOrCreateDatabase(path,MODE_PRIVATE, null);
        SQLiteDatabase db = this.openOrCreateDatabase("ICBF", MODE_PRIVATE, null);

        db.beginTransaction();

        try{
            Cursor cur =  db.rawQuery("SELECT * FROM "+table+" WHERE jumbo="+barcode, null);
            cur.moveToFirst();

            jumbo= cur.getString(1);
            num= cur.getString(2);
            sex= cur.getString(3);
            dob= cur.getString(4);
            name= cur.getString(5);
            status= cur.getString(6);
            breed= cur.getString(7);
            dam= cur.getString(8);
            sire= cur.getString(9);
            replacement= cur.getString(10);
            replacement_maternal= cur.getString(11);
            terminal= cur.getString(12);
            replacement_maternal_prog= cur.getString(13);
            dairy= cur.getString(14);
            calving_diff= cur.getString(15);
            trait_reliability= cur.getString(16);
            replacement_index= cur.getString(17);
            replaceStar= cur.getString(18);
            termStar= cur.getString(19);
            dairyStar= cur.getString(20);
            docileStar= cur.getString(21);
            carcassWeighStar= cur.getString(22);
            carcassWeiIndx= cur.getString(23);
            carcassWeightRel= cur.getString(24);
            carcassConformStar= cur.getString(25);
            daughterMilkStar= cur.getString(26);
            daughterCalvIntStar= cur.getString(27);
            docility_index= cur.getString(28);
            docility_reliability= cur.getString(29);
            daughter_Calving_Diff= cur.getString(30);
            daughter_calving_rel= cur.getString(31);
            daughter_Milk_index= cur.getString(32);
            carcass_conform_index= cur.getString(33);
            carcass_conform_rel= cur.getString(34);
            daughter_milk_rel= cur.getString(35);
            daughter_calv_int= cur.getString(36);
            daughter_calv_int_rel= cur.getString(37);

            db.setTransactionSuccessful();
        }catch (SQLException e){
            Toast.makeText(getBaseContext(), "Invalid Jumbo Try Again", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SearchResult.this, Helloworld.class);
            Bundle bundle = new Bundle();
            startActivity(intent);
        }finally {
            db.endTransaction();
        }
        System.out.println(
                jumbo+"\n"+
                num+"\n"+
                sex+"\n"+
                dob+"\n"+
                name+"\n"+
                status+"\n"+
                breed+"\n"+
                dam+"\n"+
                sire+"\n"+
                replacement+"\n"+
                replacement_maternal+"\n"+
                terminal+"\n"+
                replacement_maternal_prog+"\n"+
                dairy+"\n"+
                calving_diff+"\n"+
                trait_reliability+"\n"+
                replaceStar+"\n"+
                termStar+"\n"+
                dairyStar+"\n"+
                docileStar+"\n"+
                carcassWeighStar+"\n"+
                carcassConformStar+"\n"+
                daughterMilkStar+"\n"+
                daughterCalvIntStar+"\n"+
                replacement_index+"\n"+
                docility_index+"\n"+
                docility_reliability+"\n"+
                carcassWeiIndx+"\n"+
                carcassWeightRel+"\n"+
                daughter_Calving_Diff+"\n"+
                daughter_Milk_index+"\n"+
                daughter_milk_rel+"\n"+
                carcass_conform_index+"\n"+
                carcass_conform_rel+"\n"+
                daughter_calving_rel+"\n"+
                daughter_calv_int+"\n"+
                daughter_calv_int_rel+"\n"
        );

        textView1 = (TextView) findViewById(R.id.textView2);//jumbo
        textView2 = (TextView) findViewById(R.id.textView4);//Animal id
        textView3 = (TextView) findViewById(R.id.textView5);//sex
        textView6 = (TextView) findViewById(R.id.textView6);//dob
        textView91 = (TextView) findViewById(R.id.textView91);//breed
        textView7 = (TextView) findViewById(R.id.textView7);// replace   €
        textView77 = (TextView) findViewById(R.id.textView77);// terminal €
        textView79 = (TextView) findViewById(R.id.textView79); //calf diff
        textView83 = (TextView) findViewById(R.id.textView83); //carcass Index
        textView85 = (TextView) findViewById(R.id.textView85); //daughter calf rel
        textView87 = (TextView) findViewById(R.id.textView87); //daughter calf diff
        textView89 = (TextView) findViewById(R.id.textView89); //daughter int Rel



        textView1.setText(jumbo);
        textView2.setText(num);
        textView3.setText(sex);
        textView6.setText(dob);
        textView91.setText(breed);
        textView7.setText(replacement);
        textView77.setText(terminal);
        textView79.setText(calving_diff);
        textView83.setText(carcassWeiIndx);
        textView83.setText(carcassWeiIndx);
        textView85.setText(daughter_milk_rel);
        textView87.setText(daughter_Calving_Diff);
        textView89.setText(daughter_calv_int_rel);



        double ownStar;
        ownStar = Double.parseDouble(replaceStar);              //ownStar/StarsWithin equals sire star


        ratingBar.setRating((float) ownStar);


        double star;
        if(termStar.equals("")) {
            star = 0;
        }
        else
            star = Double.parseDouble(termStar);    //star/termStar equals dam tars

        ratingBar9.setRating((float) star);

    }
    public void bullList(View view) {
        logger.log(Level.INFO, "button works!");

        Intent intent = new Intent(SearchResult.this,BullSearch.class);
        startActivity(intent);

        Log.d("BullSearch", "button works!");
    }
}