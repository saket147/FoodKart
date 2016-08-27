package com.cebs.foodkart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemList extends AppCompatActivity {
    int id;
    ListView ListItem;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    Map<String,String> itemId=new HashMap<String,String>();
    ArrayList<HashMap<String, String>> arraylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ListItem=(ListView)findViewById(R.id.listItem);
        new Item().execute();

        ListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),"Clicked",Toast.LENGTH_LONG).show();
                String menu= arrayList.get(position);
                Intent i=new Intent(getBaseContext(),ItemShow.class);
                i.putExtra("menu_name",itemId.get(menu));
                startActivity(i);
            }
        });

    }
    public class Item extends AsyncTask<Void,Void,Void>
    {
        public  Item()
        {
            Intent i = getIntent();
            Bundle b= i.getExtras();
            id=Integer.parseInt( b.getString("id"));
        }
        @Override
        protected Void doInBackground(Void... params)
        {
            arraylist = new ArrayList<HashMap<String, String>>();

            try {
                JSONObject jsonObject = JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/items.jsp?id="+ id);
                Log.d("Test1","Test1");
                JSONArray jsonArray = jsonObject.getJSONArray("Items");

                Log.d("JSON",jsonArray.toString());
                arrayList=new ArrayList<String>();

                for (int i=0;i<jsonArray.length();i++)
                {
                    arrayList.add(jsonArray.getJSONObject(i).getString("item_name"));
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
            ListItem.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
