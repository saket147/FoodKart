package com.cebs.foodkart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemShow extends AppCompatActivity {

    TextView number,total,amount;
    Button increase,decrease,basket;
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    static String ITEM_NAME = "item_name";
    static String ITEM_DESCRIPTION = "item_description";
    static String ITEM_PRICE = "item_price";
    static String MENU_NAME="menu_name";
    static String LOGO = "logo";
    int num=0;
    int item_id=0;
    String menu_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_show);
        Bundle bundle= getIntent().getExtras();
        item_id= Integer.parseInt(bundle.getString("id"));

        number=(TextView)findViewById(R.id.number);
        total=(TextView)findViewById(R.id.Total);
        amount=(TextView)findViewById(R.id.Amount);
        Button increase=(Button)findViewById(R.id.btnIncrease);
        final Button decrease=(Button)findViewById(R.id.btnDecrease);
        final Button cart=(Button)findViewById(R.id.cartsize);
        basket=(Button)findViewById(R.id.btnBasket);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = Integer.parseInt(number.getText().toString());
                num++;
                number.setText(num);
                amount.setText(num*(Integer.parseInt(item_price)));
            }
        });
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = Integer.parseInt(number.getText().toString());
                if(num>1)
                {
                    num--;
                }
                else
                {
                    decrease.setFocusable(false);
                }
                number.setText(num);
                amount.setText(num*(Integer.parseInt(item_price)));
            }
        });
        basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.setText(num);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        new ItemJSON().execute();
    }
    private class ItemJSON extends AsyncTask<Void, Void, Void> {

        public ItemJSON(){
            {
                Intent i = getIntent();
                Bundle b= i.getExtras();
                menu_name=b.getString("menu_name");
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(ItemShow.this);
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
            return null;
            arraylist = new ArrayList<HashMap<String, String>>();
            jsonobject=JSONfunctions.getJSONfromURL("http://10.0.2.2:8010/RestroKart/json/items.jsp?id="+item_id);

            try {
                jsonarray = jsonobject.getJSONArray("Item");
                Log.d("JSON", jsonarray.toString());
                for (int i = 0; i <= jsonarray.length(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    jsonobject = jsonarray.getJSONObject(i);
                    map.put("menu_name", jsonobject.getString("menu_name"));
                    map.put("item_name", jsonobject.getString("item_name"));
                    map.put("item_description", jsonobject.getString("item_description"));
                    map.put("item_price", jsonobject.getString("item_price"));
                    map.put("logo", jsonobject.getString("logo"));
                    // Set the JSON Objects into the array
                    arraylist.add(map);
                }
            }catch(JSONException e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
                return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            listview = (ListView) findViewById(R.id.item_list);
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
            adapter = new ListViewAdapter(ItemShow.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }


    }
}
