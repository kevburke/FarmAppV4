package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.ofix.barcode.R;

/**
 * Created by Kev on 15/04/2016.
 */
public class Finish extends Activity {
    public static final String MY_PREFS = "SharedPreferences";
    SQLiteDatabase db;
    String jumbo;
    String jumbo1;
    String numID;
    String numID1;
    String BullName;
    String BullName1;
    String Code;
    String Code1;
    String MateDate;
    String MateDate1;
    String Dob;
    String Dob1;
    String Tblname;
    TextView textView46;
    TextView textView48;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        jumbo = bundle.getString("1");         //Dam jumbo
        numID = bundle.getString("2");         //dam ID
        BullName = bundle.getString("3");       //Bullname
        Code = bundle.getString("4");           //Bulls Jumbo
        MateDate = bundle.getString("5");       //mating date
        Dob =bundle.getString("6");             //date of birth
        SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        String name = prefs.getString("username", "");
        Tblname = "Calf"+name ;
        System.out.println(Tblname);
        textView46 = (TextView) findViewById(R.id.textView46);
        textView48 = (TextView) findViewById(R.id.textView48);
        String path = "ICBF";
        db = this.openOrCreateDatabase(path,MODE_PRIVATE, null);
        SQLiteDatabase db = this.openOrCreateDatabase("ICBF", MODE_PRIVATE, null);
        addDataBaseTable();
        System.out.println(jumbo);
        textView46.setText(jumbo+" "+ numID+" "+BullName+" "+Code+" "+MateDate+" "+Dob);
        insertData();
        db = this.openOrCreateDatabase(path,MODE_PRIVATE, null);
        db.beginTransaction();

        try{
            Cursor cur =  db.rawQuery("SELECT * FROM "+Tblname+" WHERE jumbo="+jumbo, null);
            cur.moveToFirst();

            jumbo1= cur.getString(1);
            numID1 =cur.getString(2);
            BullName1 = cur.getString(3);
            Code1 = cur.getString(4);
            MateDate1 =cur.getString(5);
            Dob1 = cur.getString(6);
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Toast.makeText(getBaseContext(), "DataBase Exception ", Toast.LENGTH_LONG).show();

        }finally {
            db.endTransaction();
            Toast.makeText(getBaseContext(), "DataBase Done", Toast.LENGTH_LONG).show();
        }
        textView48.setText(jumbo1=" "+ numID1+" "+BullName1+" "+Code1+" "+MateDate1+" "+Dob1);
    }
    private void addDataBaseTable() {
        System.out.println("inside database function..........");
        db.execSQL("DROP TABLE IF EXISTS '"+Tblname+"'");
        //create table
        db.beginTransaction();
        System.out.println("inside database beginTransaction..........");
        try {
            //db.execSQL("DROP TABLE IF EXISTS '"+Tblname+"'");
            //create table
            System.out.println("inside database try..........");
            db.execSQL("create table "+Tblname+"("
                    + " recID integer PRIMARY KEY autoincrement, "
                    + " jumbo  text, "
                    + " numID  text, "
                    + " BullName  text, "
                    + " Code  text, "
                    + " MateDate  text, "
                    + " Dob  text"
                    + ");  ");

            //commit your changes
            db.setTransactionSuccessful();

        } catch (SQLException e1) {
            System.out.println("inside database Catch..........");
        }
        finally {
            db.endTransaction();
            Toast.makeText(getBaseContext(), "DataBase Table Created", Toast.LENGTH_LONG).show();
        }

    }
    private void insertData() {
        System.out.println("inside database insert function..........");
        db.beginTransaction();
        System.out.println("inside database insert beginTransaction..........");
        try {
            System.out.println("inside database insert try..........");
                db.execSQL( "insert into "+Tblname+"(jumbo, numID, BullName, Code, MateDate, Dob) "
                        + " values ('"+jumbo+
                        "' , '"+numID+
                        "' , '"+BullName+
                        "' , '"+Code+
                        "' , '"+MateDate+
                        "' , '"+Dob+ "');" );

            //commit your changes
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e2) {
            System.out.println("inside database Catch..........");
        }
        finally {
            db.endTransaction();
            Toast.makeText(getBaseContext(), "DataBase insert Done", Toast.LENGTH_LONG).show();
        }
    }


}