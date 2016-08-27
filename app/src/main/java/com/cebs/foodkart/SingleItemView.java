package com.cebs.foodkart;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SingleItemView extends Activity {
	// Declare Variables

	int id;
	ListView listMenu;
	ArrayAdapter<String> adapter;
	ArrayList<String> arrayList;
	Map<String,String> itemId=new HashMap<String,String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);
		listMenu=(ListView) findViewById(R.id.listMenu);
		listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getBaseContext(),"Clicked",Toast.LENGTH_LONG).show();
				String menu= arrayList.get(position);
				Intent i=new Intent(getBaseContext(),ItemList.class);
				i.putExtra("id",itemId.get(menu));
				startActivity(i);
			}
		});

		new Menu().execute();

		// Get the result of rank

	}
	public class Menu extends AsyncTask<Void,Void,Void>
	{
		public  Menu()
		{
			Intent i = getIntent();
			Bundle b= i.getExtras();
			id=Integer.parseInt( b.getString("id"));
		}
		@Override
		protected Void doInBackground(Void... params)
		{
			try {
				JSONObject jsonObject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/menus.jsp?id="+ id);
				Log.d("Test1","Test1");
				JSONArray jsonArray = jsonObject.getJSONArray("Menu");

				Log.d("JSON",jsonArray.toString());
				arrayList=new ArrayList<String>();

				for (int i=0;i<jsonArray.length();i++)
				{
					arrayList.add(jsonArray.getJSONObject(i).getString("menu_name"));
					itemId.put(jsonArray.getJSONObject(i).getString("menu_name"), jsonArray.getJSONObject(i).getString("id"));
				}


			}
			catch (Exception ex)
			{
				Toast.makeText(getBaseContext(),ex+"",Toast.LENGTH_LONG).show();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);

			adapter=new ArrayAdapter<String>(getBaseContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
			listMenu.setAdapter(adapter);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
}