package com.cebs.foodkart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class state extends AppCompatActivity{
    JSONObject jsonobject;
    JSONArray jsonarray;


    ProgressDialog mProgressDialog;
    ArrayList<String> arraylist,stateid,citylist,cityid;

    Spinner state,city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        state=(Spinner) findViewById(R.id.state);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int sid=Integer.parseInt(stateid.get(position));
                new City_List(sid).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        city=(Spinner) findViewById(R.id.city);
        new state_list().execute();
    }
    public class City_List extends  AsyncTask<Void,Void,Void>
    {
        int sid;

        public City_List(int sid) {
            this.sid = sid;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mProgressDialog = new ProgressDialog(state.this);
            // Set progressdialog title
            mProgressDialog.setTitle("FoodKart");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();;;;;
            ArrayAdapter< String> adapter=new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item,citylist);
                city.setAdapter(adapter);


        }

        @Override
        protected Void doInBackground(Void... params)
        {
            citylist=new ArrayList<String>();
            cityid=new ArrayList<String>();
            jsonobject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/cities.jsp?id="+sid);

            try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("Cities");
                Log.d("JSON", jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    //HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);


                    // Retrieve JSON Objects
                    //map.put("id", jsonobject.getString("id"));
                    //map.put("state_name",jsonobject.getString("state_name"));

                    citylist.add(jsonobject.getString("city_name"));
                    cityid.add(jsonobject.getString("id"));
                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    }
    public class state_list extends AsyncTask<Void, Void, Boolean>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(state.this);
            // Set progressdialog title
            mProgressDialog.setTitle("FoodKart");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();;;;;
            if(result)
            {
                ArrayAdapter< String> adapter=new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item,arraylist);
                state.setAdapter(adapter);

            }
// Specify the layout to use when the list of choices appears



        }

        @Override
        protected Boolean doInBackground(Void...params) {
            // Create an array
            arraylist = new ArrayList<String>();
            stateid = new ArrayList<String>();
            // Retrieve JSON Objects from the given URL address
            jsonobject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/states.jsp");

            try {
                // Locate the array name in JSON
                jsonarray = jsonobject.getJSONArray("States");
                Log.d("JSON",jsonarray.toString());
                for (int i = 0; i < jsonarray.length(); i++) {
                    //HashMap<String, String> map = new HashMap<String, String>();
                    jsonobject = jsonarray.getJSONObject(i);


                    // Retrieve JSON Objects
                    //map.put("id", jsonobject.getString("id"));
                    //map.put("state_name",jsonobject.getString("state_name"));

                    arraylist.add(jsonobject.getString("state_name"));
                    stateid.add(jsonobject.getString("id"));
                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return true;
        }
    }
    public void search(View view)
    {
        try
        {
            String city_id= cityid.get( city.getSelectedItemPosition());
            Intent i=new Intent(this,MainActivity.class);
            i.putExtra("id",city_id);
            startActivity(i);
        }
        catch (Exception ex){

        }
    }

}
