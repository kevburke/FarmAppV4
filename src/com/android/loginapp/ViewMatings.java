package com.android.loginapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.ofix.barcode.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Kev on 22/04/2016.
 */
public class ViewMatings extends Activity {

    String jumbo;
    String numID;
    String BullName;
    String Code;
    String MateDate;
    String Dob;
    String table;

    SQLiteDatabase db;
    SQLiteDatabase db2;
    SQLiteDatabase db3;

    public static final String MY_PREFS = "SharedPreferences";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmatings);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        table = prefs.getString("username", "");
        String path = "ICBF";
        db = this.openOrCreateDatabase(path, MODE_PRIVATE, null);


        ArrayAdapter calfAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1);
        ListView listView3 = (ListView) findViewById(R.id.listView3);
        String[] listElements = new String[80];
        db.beginTransaction();
        int ii = 0;
        try {
            Cursor cur = db.rawQuery("SELECT * FROM Calf" + table, null);// put all animal id's in arrayList
            if (cur.moveToFirst()) {
                while (!cur.isLast()) {
                    numID = cur.getString(2);
                    calfAdapter.add(cur.getString(2));
                    listElements[ii++] = cur.getString(2);
                    cur.moveToNext();
                }
                calfAdapter.add(cur.getString(2));
                listElements[ii++] = cur.getString(2);
                db.setTransactionSuccessful();

                //jumbo = cur.getString(1);
                // BullName = cur.getString(3);
                // Code = cur.getString(4);
                // MateDate = cur.getString(5);
                //  Dob = cur.getString(6);
            }

        } catch (SQLException e) {
            //salary.setText("nope");
        } finally {
            db.endTransaction();
            // System.out.println("Database of cow");
        }

        listView3.setAdapter(calfAdapter);
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LayoutInflater layoutInflater
                        = (LayoutInflater) getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup3, null);
                db2 = openOrCreateDatabase("ICBF", MODE_PRIVATE, null);

                db2.beginTransaction();
                System.out.println("***************" + position + "***********************");
                try {                                    //all from Calf table where id is
                    Cursor cur = db2.rawQuery("SELECT * FROM Calf" + table + " WHERE numID='" + listElements[position] + "'", null);
                   // Cursor cur = db2.rawQuery("SELECT * FROM Calf" + table + " WHERE numID='" +numID+ "'", null);

                    cur.moveToFirst();
                    jumbo = cur.getString(1);
                    numID = cur.getString(2);
                    BullName = cur.getString(3);
                    Code = cur.getString(4);
                    MateDate = cur.getString(5);
                    Dob = cur.getString(6);
                    db2.setTransactionSuccessful();
                    System.out.println("****************Data recieved************************");

                } catch (SQLException e) {

                } finally {
                    db2.endTransaction();
                }


                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);

                ((TextView) popupWindow.getContentView().findViewById(R.id.textView109)).setText(jumbo);
                ((TextView) popupWindow.getContentView().findViewById(R.id.textView110)).setText(numID);
                ((TextView) popupWindow.getContentView().findViewById(R.id.textView111)).setText(BullName);
                ((TextView) popupWindow.getContentView().findViewById(R.id.textView112)).setText(Code);
                ((TextView) popupWindow.getContentView().findViewById(R.id.textView113)).setText(MateDate);
                ((TextView) popupWindow.getContentView().findViewById(R.id.textView114)).setText(Dob);

                Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();

                    }
                });

                Button delete = (Button) popupView.findViewById(R.id.delete);
                delete.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        db3 = openOrCreateDatabase("ICBF", MODE_PRIVATE, null);
                        db3.beginTransaction();
                        try {                                     //type=BllsTerminal name=TBullname
                            Cursor cur = db3.rawQuery("DELETE FROM Calf" + table + " WHERE numID ='" + numID + "'", null);
                            db3.setTransactionSuccessful();
                            Toast.makeText(getBaseContext(), "delete done " + numID, Toast.LENGTH_LONG).show();
                        } catch (SQLException e) {
                            Toast.makeText(getBaseContext(), "delete failed " + numID, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        } finally {
                            db3.endTransaction();
                        }



                        JSONObject json = new JSONObject();
                        Log.d("BullSearch", "JSON CREAATED");
                        try {
                            json.put("user",table);
                            json.put("delete", numID);
                            String baseUrl = "http://10.12.11.250:8080/UpdateDatabase";
                            new HttpAsyncTask().execute(baseUrl, json.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent in = new Intent(ViewMatings.this, Helloworld.class);
                        startActivity(in);
                    }
                });

                // popupWindow.showAsDropDown(view, 100, 50);
                popupWindow.showAtLocation(view, 80, 50, 50);
            }

        });
    }
        private class HttpAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... urls) {

                String jsonString = "";

                try {
                    jsonString = HttpUtils.urlContentPost(urls[0], "Update", urls[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(jsonString);
                return jsonString;
            }//do in background
            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(String result) {
                JSONObject jsonResult = null;

               // Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
            }//on post execute
        }//http async task

}