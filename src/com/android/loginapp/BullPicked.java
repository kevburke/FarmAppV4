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
import com.ofix.barcode.R;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Kev on 06/04/2016.
 */
public class BullPicked extends Activity  {
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
    String Rank;
    String Code;
    String BullName;
    String Breed;
    String BullIndex;
    String StarsWithin;
    String StarsAcross;
    Double CalfStar;
    Double CalfAcrossStar;
    String Gest;
    String Supplier;

    SQLiteDatabase db;
    SQLiteDatabase db2;
    TextView textView23;
    TextView textView25;
    TextView textView27;
    TextView textView29;
    TextView textView34;
    TextView textView40;
    TextView textView41;
    TextView textView42;
    TextView textView43;
    TextView textView92;
    TextView textView94;
    TextView textView96;
    TextView textView98;


    private static final Logger logger = Logger.getLogger("logger");

    public static final String MY_PREFS = "SharedPreferences";
    private RatingBar ratingBar2;
    private RatingBar ratingBar3;
    private RatingBar ratingBar4;
    private RatingBar ratingBar11;
    String type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullpicked);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        ratingBar11 = (RatingBar) findViewById(R.id.ratingBar11);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String barcode = preferences.getString("Cow", "");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        String table = prefs.getString("username", "");

        ratingBar3 = (RatingBar)findViewById(R.id.ratingBar3);
        ratingBar4 = (RatingBar)findViewById(R.id.ratingBar4);
        String path = "ICBF";
        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);

        db.beginTransaction();

        try {
            Cursor cur = db.rawQuery("SELECT * FROM " + table + " WHERE jumbo=" + barcode, null);
            cur.moveToFirst();

            jumbo = cur.getString(1);
            num = cur.getString(2);
            sex = cur.getString(3);
            dob = cur.getString(4);
            name = cur.getString(5);
            status = cur.getString(6);
            breed = cur.getString(7);
            dam = cur.getString(8);
            sire = cur.getString(9);
            replacement = cur.getString(10);
            replacement_maternal = cur.getString(11);
            terminal = cur.getString(12);
            replacement_maternal_prog = cur.getString(13);
            dairy = cur.getString(14);
            calving_diff = cur.getString(15);
            trait_reliability = cur.getString(16);
            replacement_index = cur.getString(17);
            replaceStar = cur.getString(18);
            termStar = cur.getString(19);
            dairyStar = cur.getString(20);
            docileStar = cur.getString(21);
            carcassWeighStar = cur.getString(22);
            carcassWeiIndx = cur.getString(23);
            carcassWeightRel = cur.getString(24);
            carcassConformStar = cur.getString(25);
            daughterMilkStar = cur.getString(26);
            daughterCalvIntStar = cur.getString(27);
            docility_index = cur.getString(28);
            docility_reliability = cur.getString(29);
            daughter_Calving_Diff = cur.getString(30);
            daughter_calving_rel = cur.getString(31);
            daughter_Milk_index = cur.getString(32);
            carcass_conform_index = cur.getString(33);
            carcass_conform_rel = cur.getString(34);
            daughter_milk_rel = cur.getString(35);
            daughter_calv_int = cur.getString(36);
            daughter_calv_int_rel = cur.getString(37);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //salary.setText("nope");
        } finally {
            db.endTransaction();
           // System.out.println("Database of cow");
        }

        textView23 = (TextView) findViewById(R.id.textView23);
        textView25 = (TextView) findViewById(R.id.textView25);
        textView27 = (TextView) findViewById(R.id.textView27);
        textView29 = (TextView) findViewById(R.id.textView29);
        textView34 = (TextView) findViewById(R.id.textView34);
        textView92 = (TextView) findViewById(R.id.textView92);
        textView94 = (TextView) findViewById(R.id.textView94);
        textView96 = (TextView) findViewById(R.id.textView96);
        textView98 = (TextView) findViewById(R.id.textView98);


        textView23.setText(jumbo);
        textView25.setText(num);
        textView27.setText(sex);
        textView29.setText(dam);
        textView34.setText(replaceStar);
        textView92.setText(replacement);
        textView94.setText(terminal);


        double repStar;

        repStar = Double.parseDouble(replaceStar);
        ratingBar2.setRating((float) repStar);
        double conStar;
        if(carcassConformStar.equals("")){
            conStar =0;
        }
        else
            conStar= Double.parseDouble(carcassConformStar);
        ratingBar11.setRating((float) conStar);
        String name;

        String animalName;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        type = bundle.getString("1");         //table type
        name = bundle.getString("2");         //column name
        animalName = bundle.getString("3");          //animal name

        System.out.println(type);
        System.out.println(name);
        System.out.println(animalName);

        db2 = openOrCreateDatabase(path, MODE_PRIVATE, null);

        db2.beginTransaction();
        if (type.equals("BullsTerminal")){
            try {

                Cursor cur = db2.rawQuery("SELECT * FROM " + type + " WHERE " + name + "='" + animalName+"'", null);
                System.out.println("*****************************Arrived*******************************************");
                cur.moveToFirst();

                Rank = cur.getString(1);
                Code = cur.getString(2);
                BullName = cur.getString(3);
                Breed = cur.getString(4);
                BullIndex = cur.getString(5);
                StarsWithin = cur.getString(7);
                StarsAcross = cur.getString(8);
                Gest =cur.getString(11);
                Supplier = cur.getString(21);
                db2.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);

            } finally {
                db2.endTransaction();
                System.out.println("Data base bulls done");
            }
        }else if(type.equals("BullsMaternal")) {
            try {
                Cursor cur = db2.rawQuery("SELECT * FROM " + type + " WHERE " + name + "='" + animalName+"'", null);

                cur.moveToFirst();

                Rank = cur.getString(1);
                Code = cur.getString(2);
                BullName = cur.getString(3);
                Breed = cur.getString(4);
                BullIndex = cur.getString(5);
                StarsWithin = cur.getString(7);
                StarsAcross = cur.getString(8);
                Gest =cur.getString(11);
                Supplier = cur.getString(21);
                db2.setTransactionSuccessful();
            } catch (SQLException e) {

            } finally {
                db2.endTransaction();
            }
        }

        System.out.println(Rank);
        System.out.println(Code);
        System.out.println(BullName);
        System.out.println(Breed);
        textView40 = (TextView) findViewById(R.id.textView40);
        textView41 = (TextView) findViewById(R.id.textView41);
        textView42 = (TextView) findViewById(R.id.textView42);
        textView43 = (TextView) findViewById(R.id.textView43);
        textView40.setText(Rank);
        textView41.setText(Code);
        textView42.setText(BullName);
        textView43.setText(Breed);
        textView96.setText(BullIndex);
        textView98.setText(Gest);

        double ownStar;
        double awayStar;
        System.out.println(StarsWithin);
        ownStar = Double.parseDouble(StarsWithin);              //ownStar/StarsWithin equals sire star
        ratingBar3.setRating((int) ownStar);
        awayStar = Double.parseDouble(StarsAcross);
        ratingBar4.setRating((int)awayStar);
        double star;

        //Calf star algorithm
        if(termStar.equals("")) {
            star = 0;
        }
        else
            star = Double.parseDouble(termStar);    //star/termStar equals dam tars

        CalfStar = (ownStar + star)/2;

        if(replaceStar.equals("")){
            repStar = 0;
        }
        else
            repStar= Double.parseDouble(replaceStar);

        CalfAcrossStar = (repStar + awayStar)/2;

    }


    public void mate(View view) {
        logger.log(Level.INFO, "button works!");

        Intent intent = new Intent(BullPicked.this, Mating.class);
        Bundle bundle = new Bundle();
        bundle.putString("1",jumbo);        //dam jumbo
        bundle.putString("2",num);          //dam ID
        bundle.putString("3",BullName);     //Sire name
        bundle.putString("4",Code);         //Bull code
        bundle.putDouble("5",CalfStar);
        bundle.putDouble("6",CalfAcrossStar);
        bundle.putString("7",BullIndex);
        bundle.putString("8",replacement);
        bundle.putString("9",type);
        bundle.putString("10",terminal);
        bundle.putString("11",Supplier);

        intent.putExtras(bundle);
        startActivity(intent);

        Log.d("BullSearch", "button works!");
    }
    public void back(View view) {
        logger.log(Level.INFO, "button works!");
        finish();
    }
}


