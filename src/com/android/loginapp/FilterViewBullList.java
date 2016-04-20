package com.android.loginapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.ofix.barcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kev on 04/04/2016.
 */
public class FilterViewBullList extends Activity {
    SQLiteDatabase db;
    SQLiteDatabase db2;
    TextView textView17;
    List<String> TBullName = new ArrayList<String>();
    String BullName;
    String StarsWithin;
    String CarcassWeight;
    String CarcassConform;
    TextView textView18;
    TextView textView19;
    TextView textView92;
    TextView textView94;
    String type;
    String name;
    String Code;
    String Breed;
    String Index;
    String StarsAcross;
    String Price;
    String Supplier;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullsel);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        String[] listElements = new String[80];
        String[] details = new String[6];
        details[0] = bundle.getString("1");         //type
        details[1] = bundle.getString("2");         //Breed
        details[2] = bundle.getString("3");         //Ratings
        details[3] = bundle.getString("4");         //ratings across
        details[4] = bundle.getString("5");         //Supplier
        details[5] = bundle.getString("6");         //code

        TextView textView17 = (TextView) findViewById(R.id.textView17);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter bullAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        textView17.setText("Filtered by: " + details[0] + "\n" + details[1] + "\n" + details[2] + "\n" + details[3] + "\n" +
                details[4]);

        if(details[0].equals("Terminal")) {
            type = "BullsTerminal";
            name = "TBullName";
        }
        else {
            type = "BullsMaternal";
            name = "MBullName";
        }

        System.out.println("**************" + details[0] + " " + details[1] + " " + details[2] + " " + details[3]);
        String path = "ICBF";
        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);

        // bullAdapter.add("adfsghfgjh");
        System.out.println("at the query *********");
        db.beginTransaction();
        if (type.equals("BullsTerminal")) {                                                         //+" AND "+ "TStarsWithin="+ details[2]
            try {
                String sql = "";
                sql += "SELECT * FROM " + type + "";
                if (!details[1].equals("ANY"))
                    sql += " WHERE Tbreed='" + details[1] + "'";
                if (!details[2].equals("ANY"))
                    sql += " AND TStarsWithin='" + details[2] + "'";
                if (!details[3].equals("ANY"))
                    sql += " AND TStarsAcross='" + details[3] + "'";
                //    if(!details[4].equals("ANY"))
                //        sql+=" AND TStarsWithin='"+ details[2]+"'";
                if (!details[4].equals("ANY"))
                    sql += " AND TSupplier='" + details[4] + "'";

                //Cursor cur = db.rawQuery("SELECT * FROM "+ type +" WHERE Tbreed='" + details[1]+"' AND TStarsWithin>='"+ details[2]+"' AND TStarsAcross>='"+ details[3]+"'",null);
                Cursor cur = db.rawQuery(sql, null);
                // if(cur !=null) {
                if (cur.moveToFirst()) {


                    int ii = 0;
                    while (!cur.isLast()) {

                        bullAdapter.add(cur.getString(3));
                        listElements[ii++] = cur.getString(3);
                        cur.moveToNext();
                    }
                    bullAdapter.add(cur.getString(3));
                    listElements[ii] = cur.getString(3);

                    db.setTransactionSuccessful();
                } else {
                    db.setTransactionSuccessful();
                    Toast.makeText(getApplicationContext(),
                            "This selection is not available.",
                            Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(FilterViewBullList.this, ViewBullList.class);
                    startActivity(in);
                }

            } catch (SQLException e) {
                System.out.println("***********In Catch**********");


            } finally {
                db.endTransaction();
            }
        }
        else if (type.equals("BullsMaternal"))
        {
            try {
                String sql = "";
                sql += "SELECT * FROM " + type + "";
                if (!details[1].equals("ANY"))
                    sql += " WHERE Mbreed='" + details[1] + "'";
                if (!details[2].equals("ANY"))
                    sql += " AND MStarsWithin='" + details[2] + "'";
                if (!details[3].equals("ANY"))
                    sql += " AND MStarsAcross='" + details[3] + "'";
                //    if(!details[4].equals("ANY"))
                //        sql+=" AND TStarsWithin='"+ details[2]+"'";
                if (!details[4].equals("ANY"))
                    sql += " AND MSupplier='" + details[4] + "'";

                //Cursor cur = db.rawQuery("SELECT * FROM "+ type +" WHERE Tbreed='" + details[1]+"' AND TStarsWithin>='"+ details[2]+"' AND TStarsAcross>='"+ details[3]+"'",null);
                Cursor cur = db.rawQuery(sql, null);
                // if(cur !=null) {
                if (cur.moveToFirst()) {


                    int ii = 0;
                    while (!cur.isLast()) {

                        bullAdapter.add(cur.getString(3));
                        listElements[ii++] = cur.getString(3);
                        cur.moveToNext();
                    }
                    bullAdapter.add(cur.getString(3));
                    listElements[ii] = cur.getString(3);

                    db.setTransactionSuccessful();
                } else {
                    db.setTransactionSuccessful();
                    Toast.makeText(getApplicationContext(),
                            "This selection is not available.",
                            Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(FilterViewBullList.this, ViewBullList.class);
                    startActivity(in);
                }

            } catch (SQLException e) {
                System.out.println("***********In Catch**********");


            } finally {
                db.endTransaction();
            }
        }

















//        String sql ="";
//
//        sql += "SELECT * FROM "+ type+"";
//
//        if(!details[1].equals("ANY"))
//            sql+=" WHERE Tbreed='" + details[1]+"'";
//        if(!details[2].equals("ANY"))
//            sql+=" AND TStarsWithin='"+ details[2]+"'";
//        if(!details[3].equals("ANY"))
//            sql+=" AND TStarsAcross='"+ details[3]+"'";
//        //    if(!details[4].equals("ANY"))
//        //        sql+=" AND TStarsWithin='"+ details[2]+"'";
//        //    if(!details[5].equals("ANY"))
//        //        sql+=" AND TStarsWithin='"+ details[2]+"'";
//
//
//        System.out.println("**************"+details[0]+" "+details[1]+" "+details[2]+" "+details[3]);
//        String path = "ICBF";
//        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);
//
//        // bullAdapter.add("adfsghfgjh");
//        System.out.println("at the query *********");
//        db.beginTransaction();
//        //if (details[0].equals("Terminal")) {                                                         //+" AND "+ "TStarsWithin="+ details[2]
//        try {
//            //Cursor cur = db.rawQuery("SELECT * FROM "+ type +" WHERE Tbreed='" + details[1]+"' AND TStarsWithin>='"+ details[2]+"' AND TStarsAcross>='"+ details[3]+"'",null);
//            Cursor cur = db.rawQuery(sql,null);
//            // if(cur !=null) {
//            if(cur.moveToFirst()) {
//
//
//                int ii = 0;
//                while (!cur.isLast()) {
//
//                    bullAdapter.add(cur.getString(3));
//                    listElements[ii++] = cur.getString(3);
//                    cur.moveToNext();
//                }
//                bullAdapter.add(cur.getString(3));
//                listElements[ii] = cur.getString(3);
//
//                db.setTransactionSuccessful();
//            }else{
//                db.setTransactionSuccessful();
//                Toast.makeText(getApplicationContext(),
//                        "This selection is not available.",
//                        Toast.LENGTH_SHORT).show();
//                Intent in = new Intent(FilterViewBullList.this, ViewBullList.class);
//                startActivity(in);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("***********In Catch**********");
//
//
//        } finally {
//            db.endTransaction();
//        }
//
        listView.setAdapter(bullAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup2, null);
                db2 = openOrCreateDatabase("ICBF",MODE_PRIVATE, null);

                db2.beginTransaction();
                System.out.println("***************"+position+"***********************");
                try{                                     //type=BllsTerminal name=TBullname
                    Cursor cur =  db2.rawQuery("SELECT * FROM "+type+" WHERE "+name+"='" + listElements[position]+"'", null);
                    cur.moveToFirst();
                    Code = cur.getString(2);
                    BullName = cur.getString(3);
                    Breed = cur.getString(4);
                    Index = cur.getString(5);
                    StarsWithin = cur.getString(7);
                    StarsAcross =cur.getString(8);
                    CarcassWeight = cur.getString(15);
                    CarcassConform = cur.getString(17);
                    Price = cur.getString(20);
                    Supplier = cur.getString(21);
                    db2.setTransactionSuccessful();
                    System.out.println("****************Data recieved************************");
                    System.out.println("*******************************"+BullName+"****************");
                    System.out.println(StarsWithin);
                    System.out.println(CarcassWeight);
                    System.out.println("********************************"+CarcassConform+"**********************");
                }catch (SQLException e){
                    //salary.setText("nope");
                }finally {
                    db2.endTransaction();
                }

                double ownStar;
                //double awayStar;
                double star;
                if(StarsAcross.equals("")) {
                    star = 0;
                }
                else
                    star = Double.parseDouble(StarsAcross);    //star/termStar equals dam tars

                //System.out.println(StarsWithin);
                ownStar = Double.parseDouble(StarsWithin);              //ownStar/StarsWithin equals sire star
                //ratingBar5.setRating((int) ownStar);
                //awayStar = Double.parseDouble(termStar);
                //ratingBar6.setRating((int)awayStar);

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);

                ((TextView)popupWindow.getContentView().findViewById(R.id.textView18)).setText(Code);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView19)).setText(BullName);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView20)).setText(Breed);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView21)).setText(Index);
                ((RatingBar)popupWindow.getContentView().findViewById(R.id.ratingBar7)).setRating((float)ownStar);
                ((RatingBar)popupWindow.getContentView().findViewById(R.id.ratingBar8)).setRating((float) star);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView73)).setText(CarcassWeight);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView71)).setText(Price);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView69)).setText(Supplier);

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

                        Intent in = new Intent(FilterViewBullList.this, Helloworld.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("1",type);
                        bundle.putString("2",name);
                        bundle.putString("3",BullName);
                        in.putExtras(bundle);
                        startActivity(in);
                    }
                });
                // popupWindow.showAsDropDown(view, 100, 50);
                popupWindow.showAtLocation(view,80,50,50);
            }}
        );
    }
}




