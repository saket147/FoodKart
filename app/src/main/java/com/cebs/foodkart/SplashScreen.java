package com.cebs.foodkart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dhiraj.Pathak on 8/22/2016.
 */
public class SplashScreen extends Activity
{
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalash);


        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);

                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                }
            }
        };
        timerThread.start();
        new CheckUser().execute();

    }
    private class CheckUser extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SplashScreen.this);
            // Set progressdialog title
            mProgressDialog.setTitle("FoodKart");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            TelephonyManager telephonyManager=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            String deviceid= telephonyManager.getDeviceId();
            Log.d("IMEI",deviceid);
            jsonobject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/checkuser.jsp?deviceid="+deviceid);

            try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("User");
                Log.d("JSON",jsonarray.toString());
                if(jsonarray.length()>0) {
                    Log.d("Result", !jsonarray.getJSONObject(0).getString("id").equals("0") + "");
                    return !jsonarray.getJSONObject(0).getString("id").equals("0");
                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean result) {

            mProgressDialog.dismiss();

            if (result) {

                Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getBaseContext(),"Register",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SplashScreen.this,Registration.class);
                startActivity(intent);
            }

        }
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
       // finish();
    }
}
