package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.ofix.barcode.R;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by Kev on 29/03/2016.
 */
public class BullSearch extends Activity implements AdapterView.OnItemSelectedListener {
    private static final Logger logger = Logger.getLogger("logger");
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private String Type = "*";
    private String Breed ="*";
    private String Ratings ="*";
    private String CalvingRating ="*";
    private String Gestation ="*";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bullsearch);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 =(Spinner) findViewById(R.id.spinner2);
        Spinner spinner3 =(Spinner) findViewById(R.id.spinner3);
        Spinner spinner4 =(Spinner) findViewById(R.id.spinner4);
        Spinner spinner5 =(Spinner) findViewById(R.id.spinner5);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);
        spinner5.setOnItemSelectedListener(this);

        // Spinner1 Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Type");
        categories.add("Terminal");
        categories.add("Replacement");


        // Spinner2 Drop down elements
        List<String> categories2 = new ArrayList<String>();
        categories2.add("Breed");
        categories2.add("AUNGUS");
        categories2.add("AUBRAC");
        categories2.add("BLONDE D’AQUITANE");
        categories2.add("BELIGUM BLUE");
        categories2.add("CHAROLAIS");
        categories2.add("HEREFORD");
        categories2.add("LIMOUSIN");
        categories2.add("PARTHENAISE");
        categories2.add("SALERS");
        categories2.add("SHORTHORN");
        categories2.add("SIMMENTAL");
        categories2.add("WAGYU");

        // Spinner3 Drop down elements
        List<String> categories3 = new ArrayList<String>();
        categories3.add("Ratings");
        categories3.add("1 Star");
        categories3.add("2 Stars");
        categories3.add("3 Stars");
        categories3.add("4 Stars");
        categories3.add("5 Stars");

        // Spinner4 Drop down elements
        List<String> categories4 = new ArrayList<String>();
        categories4.add("Calving Rating");
        categories4.add("1 Star");
        categories4.add("2 Stars");
        categories4.add("3 Stars");
        categories4.add("4 Stars");
        categories4.add("5 Stars");

        // Spinner5 Drop down elements
        List<String> categories5 = new ArrayList<String>();
        categories5.add("Gestation");
        categories5.add("1 Star");
        categories5.add("2 Stars");
        categories5.add("3 Stars");
        categories5.add("4 Stars");
        categories5.add("5 Stars");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories3);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories4);
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories5);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);
        spinner3.setAdapter(dataAdapter3);
        spinner4.setAdapter(dataAdapter4);
        spinner5.setAdapter(dataAdapter5);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       Spinner spinner = (Spinner) parent;

        String S = parent.getItemAtPosition(position).toString();
        //String Breed = parent.getItemAtPosition(position).toString();
        //String Rating = parent.getItemAtPosition(position).toString();
        //String CalvDiff = parent.getItemAtPosition(position).toString();
        //System.out.println("******************"+ Type+"******************");
        //System.out.println("******************"+ Breed+"******************");
        //System.out.println("******************"+Rating+"******************");
        //System.out.println("******************"+CalvDiff+"******************");

        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton5 = (RadioButton) findViewById(R.id.radioButton5);

        //if(parent.getItemAtPosition(position).toString()=="Type") {
            if (spinner.getId() == R.id.spinner) {
                if(S == "Terminal" || S == "Replacement") {
                    Type = S;
                    System.out.println("***************** Im In d loop**********************");
                    if (!(radioButton.isChecked())) {
                        System.out.println("***************** Toggle **********************");
                        radioButton.toggle();
                    }
                }
                else if(S=="Type"){
                    radioButton.setChecked(false);
                }
            }
        //}
       // if(parent.getItemAtPosition(position).toString()=="Breed") {
            if (spinner.getId()==R.id.spinner2) {
                if(S == "AUNGUS" || S == "AUBRAC" || S == "BLONDE D’AQUITANE"
                        || S == "BELIGUM BLUE" || S == "CHAROLAIS"|| S == "HEREFORD"
                        || S == "LIMOUSIN"|| S == "PARTHENAISE"|| S == "SALERS"
                        || S == "SHORTHORN"|| S == "SIMMENTAL"|| S == "WAGYU") {
                    String myBreed =S;
                    if(myBreed=="AUNGUS")
                        Breed = "AA";
                    if(myBreed=="AUBRAC")
                        Breed = "AU";
                    if(myBreed=="BLONDE D’AQUITANE")
                        Breed = "BA";
                    if(myBreed=="BELIGUM BLUE")
                        Breed = "BB";
                    if(myBreed=="CHAROLAIS")
                        Breed = "CH";
                    if(myBreed=="HEREFORD")
                        Breed = "HE";
                    if(myBreed=="LIMOUSIN")
                        Breed = "LM";
                    if(myBreed=="PARTHENAISE")
                        Breed = "AA";
                    if(myBreed=="SAILOR")
                        Breed = "PT";
                    if(myBreed=="SALERS")
                        Breed = "SA";
                    if(myBreed=="SHORTHORN")
                        Breed = "SH";
                    if(myBreed=="SIMMENTAL")
                        Breed = "SI";
                    if(myBreed=="WAGYU")
                        Breed = "WA";

                if (!(radioButton2.isChecked())) {
                    System.out.println("***************** Toggle 2**********************");
                    radioButton2.toggle();
                }
            }
        }
        if (spinner.getId()==R.id.spinner3) {
            if(S == "1 Star" || S == "2 Stars" || S == "3 Stars"
                    || S == "4 Stars" || S == "5 Stars"){
                Ratings = S;
                if (!(radioButton3.isChecked()))
                    radioButton3.toggle();
            }
        }
        if (spinner.getId()==R.id.spinner4) {
            if(S == "1 Star" || S == "2 Stars" || S == "3 Stars"
                    || S == "4 Stars" || S == "5 Stars"){
                CalvingRating = S;
                if (!(radioButton4.isChecked()))
                    radioButton4.toggle();
            }
        }
        if (spinner.getId()==R.id.spinner5) {
            if(S == "1 Star" || S == "2 Stars" || S == "3 Stars"
                    || S == "4 Stars" || S == "5 Stars"){
                Gestation =S;
                if (!(radioButton5.isChecked()))
                    radioButton5.toggle();
            }
        }


//        if(Rating != "Ratings"){
//            if (!(radioButton3.isChecked()))
//                radioButton3.toggle();
//        }
//        if(CalvDiff != "Calving Rating"){
//            if (!(radioButton4.isChecked()))
//                radioButton4.toggle();
//        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
     public void Refine(View view) {
        logger.log(Level.INFO, "button works!");
         if(Type=="Type"){
             Toast.makeText(getBaseContext(), "Select Type", Toast.LENGTH_LONG).show();
         }
         else {

             Intent intent = new Intent(BullSearch.this, BullSelect.class);
             Bundle bundle = new Bundle();

             bundle.putString("1", Type);
             bundle.putString("2", Breed);
             bundle.putString("3", Ratings);
             bundle.putString("4", CalvingRating);
             bundle.putString("5", Gestation);
             intent.putExtras(bundle);
             startActivity(intent);

             Log.d("test", "button works!");
         }
    }

}