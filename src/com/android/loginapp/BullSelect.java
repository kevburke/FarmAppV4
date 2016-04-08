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
public class BullSelect extends Activity {
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
    TextView textView20;
    TextView textView21;
    String type;
    String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullsel);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        String [] listElements = new String[80];
        String[] details = new String[5];
        details[0] = bundle.getString("1");         //type
        details[1] = bundle.getString("2");         //Breed
        details[2] = bundle.getString("3");         //Ratings
        details[3] = bundle.getString("4");         //ratings across
        details[4] = bundle.getString("5");         //Gestation

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
        System.out.println("**************"+details[0]+" "+details[1]+" "+details[2]+" "+details[3]);
        String path = "ICBF";
        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);

       // bullAdapter.add("adfsghfgjh");
        System.out.println("at the query *********");
        db.beginTransaction();
        //if (details[0].equals("Terminal")) {                                                         //+" AND "+ "TStarsWithin="+ details[2]
            try {
                Cursor cur = db.rawQuery("SELECT * FROM "+ type +" WHERE Tbreed='" + details[1]+"' AND TStarsWithin='"+ details[2]+"' AND TStarsAcross='"+ details[3]+"'",null);
                cur.moveToFirst();
                int ii=0;
                while(! cur.isLast()) {
                    bullAdapter.add(cur.getString(3));
                    listElements[ii++]=cur.getString(3);
                    cur.moveToNext();
                }
                bullAdapter.add(cur.getString(3));
                listElements[ii]=cur.getString(3);
                db.setTransactionSuccessful();


            } catch (SQLException e) {
                //salary.setText("nope");
            } finally {
                db.endTransaction();
            }

        listView.setAdapter(bullAdapter);
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
                    Cursor cur =  db2.rawQuery("SELECT * FROM "+type+" WHERE "+name+"='" + listElements[position]+"'", null);
                    cur.moveToFirst();
                    BullName = cur.getString(3);
                    StarsWithin = cur.getString(7);
                    CarcassWeight = cur.getString(15);
                    CarcassConform = cur.getString(17);
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

                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);


                ((TextView)popupWindow.getContentView().findViewById(R.id.textView18)).setText(BullName);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView19)).setText(StarsWithin);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView20)).setText(CarcassWeight);
                ((TextView)popupWindow.getContentView().findViewById(R.id.textView21)).setText(CarcassConform);

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

                        Intent in = new Intent(BullSelect.this, BullPicked.class);

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