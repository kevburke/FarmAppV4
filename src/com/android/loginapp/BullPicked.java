package com.android.loginapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
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
public class BullPicked extends Activity implements DateDialog.TheListener {
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
    String TRank;
    String MRank;
    String TCode;
    String MCode;
    String TBullName;
    String MBullName;
    String TBreed;
    String MBreed;
    String TBullIndex;
    String MBullIndex;
    String TStarsWithin;
    String MStarsWithin;
    String TStarsAcross;
    String MStarsAcross;



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
    TextView textView49;

    private static final Logger logger = Logger.getLogger("logger");
    public TextView textView16;
    public static final String MY_PREFS = "SharedPreferences";
    private RatingBar ratingBar2;
    private RatingBar ratingBar3;
    private RatingBar ratingBar4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullpicked);
        textView16 = (TextView) findViewById(R.id.textView16);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String barcode = preferences.getString("Cow", "");
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        String table = prefs.getString("username", "");

        ratingBar3 = (RatingBar)findViewById(R.id.ratingBar3);
        ratingBar4 = (RatingBar)findViewById(R.id.ratingBar4);
        String path = "ICBF";
        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);

        //SQLiteDatabase db = this.openOrCreateDatabase("ICBF", MODE_PRIVATE, null);

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
            System.out.println("Database of cow");
        }
        System.out.println("*************************"+jumbo+"*********************");
        System.out.println("*************************"+docileStar+"*********************");
        textView23 = (TextView) findViewById(R.id.textView23);
        textView25 = (TextView) findViewById(R.id.textView25);
        textView27 = (TextView) findViewById(R.id.textView27);
        textView29 = (TextView) findViewById(R.id.textView29);
        textView34 = (TextView) findViewById(R.id.textView34);


        textView23.setText(jumbo);
        textView25.setText(num);
        textView27.setText(sex);
        textView29.setText(dam);
        textView34.setText(replaceStar);
        int repStar;

        repStar = Integer.parseInt(replaceStar);
        ratingBar2.setRating(repStar);
        String name;
        String type;
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
        //SQLiteDatabase db2 = this.openOrCreateDatabase("ICBF", MODE_PRIVATE, null);
        db2.beginTransaction();
        if (type.equals("BullsTerminal")){
            try {

                Cursor cur = db2.rawQuery("SELECT * FROM " + type + " WHERE " + name + "='" + animalName+"'", null);
                System.out.println("*****************************Arrived*******************************************");
                cur.moveToFirst();

                TRank = cur.getString(1);
                TCode = cur.getString(2);
                TBullName = cur.getString(3);
                TBreed = cur.getString(4);
                TBullIndex = cur.getString(5);
                TStarsWithin = cur.getString(7);
                TStarsAcross = cur.getString(8);
                db2.setTransactionSuccessful();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);

            } finally {
                db2.endTransaction();
                System.out.println("Data base bulls done");
            }
        }else if(type == "BullsMaternal") {
            System.out.println("*************************never here**************************************");
            try {
                Cursor cur = db.rawQuery("SELECT * FROM " + type + " WHERE " + name + "=" + animalName, null);
                cur.moveToFirst();

                MRank = cur.getString(1);
                MCode = cur.getString(2);
                MBullName = cur.getString(3);
                MBreed = cur.getString(4);
                MBullIndex = cur.getString(5);
                MStarsWithin = cur.getString(7);
                MStarsAcross = cur.getString(8);
                db2.setTransactionSuccessful();
            } catch (SQLException e) {
                //salary.setText("nope");
            } finally {
                db2.endTransaction();
            }
        }

        System.out.println(TRank);
        System.out.println(TCode);
        System.out.println(TBullName);
        System.out.println(TBreed);
        textView40 = (TextView) findViewById(R.id.textView40);
        textView41 = (TextView) findViewById(R.id.textView41);
        textView42 = (TextView) findViewById(R.id.textView42);
        textView43 = (TextView) findViewById(R.id.textView43);
        textView49 = (TextView) findViewById(R.id.textView49);

        textView40.setText(TRank);
        textView41.setText(TCode);
        textView42.setText(TBullName);
        textView43.setText(TBreed);

        double ownStar;
        double awayStar;
        System.out.println(TStarsWithin);
        ownStar = Double.parseDouble(TStarsWithin);
        ratingBar3.setRating((int) ownStar);
        awayStar = Double.parseDouble(TStarsAcross);
        ratingBar4.setRating((int)awayStar);



    }



    public void mate(View view) {
        logger.log(Level.INFO, "button works!");

        Intent intent = new Intent(BullPicked.this, DateDialog.class);
        startActivity(intent);

        Log.d("BullSearch", "button works!");
    }


    public void SetDate(View v) {
        DialogFragment picker = new DateDialog();
        picker.show(getFragmentManager(), "datePicker");


    }
    @Override
    public void returnDate(String date) {
        // TODO Auto-generated method stub
        System.out.println("****************************************In function return*************");
        textView49.setText(date);
    }

}


