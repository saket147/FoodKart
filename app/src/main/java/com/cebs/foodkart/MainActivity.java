package com.cebs.foodkart;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {
	// Declare Variables
	JSONObject jsonobject;
	JSONArray jsonarray;
	ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;
	int city_id=0;
	static String ID="id";
	static String BRANCH_NAME = "branch_name";
	static String BRANCH_ADDRESS1 = "branch_address1";
	static String BRANCH_ADDRESS2 = "branch_address2";
	static String LOGO = "logo";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml
		setContentView(R.layout.listview_main);
		Bundle bundle= getIntent().getExtras();
		city_id= Integer.parseInt(bundle.getString("id"));

		// Execute DownloadJSON AsyncTask
		new DownloadJSON().execute();
	}

	// DownloadJSON AsyncTask
	private class DownloadJSON extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(MainActivity.this);
			// Set progressdialog title
			mProgressDialog.setTitle("FoodKart");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
			// Retrieve JSON Objects from the given URL address
			jsonobject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/restaurants.jsp?id="+city_id);

			try {
				// Locate the array name in JSON
				jsonarray = jsonobject.getJSONArray("Restaurants");
                Log.d("JSON",jsonarray.toString());
				for (int i = 0; i < jsonarray.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					jsonobject = jsonarray.getJSONObject(i);

					// Retrieve JSON Objects
					map.put("id", jsonobject.getString("id"));
					map.put("branch_name", jsonobject.getString("branch_name"));
					map.put("branch_address1", jsonobject.getString("address_line1"));
					map.put("branch_address2", jsonobject.getString("address_line2"));
					map.put("logo", jsonobject.getString("logo"));
					// Set the JSON Objects into the array
					arraylist.add(map);
				}
			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.restaurants_list);
			listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
				{
					Map<String,String> m=(Map<String,String>)arraylist.get(position);
					int branchid= Integer.parseInt( m.get("id"));

					Intent i=new Intent(getBaseContext(),SingleItemView.class);
					i.putExtra("id",branchid);
					startActivity(i);

				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(MainActivity.this, arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
}