package com.android.loginapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.ofix.barcode.R;

/**
 * Created by Kev on 17/04/2016.
 */
public class NotificationReturn extends Activity {

    SQLiteDatabase db;
    String jumbo;
    String jumbo1;
    String numID1;
    String BullName1;
    String Code1;
    String MateDate1;
    String Dob1;
    String Tblname;
    TextView textView61;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationreturn);
        textView61 = (TextView) findViewById(R.id.textView61);
              //Dam jumbo
        db = this.openOrCreateDatabase("ICBF",MODE_PRIVATE, null);
        db.beginTransaction();

        try{
            Cursor cur =  db.rawQuery("SELECT * FROM "+Tblname, null);
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
        textView61.setText(jumbo1=" "+ numID1+" "+BullName1+" "+Code1+" "+MateDate1+" "+Dob1);
    }
}