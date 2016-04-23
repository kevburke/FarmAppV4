package com.android.loginapp;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
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
    public static final String MY_PREFS = "SharedPreferences";
    TextView textView49;
    private RatingBar ratingBar7;
    private RatingBar ratingBar8;
    TextView textView52;
    TextView textView54;
    TextView textView56;
    TextView textView99;
    TextView textView101;
    SQLiteDatabase db;
    long epoch;
    String str;
    String numID;
    String jumbo;
    String BullName;
    String Code;
    String datein;
    Button Confirm;
    Button Contact;
    String BullIndex;
    String  replacement;
    String terminal;
    String type;
    String supplier;
    double calfRep=0;
    String calfRep2;
    String num;
    String mess;
    String txtDate;
    String table;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mating);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
        table = prefs.getString("username", "");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Confirm = (Button) findViewById(R.id.Confirm);
        Contact = (Button) findViewById(R.id.Contact);
        ratingBar7 = (RatingBar) findViewById(R.id.ratingBar7);
        ratingBar8 = (RatingBar) findViewById(R.id.ratingBar8);
        double CalfStar;
        double  CalfAcrossStar;
        jumbo = bundle.getString("1");          //Dam jumbo
        numID = bundle.getString("2");          //dam ID
        BullName = bundle.getString("3");       //Bullname
        Code = bundle.getString("4");           //Bulls Jumbo
        CalfStar = bundle.getDouble("5");       //Calf star rating calculeated previously
        CalfAcrossStar =bundle.getDouble("6");  //Calf star rating as a across calculated previously
        BullIndex = bundle.getString("7");
        replacement =bundle.getString("8");
        type = bundle.getString("9");
        terminal = bundle.getString("10");
        supplier = bundle.getString("11");



        if(supplier.equals("Limousin Soc"))
            num = "0868206051";
        if(supplier.equals("Bova"))
            num = "0868206051";
        if(supplier.equals("Sligo AI"))
            num = "0876530516";
        if(supplier.equals("Powerful Genetics"))
            num = "0868206051";
        if(supplier.equals("NCBC"))
            num = "0868206051";
        if(supplier.equals("Dovea"))
            num = "0868206051";
        if(supplier.equals("Eurogene"))
            num = "0868206051";
        if(supplier.equals("Parthenais Soc"))
            num = "0868206051";
        if(supplier.equals("Charolais Soc"))
            num = "0868206051";
        if(supplier.equals("NCBC/Dovea"))
            num = "0868206051";
        if(supplier.equals("Celtic Sires"))
            num = "0868206051";
        if(supplier.equals("Hereford Soc"))
            num = "0868206051";

        Confirm.setEnabled(false);
        Contact.setEnabled(false);
        ratingBar7.setRating((int) CalfStar);
        ratingBar8.setRating((float) CalfAcrossStar);
        textView49 = (TextView) findViewById(R.id.textView49);
        textView52 = (TextView) findViewById(R.id.textView52);
        textView54 = (TextView) findViewById(R.id.textView54);
        textView56 = (TextView) findViewById(R.id.textView56);
        textView99 = (TextView) findViewById(R.id.textView99);
        textView101 = (TextView) findViewById(R.id.textView101);
        textView54.setText(jumbo);
        textView56.setText(BullName);

        if(type.equals("BullsMaternal")){
            double rep;
            double ind;

            rep = Double.parseDouble(replacement);
            ind = Double.parseDouble(BullIndex);
            System.out.println("**************************"+type+"*************************************");
            System.out.println("****************************"+rep+ "***********************************");
            System.out.println("****************************"+ind+ "***********************************");
            calfRep = (rep + ind)/2;
            calfRep2 = String.valueOf(calfRep);
            System.out.println(calfRep2+"***********************************"+calfRep);
            textView99.setText(calfRep2);
        }
        else if(type.equals("BullsTerminal")){
            double ter;
            double ind1;
            double calfter;
            ter = Double.parseDouble(terminal);
            ind1 = Double.parseDouble(BullIndex);
            calfter = (ter + ind1)/2;

            textView101.setText(String.valueOf(calfter));
        }
        addDataBaseTable();

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
        Contact.setEnabled(true);
        datein = date;
        txtDate =date;
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
    public void back(View view) {
        finish();
    }

    public void Contact(View view){
        Confirm.setEnabled(true);
        mess = "Please service "+numID+" with "+BullName+ " id "+ Code +" on "+txtDate + ". From  "+table+".";
        sendSMS(num, mess);
        Toast.makeText(getApplicationContext(), "Your Message is sent to "+supplier , Toast.LENGTH_LONG).show();
        insertData();
    }
    public void Confirm(View view) {


        handler.postDelayed(task, 10000);
       // Toast.makeText(this, "notification will post in 10 seconds", Toast.LENGTH_SHORT).show();
        handler2.postDelayed(task2, 30000);
       // Toast.makeText(this, "notification will post in 30 seconds", Toast.LENGTH_SHORT).show();
        handler3.postDelayed(task3, 60000);
       // Toast.makeText(this, "notification will post in 60 seconds", Toast.LENGTH_SHORT).show();
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
            style.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.notification));
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
            style.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.notification));
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
            style.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.notification));
            Notification note = builder.build();
            manager.notify(NOTE_ID3, note);
        }
    };

        public void sendSMS(String phoneNumber, String message) {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);
        }
    private void addDataBaseTable() {
        db = openOrCreateDatabase("ICBF", MODE_PRIVATE, null);

        db.beginTransaction();
        try {
           // db.execSQL("CREATE TABLE IF NOT EXISTS '"+"Calf"+table+"'");
            //create table

            db.execSQL("CREATE TABLE IF NOT EXISTS '"+"Calf"+table+"("
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

        }
        finally {
            db.endTransaction();
        }

    }
    private void insertData() {

        db.beginTransaction();
        try {

            //insert rows
                db.execSQL( "insert into Calf"+table+"(jumbo, numID, BullName, Code, MateDate, Dob) "
                        + " values ('"+jumbo+
                        "' , '"+numID+
                        "' , '"+BullName+
                        "' , '"+Code+
                        "' , '"+datein+
                        "' , '"+str+"');" );

            //commit your changes
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e2) {
        }
        finally {
            db.endTransaction();
            Toast.makeText(getBaseContext(), "Calf DataBase Done", Toast.LENGTH_LONG).show();
        }

    }
}