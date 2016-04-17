package com.android.loginapp;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.ofix.barcode.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Kev on 12/04/2016.
 */
public class Mating extends Activity implements DateDialog.TheListener{

    private static final int NOTE_ID = 100,NOTE_ID2 =101,NOTE_ID3 =102;
    TextView textView49;
    private RatingBar ratingBar7;
    private RatingBar ratingBar8;
    TextView textView52;
    long epoch;
    String str;
    String numID;
    String jumbo;
    String BullName;
    String Code;
    String datein;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mating);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ratingBar7 = (RatingBar) findViewById(R.id.ratingBar7);
        ratingBar8 = (RatingBar) findViewById(R.id.ratingBar8);
        double CalfStar;
        double  CalfAcrossStar;
        jumbo = bundle.getString("1");         //Dam jumbo
        numID = bundle.getString("2");         //dam ID
        BullName = bundle.getString("3");       //Bullname
        Code = bundle.getString("4");           //Bulls Jumbo
        CalfStar = bundle.getDouble("5");       //Calf star rating calculeated previously
        CalfAcrossStar =bundle.getDouble("6");  //Calf star rating as a across calculated previously



        ratingBar7.setRating((int) CalfStar);
        ratingBar8.setRating((float) CalfAcrossStar);
        textView49 = (TextView) findViewById(R.id.textView49);
        textView52 = (TextView) findViewById(R.id.textView52);
    }
    public void SetDate(View v) {
        DialogFragment picker = new DateDialog();
        picker.show(getFragmentManager(), "datePicker");


    }
    @Override
    public void returnDate(String date) {       //come comes back dd-mm-yyyy
        // TODO Auto-generated method stub
        System.out.println("********************************In function return***************************");
        textView49.setText(date);       //put in textviev
        datein = date;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date datef = null;
        try {
            datef = df.parse(datein);       //parse datein to datef
        } catch (ParseException e) {
            e.printStackTrace();
        }
        epoch = datef.getTime();                                //epoch gets epoch value of date selected
        System.out.println(epoch);



        long nineMonth = 23328000;                              //nine months seconds
        long dueDate = epoch + (nineMonth*1000);                //change to milliseconds
        //System.out.println("***********************"+dueDate);

        Date theDate = new Date(dueDate);                       //Calander api
        Calendar cal = new GregorianCalendar();
        cal.setTime(theDate);
        SimpleDateFormat dfss = new SimpleDateFormat("dd-MM-yyyy");//format date
        str = dfss.format(theDate);
        //System.out.println(str);
        textView52.setText(str);                                    //send to text view

        //System.out.println("Epoch representation of this date is: " + epoch);
    }
    public void Confirm(View view) {


        handler.postDelayed(task, 10000);
        Toast.makeText(this, "Notification will post in 10 seconds", Toast.LENGTH_SHORT).show();
        handler2.postDelayed(task2, 15000);
        Toast.makeText(this, "Notification will post in 15 seconds", Toast.LENGTH_SHORT).show();
        handler3.postDelayed(task3, 30000);
        Toast.makeText(this, "Notification will post in 30 seconds", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Mating.this, Finish.class);
            Bundle bundle = new Bundle();

            bundle.putString("1", jumbo);           //dams jumbo
            bundle.putString("2", numID);           //Dams Id
            bundle.putString("3", BullName);        //Bulls Name
            bundle.putString("4", Code);            //Bulls Id
            bundle.putString("5",datein);           //date of mating
            bundle.putString("6",str);              //calves date of Birth
            intent.putExtras(bundle);
            startActivity(intent);
    }
    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Intent launchIntent = new Intent(getApplicationContext(), NotificationReturn.class);
            Bundle bundle = new Bundle();

            bundle.putString("1", jumbo);           //dams jumbo

            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, 0);
            Notification.Builder builder = new Notification.Builder(Mating.this);
            //Set notification information
            builder.setSmallIcon(R.drawable.cowicon)
                    .setTicker("your cow needs attention")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle(jumbo+ " Check cow")
                    .setContentText("Go to app")
                    .setContentIntent(contentIntent)
                    .addExtras(bundle);

            Notification.BigPictureStyle style= new Notification.BigPictureStyle(builder);
            style.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.cowicon));
            Notification note = builder.build();
            manager.notify(NOTE_ID, note);
        }
    };
    private Handler handler2 = new Handler();
    private Runnable task2 = new Runnable() {
        @Override
        public void run() {
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Intent launchIntent = new Intent(getApplicationContext(), NotificationReturn.class);
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, 0);
            Notification.Builder builder = new Notification.Builder(Mating.this);
            //Set notification information
            builder.setSmallIcon(R.drawable.cowicon)
                    .setTicker("Two")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle(jumbo+ " Check cow again")
                    .setContentText("Go to app")
                    .setContentIntent(contentIntent);

            Notification.BigPictureStyle style= new Notification.BigPictureStyle(builder);
            style.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.cowicon));
            Notification note = builder.build();
            manager.notify(NOTE_ID2, note);
        }
    };
    private Handler handler3 = new Handler();
    private Runnable task3 = new Runnable() {
        @Override
        public void run() {
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Intent launchIntent = new Intent(getApplicationContext(), NotificationReturn.class);

            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder builder = new Notification.Builder(Mating.this);
            //Set notification information
            builder.setSmallIcon(R.drawable.cowicon)
                    .setTicker("Three")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle(jumbo+ " Due date approaching")
                    .setContentText("Go to app")
                    .setContentIntent(contentIntent);

            Notification.BigPictureStyle style= new Notification.BigPictureStyle(builder);
            style.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.cowicon));
            Notification note = builder.build();
            manager.notify(NOTE_ID3, note);
        }
    };
}