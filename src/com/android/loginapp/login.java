package com.android.loginapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.database.DatabaseAdapter;
import com.ofix.barcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The main application activity which serves as a login page.
 *
 *
 */

public class login extends Activity {

	public static final String MY_PREFS = "SharedPreferences";
	private DatabaseAdapter dbHelper;
	private EditText theUsername;
	private EditText thePassword;
	private Button loginButton;
	private Button registerButton;
	private Button clearButton;
	private Button exitButton;
	private CheckBox rememberDetails;
	String outUser ="";
	String outPass = "";
	String Tblname ="";
	SQLiteDatabase db;
	StringBuilder stringBuilder;
	String[] jumbo;
	String[] num ;
	String[] sex ;
	String[] dob ;
	String[] name ;
	String[] status ;
	String[] breed ;
	String[] dam ;
	String[] sire ;
	String[] replacement;
	String[] replacement_maternal ;
	String[] terminal ;
	String[] replacement_maternal_prog ;
	String[] dairy ;
	String[] calving_diff;
	String[] trait_reliability;
	String[] replaceStar;
	String[] termStar;
	String[] dairyStar;
	String[] docileStar;
	String[] carcassWeighStar;
	String[] carcassConformStar;
	String[] daughterMilkStar;
	String[] daughterCalvIntStar;
	String[] replacement_index;
	String[] docility_index;
	String[] docility_reliability;
	String[] carcassWeiIndx;
	String[] carcassWeightRel;
	String[] daughter_Calving_Diff;
	String[] daughter_Milk_index;
	String[] daughter_milk_rel;
	String[] carcass_conform_index;
	String[] carcass_conform_rel;
	String[] daughter_calving_rel;
	String[] daughter_calv_int;
	String[] daughter_calv_int_rel;
	String[] MRank;
	String[] MCode;
	String[] MBullName;
	String[] MBreed;
	String[] MIndex;
	String[] MRel1;
	String[] MStarsWithin;
	String[] MStarsAcross;
	String[] MCalvDiff;
	String[] MRel2;
	String[] MGest;
	String[] MRel3;
	String[] MDocility;
	String[] MRel4;
	String[] MCarcassWeightkgs;
	String[] MRel5;
	String[] MCarcassConf;
	String[] MRel6;
	String[] MAvail;
	String[] MPrice;
	String[] MSupplier;
	String[] TRank;
	String[] TCode;
	String[] TBullName;
	String[] TBreed;
	String[] TIndex;
	String[] TRel1;
	String[] TStarsWithin;
	String[] TStarsAcross;
	String[] TCalvDiff;
	String[] TRel2;
	String[] TGest;
	String[] TRel3;
	String[] TDocility;
	String[] TRel4;
	String[] TCarcassWeightkgs;
	String[] TRel5;
	String[] TCarcassConf;
	String[] TRel6;
	String[] TAvail;
	String[] TPrice;
	String[] TSupplier;
	/**
	 * Called when the activity is first created.
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences mySharedPreferences = getSharedPreferences(MY_PREFS, 0);
		Editor editor = mySharedPreferences.edit();
		editor.putLong("uid", 0);
		editor.commit();

		dbHelper = new DatabaseAdapter(this);
		dbHelper.open();
		String path = "ICBF";
		db = this.openOrCreateDatabase(path,MODE_PRIVATE, null);
		setContentView(R.layout.main);
		initControls();
	}



	private void initControls() {
		//Set the activity layout.

		theUsername = (EditText) findViewById(R.id.Username);
		thePassword = (EditText) findViewById(R.id.Password);
		loginButton = (Button) findViewById(R.id.Login);
		registerButton = (Button) findViewById(R.id.Register);
		clearButton = (Button) findViewById(R.id.Clear);
		exitButton = (Button) findViewById(R.id.Exit);
		rememberDetails = (CheckBox) findViewById(R.id.RememberMe);



		//Create touch listeners for all buttons.
		loginButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				LogMeIn(v);
			}
		});

		registerButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Register(v);
			}
		});

		clearButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				ClearForm();
			}
		});

		exitButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Exit();
			}
		});
		//Create remember password check box listener.
		rememberDetails.setOnClickListener(new CheckBox.OnClickListener() {
			public void onClick(View v) {
				RememberMe();
			}
		});

		//Handle remember password preferences.
		SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
		String thisUsername = prefs.getString("username", "");
		String thisPassword = prefs.getString("password", "");
		boolean thisRemember = prefs.getBoolean("remember", false);
		if (thisRemember) {
			theUsername.setText(thisUsername);
			thePassword.setText(thisPassword);
			rememberDetails.setChecked(thisRemember);
		}

	}

	/**
	 * Deals with Exit option - exits the application.
	 */
	private void Exit() {
		finish();
	}

	/**
	 * Clears the login form.
	 */
	private void ClearForm() {
		saveLoggedInUId(0, "", "");
		theUsername.setText("");
		thePassword.setText("");
	}

	/**
	 * Handles the remember password option.
	 */
	private void RememberMe() {
		boolean thisRemember = rememberDetails.isChecked();
		SharedPreferences prefs = getSharedPreferences(MY_PREFS, 0);
		Editor editor = prefs.edit();
		editor.putBoolean("remember", thisRemember);
		editor.commit();
	}

	/**
	 * This method handles the user login process.
	 * And runs Async task to send credentials to server for authentication on remote server
	 * @param v
	 */
	private void LogMeIn(View v) {
		//Get the username and password
		String thisUsername = theUsername.getText().toString();
		String thisPassword = thePassword.getText().toString();

		outUser = thisUsername;
		outPass = thisPassword;
		Tblname = thisUsername;
		JSONObject json = new JSONObject();
		try {
			json.put("username",outUser);
			json.put("password",outPass);

			//String baseUrl = "http://10.12.11.250:8080/InputOutput";
			String baseUrl = "http://192.168.1.4:8080/InputOutput";
			new HttpAsyncTask().execute(baseUrl, json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println("*******************"+ thisUsername + "*********************"+ thisPassword);
		System.out.println("Im after logging in .................@@@@@@@@@@@@");
		//Assign the hash to the password
		thisPassword = md5(thisPassword);                   //send down to encrypt username to be saved on db

		// Check the existing user name and password database
		Cursor theUser = dbHelper.fetchUser(thisUsername, thisPassword);
		if (theUser != null) {
			startManagingCursor(theUser);
			if (theUser.getCount() > 0) {
				//  saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(DatabaseAdapter.COL_ID)), thisUsername, thePassword.getText().toString());
				saveLoggedInUId(theUser.getLong(theUser.getColumnIndex(DatabaseAdapter.COL_ID)), thisUsername, thisPassword);
				stopManagingCursor(theUser);
				theUser.close();
				Intent i = new Intent(v.getContext(), Helloworld.class);
				startActivity(i);
			}

			//Returns appropriate message if no match is made
			else {
				Toast.makeText(getApplicationContext(),
						"You have entered an incorrect username or password.",
						Toast.LENGTH_SHORT).show();
				saveLoggedInUId(0, "", "");
			}
			stopManagingCursor(theUser);
			theUser.close();
		} else {
			Toast.makeText(getApplicationContext(),
					"Database query error",
					Toast.LENGTH_SHORT).show();
		}
	}
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			String jsonString = "";

			try {
				jsonString = HttpUtils.urlContentPost(urls[0], "login", urls[1]);
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
			try {
				jsonResult = new JSONObject(result);
				JSONArray Jinjumbo = jsonResult.getJSONArray("jumbo");
				JSONArray Jinnum = jsonResult.getJSONArray("num");
				JSONArray Jinsex = jsonResult.getJSONArray("sex");
				JSONArray Jindob = jsonResult.getJSONArray("dob");
				JSONArray Jinname = jsonResult.getJSONArray("name");
				JSONArray Jinstatus = jsonResult.getJSONArray("status");
				JSONArray Jinbreed = jsonResult.getJSONArray("breed");
				JSONArray Jindam = jsonResult.getJSONArray("dam");
				JSONArray Jinsire = jsonResult.getJSONArray("sire");
				JSONArray Jinreplacement = jsonResult.getJSONArray("replacement");
				JSONArray Jinreplacement_maternal = jsonResult.getJSONArray("replacement_maternal");
				JSONArray Jinterminal = jsonResult.getJSONArray("terminal");
				JSONArray Jinreplacement_maternal_prog = jsonResult.getJSONArray("replacement_maternal_prog");
				JSONArray Jindairy = jsonResult.getJSONArray("dairy");
				JSONArray Jincalving_diff = jsonResult.getJSONArray("calving_diff");
				JSONArray Jintrait_reliability = jsonResult.getJSONArray("trait_reliability");
				JSONArray Jinreplacement_index = jsonResult.getJSONArray("replacement_index");
				JSONArray JinreplaceStar = jsonResult.getJSONArray("replaceStar");
				JSONArray JintermStar = jsonResult.getJSONArray("termStar");
				JSONArray JindairyStar = jsonResult.getJSONArray("dairyStar");
				JSONArray JindocileStar = jsonResult.getJSONArray("docileStar");
				JSONArray JincarcassWeighStar = jsonResult.getJSONArray("carcassWeighStar");
				JSONArray JincarcassWeiIndx = jsonResult.getJSONArray("carcassWeiIndx");
				JSONArray JincarcassWeightRel = jsonResult.getJSONArray("carcassWeightRel");
				JSONArray JincarcassConformStar = jsonResult.getJSONArray("carcassConformStar");
				JSONArray JindaughterMilkStar = jsonResult.getJSONArray("daughterMilkStar");
				JSONArray JindaughterCalvIntStar = jsonResult.getJSONArray("daughterCalvIntStar");
				JSONArray Jindocility_index = jsonResult.getJSONArray("docility_index");
				JSONArray Jindocility_reliability = jsonResult.getJSONArray("docility_reliability");
				JSONArray Jindaughter_Calving_Diff = jsonResult.getJSONArray("daughter_Calving_Diff");
				JSONArray Jindaughter_calving_rel = jsonResult.getJSONArray("daughter_calving_rel");
				JSONArray Jindaughter_Milk_index = jsonResult.getJSONArray("daughter_Milk_index");
				JSONArray Jincarcass_conform_index = jsonResult.getJSONArray("carcass_conform_index");
				JSONArray Jincarcass_conform_rel = jsonResult.getJSONArray("carcass_conform_rel");
				JSONArray Jindaughter_milk_rel = jsonResult.getJSONArray("daughter_milk_rel");
				JSONArray Jindaughter_calv_int = jsonResult.getJSONArray("daughter_calv_int");
				JSONArray Jindaughter_calv_int_rel = jsonResult.getJSONArray("daughter_calv_int_rel");
				JSONArray  JinMRank= jsonResult.getJSONArray("MRank");
				JSONArray  JinMCode= jsonResult.getJSONArray("MCode");
				JSONArray  JinMBullName= jsonResult.getJSONArray("MBullName");
				JSONArray  JinMBreed= jsonResult.getJSONArray("MBreed");
				JSONArray  JinMIndex= jsonResult.getJSONArray("MIndex");
				JSONArray  JinMRel1= jsonResult.getJSONArray("MRel1");
				JSONArray  JinMStarsWithin= jsonResult.getJSONArray("MStarsWithin");
				JSONArray  JinMStarsAcross= jsonResult.getJSONArray("MStarsAcross");
				JSONArray  JinMCalvDiff= jsonResult.getJSONArray("MCalvDiff");
				JSONArray  JinMRel2= jsonResult.getJSONArray("MRel2");
				JSONArray  JinMGest= jsonResult.getJSONArray("MGest");
				JSONArray  JinMRel3= jsonResult.getJSONArray("MRel3");
				JSONArray  JinMDocility= jsonResult.getJSONArray("MDocility");
				JSONArray  JinMRel4= jsonResult.getJSONArray("MRel4");
				JSONArray  JinMCarcassWeightkgs= jsonResult.getJSONArray("MCarcassWeightkgs");
				JSONArray  JinMRel5= jsonResult.getJSONArray("MRel5");
				JSONArray  JinMCarcassConf= jsonResult.getJSONArray("MCarcassConf");
				JSONArray  JinMRel6= jsonResult.getJSONArray("MRel6");
				JSONArray  JinMAvail= jsonResult.getJSONArray("MAvail");
				JSONArray  JinMPrice= jsonResult.getJSONArray("MPrice");
				JSONArray  JinMSupplier= jsonResult.getJSONArray("MSupplier");
				JSONArray  JinTRank= jsonResult.getJSONArray("TRank");
				JSONArray  JinTCode= jsonResult.getJSONArray("TCode");
				JSONArray  JinTBullName= jsonResult.getJSONArray("TBullName");
				JSONArray  JinTBreed= jsonResult.getJSONArray("TBreed");
				JSONArray  JinTIndex= jsonResult.getJSONArray("TIndex");
				JSONArray  JinTRel1= jsonResult.getJSONArray("TRel1");
				JSONArray  JinTStarsWithin= jsonResult.getJSONArray("TStarsWithin");
				JSONArray  JinTStarsAcross= jsonResult.getJSONArray("TStarsAcross");
				JSONArray  JinTCalvDiff= jsonResult.getJSONArray("TCalvDiff");
				JSONArray  JinTRel2= jsonResult.getJSONArray("TRel2");
				JSONArray  JinTGest= jsonResult.getJSONArray("TGest");
				JSONArray  JinTRel3= jsonResult.getJSONArray("TRel3");
				JSONArray  JinTDocility= jsonResult.getJSONArray("TDocility");
				JSONArray  JinTRel4= jsonResult.getJSONArray("TRel4");
				JSONArray  JinTCarcassWeightkgs= jsonResult.getJSONArray("TCarcassWeightkgs");
				JSONArray  JinTRel5= jsonResult.getJSONArray("TRel5");
				JSONArray  JinTCarcassConf= jsonResult.getJSONArray("TCarcassConf");
				JSONArray  JinTRel6= jsonResult.getJSONArray("TRel6");
				JSONArray  JinTAvail= jsonResult.getJSONArray("TAvail");
				JSONArray  JinTPrice= jsonResult.getJSONArray("TPrice");
				JSONArray  JinTSupplier= jsonResult.getJSONArray("TSupplier");

				jumbo = new String[Jinjumbo.length()];
				num = new String[Jinjumbo.length()];
				sex = new String[Jinjumbo.length()];
				dob = new String[Jinjumbo.length()];
				name = new String[Jinjumbo.length()];
				status = new String[Jinjumbo.length()];
				breed = new String[Jinjumbo.length()];
				dam = new String[Jinjumbo.length()];
				sire = new String[Jinjumbo.length()];
				replacement = new String[Jinjumbo.length()];
				replacement_maternal = new String[Jinjumbo.length()];
				terminal = new String[Jinjumbo.length()];
				replacement_maternal_prog = new String[Jinjumbo.length()];
				dairy = new String[Jinjumbo.length()];
				calving_diff = new String[Jinjumbo.length()];
				trait_reliability = new String[Jinjumbo.length()];
				replacement_index = new String[Jinjumbo.length()];
				replaceStar = new String[Jinjumbo.length()];
				termStar = new String[Jinjumbo.length()];
				dairyStar = new String[Jinjumbo.length()];
				docileStar = new String[Jinjumbo.length()];
				carcassWeighStar = new String[Jinjumbo.length()];
				carcassWeiIndx = new String[Jinjumbo.length()];
				carcassWeightRel = new String[Jinjumbo.length()];
				carcassConformStar = new String[Jinjumbo.length()];
				daughterMilkStar = new String[Jinjumbo.length()];
				daughterCalvIntStar = new String[Jinjumbo.length()];
				docility_index = new String[Jinjumbo.length()];
				docility_reliability = new String[Jinjumbo.length()];
				daughter_Calving_Diff = new String[Jinjumbo.length()];
				daughter_calving_rel = new String[Jinjumbo.length()];
				daughter_Milk_index = new String[Jinjumbo.length()];
				carcass_conform_index = new String[Jinjumbo.length()];
				carcass_conform_rel = new String[Jinjumbo.length()];
				daughter_milk_rel = new String[Jinjumbo.length()];
				daughter_calv_int = new String[Jinjumbo.length()];
				daughter_calv_int_rel = new String[Jinjumbo.length()];
				MRank =new String[JinMRank.length()];
				MCode =new String[JinMRank.length()];
				MBullName =new String[JinMRank.length()];
				MBreed =new String[JinMRank.length()];
				MIndex =new String[JinMRank.length()];
				MRel1 =new String[JinMRank.length()];
				MStarsWithin =new String[JinMRank.length()];
				MStarsAcross =new String[JinMRank.length()];
				MCalvDiff =new String[JinMRank.length()];
				MRel2 =new String[JinMRank.length()];
				MGest =new String[JinMRank.length()];
				MRel3 =new String[JinMRank.length()];
				MDocility =new String[JinMRank.length()];
				MRel4 =new String[JinMRank.length()];
				MCarcassWeightkgs =new String[JinMRank.length()];
				MRel5 =new String[JinMRank.length()];
				MCarcassConf =new String[JinMRank.length()];
				MRel6 =new String[JinMRank.length()];
				MAvail =new String[JinMRank.length()];
				MPrice =new String[JinMRank.length()];
				MSupplier =new String[JinMRank.length()];
				TRank =new String[JinTRank.length()];
				TCode =new String[JinTRank.length()];
				TBullName =new String[JinTRank.length()];
				TBreed =new String[JinTRank.length()];
				TIndex =new String[JinTRank.length()];
				TRel1 =new String[JinTRank.length()];
				TStarsWithin =new String[JinTRank.length()];
				TStarsAcross =new String[JinTRank.length()];
				TCalvDiff =new String[JinTRank.length()];
				TRel2 =new String[JinTRank.length()];
				TGest =new String[JinTRank.length()];
				TRel3 =new String[JinTRank.length()];
				TDocility =new String[JinTRank.length()];
				TRel4 =new String[JinTRank.length()];
				TCarcassWeightkgs =new String[JinTRank.length()];
				TRel5 =new String[JinTRank.length()];
				TCarcassConf =new String[JinTRank.length()];
				TRel6 =new String[JinTRank.length()];
				TAvail =new String[JinTRank.length()];
				TPrice =new String[JinTRank.length()];
				TSupplier =new String[JinTRank.length()];

				for (int i = 0; i < Jinjumbo.length() ; i++) {
					if(Jinname.getString(i).length()<3){
						name[i] = "No Name";
					}
					else
						name[i] = Jinname.getString(i);

					if(Jinsire.getString(i).length()<3){
						sire[i] = "No Name";
					}
					else
						sire[i] = Jinsire.getString(i);

					if(Jindairy.getString(i).length()<3){
						dairy[i] = "No Name";
					}
					else
						dairy[i] = Jindairy.getString(i);

					jumbo[i] = Jinjumbo.getString(i);
					num[i] = Jinnum.getString(i);
					sex[i] = Jinsex.getString(i);
					dob[i] = Jindob.getString(i);
					status[i] = Jinstatus.getString(i);
					breed[i] = Jinbreed.getString(i);
					dam [i] = Jindam.getString(i);
					replacement[i] = Jinreplacement.getString(i);
					replacement_maternal[i] = Jinreplacement_maternal.getString(i);
					terminal[i] = Jinterminal.getString(i);
					replacement_maternal_prog[i] = Jinreplacement_maternal_prog.getString(i);
					calving_diff[i] = Jincalving_diff.getString(i);
					trait_reliability[i] = Jintrait_reliability.getString(i);
					replacement_index[i] = Jinreplacement_index.getString(i);
					replaceStar[i] = JinreplaceStar.getString(i);
					termStar[i] = JintermStar.getString(i);
					dairyStar[i] = JindairyStar.getString(i);
					docileStar[i] = JindocileStar.getString(i);
					carcassWeighStar[i] = JincarcassWeighStar.getString(i);
					carcassWeiIndx[i] = JincarcassWeiIndx.getString(i);
					carcassWeightRel[i] = JincarcassWeightRel.getString(i);
					carcassConformStar[i] = JincarcassConformStar.getString(i);
					daughterMilkStar[i] = JindaughterMilkStar.getString(i);
					daughterCalvIntStar[i] = JindaughterCalvIntStar.getString(i);
					docility_index[i] = Jindocility_index.getString(i);
					docility_reliability[i] = Jindocility_reliability.getString(i);
					daughter_Calving_Diff[i] = Jindaughter_Calving_Diff.getString(i);
					daughter_calving_rel[i] = Jindaughter_calving_rel.getString(i);
					daughter_Milk_index[i] = Jindaughter_Milk_index.getString(i);
					carcass_conform_index[i] = Jincarcass_conform_index.getString(i);
					carcass_conform_rel[i] = Jincarcass_conform_rel.getString(i);
					daughter_milk_rel[i] = Jindaughter_milk_rel.getString(i);
					daughter_calv_int[i] = Jindaughter_calv_int.getString(i);
					daughter_calv_int_rel[i] = Jindaughter_calv_int_rel.getString(i);

				}
				for (int i = 0; i < JinMRank.length() ; i++) {

					MRank[i] = JinMRank.getString(i);
					MCode[i] = JinMCode.getString(i);
					MBullName[i] = JinMBullName.getString(i);
					MBreed[i] = JinMBreed.getString(i);
					MIndex[i] = JinMIndex.getString(i);
					MRel1[i] = JinMRel1.getString(i);
					MStarsWithin[i] = JinMStarsWithin.getString(i);
					MStarsAcross[i] = JinMStarsAcross.getString(i);
					MCalvDiff[i] = JinMCalvDiff.getString(i);
					MRel2[i] = JinMRel2.getString(i);
					MGest[i] = JinMGest.getString(i);
					MRel3[i] = JinMRel3.getString(i);
					MDocility[i] = JinMDocility.getString(i);
					MRel4[i] = JinMRel4.getString(i);
					MCarcassWeightkgs[i] = JinMCarcassWeightkgs.getString(i);
					MRel5[i] = JinMRel5.getString(i);
					MCarcassConf[i] = JinMCarcassConf.getString(i);
					MRel6[i] = JinMRel6.getString(i);
					MAvail[i] = JinMAvail.getString(i);
					MPrice[i] = JinMPrice.getString(i);
					MSupplier[i] = JinMSupplier.getString(i);


				}
				for (int i = 0; i < JinTRank.length() ; i++) {
					TRank[i] = JinTRank.getString(i);
					TCode[i] = JinTCode.getString(i);
					TBullName[i] = JinTBullName.getString(i);
					TBreed[i] = JinTBreed.getString(i);
					TIndex[i] = JinTIndex.getString(i);
					TRel1[i] = JinTRel1.getString(i);
					TStarsWithin[i] = JinTStarsWithin.getString(i);
					TStarsAcross[i] = JinTStarsAcross.getString(i);
					TCalvDiff[i] = JinTCalvDiff.getString(i);
					TRel2[i] = JinTRel2.getString(i);
					TGest[i] = JinTGest.getString(i);
					TRel3[i] = JinTRel3.getString(i);
					TDocility[i] = JinTDocility.getString(i);
					TRel4[i] = JinTRel4.getString(i);
					TCarcassWeightkgs[i] = JinTCarcassWeightkgs.getString(i);
					TRel5[i] = JinTRel5.getString(i);
					TCarcassConf[i] = JinTCarcassConf.getString(i);
					TRel6[i] = JinTRel6.getString(i);
					TAvail[i] = JinTAvail.getString(i);
					TPrice[i] = JinTPrice.getString(i);
					TSupplier[i] = JinTSupplier.getString(i);
				}

				addDataBaseTable();
				//System.out.println("Add Table TBulls");
				addDataBaseTableT();
				//System.out.println("Add Table MBulls");
				addDataBaseTableM();
				//System.out.println("insertData IE1313426");
				insertData();
				//System.out.println("insertData TBulls");
				insertDataT();
				//System.out.println("insertData MBulls");
				insertDataM();

			} catch (JSONException e) {
				e.printStackTrace();
			}

			Toast.makeText(getBaseContext(), "Data In!", Toast.LENGTH_LONG).show();
		}//on post execute
	}//http async task
	/**
	 * Open the Registration activity.
	 *
	 * @param v
	 */
	private void Register(View v) {
		Intent i = new Intent(v.getContext(), Register.class);
		startActivity(i);
	}

	private void saveLoggedInUId(long id, String username, String password) {
		SharedPreferences settings = getSharedPreferences(MY_PREFS, 0);
		Editor myEditor = settings.edit();
		myEditor.putLong("uid", id);
		myEditor.putString("username", username);
		myEditor.putString("password", password);
		boolean rememberThis = rememberDetails.isChecked();
		myEditor.putBoolean("rememberThis", rememberThis);
		myEditor.commit();
	}

	/**
	 * Deals with the password encryption.
	 *
	 * @param s The password.
	 * @return
	 */
	private String md5(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			Toast.makeText(getApplicationContext(),
					hexString.toString(),
					Toast.LENGTH_LONG).show();
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return s;
		}
	}

	/**
	 * Deals with inserting returned data from users herd
	 */

	private void insertData() {

		db.beginTransaction();
		try {

			//insert rows
			for(int i = 0; i < jumbo.length;i++)
			{
				db.execSQL( "insert into "+Tblname+"(jumbo, num, sex, dob, name, status, breed, dam, sire, replacement, replacement_maternal, terminal, replacement_maternal_prog, dairy, calving_diff, trait_reliability, replacement_index, replaceStar, termStar, dairyStar, docileStar, carcassWeighStar, carcassWeiIndx, carcassWeightRel, carcassConformStar, daughterMilkStar, daughterCalvIntStar, docility_index, docility_reliability, daughter_Calving_Diff, daughter_calving_rel, daughter_Milk_index, carcass_conform_index, carcass_conform_rel, daughter_milk_rel, daughter_calv_int, daughter_calv_int_rel) "
						+ " values ('"+jumbo[i]+
						"' , '"+num[i]+
						"' , '"+sex[i]+
						"' , '"+dob[i]+
						"' , '"+name[i]+
						"' , '"+status[i]+
						"' , '"+breed[i]+
						"' , '"+dam[i]+
						"' , '"+sire[i]+
						"' , '"+replacement[i]+
						"' , '"+replacement_maternal[i]+
						"' , '"+terminal[i]+
						"' , '"+replacement_maternal_prog[i]+
						"' , '"+dairy[i]+
						"' , '"+calving_diff[i]+
						"' , '"+trait_reliability[i]+
						"' , '"+replacement_index[i]+
						"' , '"+replaceStar[i]+
						"' , '"+termStar[i]+
						"' , '"+dairyStar[i]+
						"' , '"+docileStar[i]+
						"' , '"+carcassWeighStar[i]+
						"' , '"+carcassWeiIndx[i]+
						"' , '"+carcassWeightRel[i]+
						"' , '"+carcassConformStar[i]+
						"' , '"+daughterMilkStar[i]+
						"' , '"+daughterCalvIntStar[i]+
						"' , '"+docility_index[i]+
						"' , '"+docility_reliability[i]+
						"' , '"+daughter_Calving_Diff[i]+
						"' , '"+daughter_calving_rel[i]+
						"' , '"+daughter_Milk_index[i]+
						"' , '"+carcass_conform_index[i]+
						"' , '"+carcass_conform_rel[i]+
						"' , '"+daughter_milk_rel[i]+
						"' , '"+daughter_calv_int[i]+
						"' , '"+daughter_calv_int_rel[i]+"');" );
			}

			//commit your changes
			db.setTransactionSuccessful();

		}
		catch (SQLiteException e2) {
		}
		finally {
			db.endTransaction();
			Toast.makeText(getBaseContext(), "DataBase Done", Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * Deals with creating tables for herd of user
	 */

	private void addDataBaseTable() {

		db.beginTransaction();
		try {
			db.execSQL("DROP TABLE IF EXISTS '"+Tblname+"'");
			//create table

			db.execSQL("create table "+Tblname+"("
					+ " recID integer PRIMARY KEY autoincrement, "
					+ " jumbo  text, "
					+ " num  text, "
					+ " sex  text, "
					+ " dob  text, "
					+ " name  text, "
					+ " status  text, "
					+ " breed  text, "
					+ " dam  text, "
					+ " sire  text, "
					+ " replacement  text, "
					+ " replacement_maternal  text, "
					+ " terminal  text, "
					+ " replacement_maternal_prog  text, "
					+ " dairy  text, "
					+ " calving_diff  text, "
					+ " trait_reliability  text, "
					+ " replacement_index  text, "
					+ " replaceStar  text, "
					+ " termStar  text, "
					+ " dairyStar  text, "
					+ " docileStar  text, "
					+ " carcassWeighStar  text, "
					+ " carcassWeiIndx  text, "
					+ " carcassWeightRel  text, "
					+ " carcassConformStar  text, "
					+ " daughterMilkStar  text, "
					+ " daughterCalvIntStar  text, "
					+ " docility_index  text, "
					+ " docility_reliability  text, "
					+ " daughter_Calving_Diff  text, "
					+ " daughter_calving_rel  text, "
					+ " daughter_Milk_index  text, "
					+ " carcass_conform_index  text, "
					+ " carcass_conform_rel  text, "
					+ " daughter_milk_rel  text, "
					+ " daughter_calv_int  text, "
					+ " daughter_calv_int_rel  text"
					+ ");  ");

			//commit your changes
			db.setTransactionSuccessful();

		} catch (SQLException e1) {

		}
		finally {
			db.endTransaction();
		}

	}

	/**
	 * Creates table for terminal bull list
	 */
	private void addDataBaseTableT() {

		db.beginTransaction();
		try {
			db.execSQL("DROP TABLE IF EXISTS 'BullsTerminal'");
			//create table

			db.execSQL("create table BullsTerminal("
					+ " recID integer PRIMARY KEY autoincrement, "
					+ " TRank  text, "
					+ " TCode  text, "
					+ " TBullName  blob, "
					+ " TBreed  text, "
					+ " TBullIndex  text, "
					+ " TRel1  text, "
					+ " TStarsWithin  text, "
					+ " TStarsAcross  text, "
					+ " TCalvDiff  text, "
					+ " TRel2  text, "
					+ " TGest  text, "
					+ " TRel3  text, "
					+ " TDocility  text, "
					+ " TRel4  text, "
					+ " TCarcassWeight  text, "
					+ " TRel5  text, "
					+ " TCarcassConf  text, "
					+ " TRel6  text, "
					+ " TAvail  text, "
					+ " TPrice  text, "
					+ " TSupplier  text"
					+ ");  ");

			//commit your changes
			db.setTransactionSuccessful();

		} catch (SQLException e1) {
			System.out.println(e1);
		}
		finally {
			db.endTransaction();
		}

	}
	/**
	 * Deals with inserting returned data from Terminal Bulls
	 */

	private void insertDataT() {

		db.beginTransaction();
		try {

			//insert rows
			for(int i = 0; i < TRank.length;i++)
			{
				db.execSQL( "insert into BullsTerminal(TRank, TCode, TBullName, TBreed, TBullIndex, TRel1, TStarsWithin, TStarsAcross, TCalvDiff, TRel2, TGest, TRel3, TDocility, TRel4, TCarcassWeight, TRel4, TCarcassConf, TRel5, TAvail, TPrice, TSupplier) "
						+ " values ('"+TRank[i]+
						"' , '"+TCode[i]+
						"' , '"+TBullName[i]+
						"' , '"+TBreed[i]+
						"' , '"+TIndex[i]+
						"' , '"+TRel1[i]+
						"' , '"+TStarsWithin[i]+
						"' , '"+TStarsAcross[i]+
						"' , '"+TCalvDiff[i]+
						"' , '"+TRel2[i]+
						"' , '"+TGest[i]+
						"' , '"+TRel3[i]+
						"' , '"+TDocility[i]+
						"' , '"+TRel4[i]+
						"' , '"+TCarcassWeightkgs[i]+
						"' , '"+TRel5[i]+
						"' , '"+TCarcassConf[i]+
						"' , '"+TRel6[i]+
						"' , '"+TAvail[i]+
						"' , '"+TPrice[i]+
						"' , '"+TSupplier[i]+"');" );
			}
			//commit your changes
			db.setTransactionSuccessful();

		}
		catch (SQLiteException e2) {
		}
		finally {
			db.endTransaction();
			Toast.makeText(getBaseContext(), "DataBase Done", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Create table for Replacement Bulls
	 */

	private void addDataBaseTableM() {

		db.beginTransaction();
		try {
			db.execSQL("DROP TABLE IF EXISTS 'BullsMaternal'");
			//create table

			db.execSQL("create table BullsMaternal("
					+ " recID integer PRIMARY KEY autoincrement, "
					+ " MRank  text, "
					+ " MCode  text, "
					+ " MBullName  blob, "
					+ " MBreed  text, "
					+ " MBullIndex  text, "
					+ " MRel1  text, "
					+ " MStarsWithin  text, "
					+ " MStarsAcross  text, "
					+ " MCalvDiff  text, "
					+ " MRel2  text, "
					+ " MGest  text, "
					+ " MRel3  text, "
					+ " MDocility  text, "
					+ " MRel4  text, "
					+ " MCarcassWeight  text, "
					+ " MRel5  text, "
					+ " MCarcassConf  text, "
					+ " MRel6  text, "
					+ " MAvail  text, "
					+ " MPrice  text, "
					+ " MSupplier  text"
					+ ");  ");

			//commit your changes
			db.setTransactionSuccessful();

		} catch (SQLException e1) {
			System.out.println(e1);
		}
		finally {
			db.endTransaction();
		}
	}

	/**
	 * Deals with inserting returned data from Replacement Bulls
	 */
	private void insertDataM() {

		db.beginTransaction();
		try {

			//insert rows
			for(int i = 0; i < MRank.length;i++)
			{
				db.execSQL( "insert into BullsMaternal(MRank, MCode, MBullName, MBreed, MBullIndex, MRel1, MStarsWithin, MStarsAcross, MCalvDiff, MRel2, MGest, MRel3, MDocility, MRel4, MCarcassWeight, MRel4, MCarcassConf, MRel5, MAvail, MPrice, MSupplier) "
						+ " values ('"+MRank[i]+
						"' , '"+MCode[i]+
						"' , '"+MBullName[i]+
						"' , '"+MBreed[i]+
						"' , '"+MIndex[i]+
						"' , '"+MRel1[i]+
						"' , '"+MStarsWithin[i]+
						"' , '"+MStarsAcross[i]+
						"' , '"+MCalvDiff[i]+
						"' , '"+MRel2[i]+
						"' , '"+MGest[i]+
						"' , '"+MRel3[i]+
						"' , '"+MDocility[i]+
						"' , '"+MRel4[i]+
						"' , '"+MCarcassWeightkgs[i]+
						"' , '"+MRel5[i]+
						"' , '"+MCarcassConf[i]+
						"' , '"+MRel6[i]+
						"' , '"+MAvail[i]+
						"' , '"+MPrice[i]+
						"' , '"+MSupplier[i]+"');" );
			}
			//commit your changes
			db.setTransactionSuccessful();

		}
		catch (SQLiteException e2) {
		}
		finally {
			db.endTransaction();
			Toast.makeText(getBaseContext(), "DataBase Mat Done", Toast.LENGTH_LONG).show();
		}
	}
}