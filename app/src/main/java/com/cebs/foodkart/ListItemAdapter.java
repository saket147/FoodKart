package com.cebs.foodkart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ListItemAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    ImageLoader imageLoader;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListItemAdapter(Context context,
                           ArrayList<HashMap<String, String>> arraylist)
    {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView item_name,item_description,item_price,menu_name;

        ImageView logo=null;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.activity_list_item_adapter, parent, false);
        // Get the position
        resultp = data.get(position);

        item_name = (TextView) itemView.findViewById(R.id.item_name);
        item_description = (TextView) itemView.findViewById(R.id.item_description);
        item_price = (TextView) itemView.findViewById(R.id.item_price);
        menu_name=(TextView)itemView.findViewById(R.id.menu_name) ;
        // Locate the ImageView in listview_item.xml
        logo = (ImageView) itemView.findViewById(R.id.logoo);

        item_name.setText(resultp.get(ItemShow.ITEM_NAME));
        item_description.setText(resultp.get(ItemShow.ITEM_DESCRIPTION));
        item_price.setText(resultp.get(ItemShow.ITEM_PRICE));
        menu_name.setText(resultp.get(ItemShow.MENU_NAME));
        // Capture position and set results to the ImageView
        // Passes flag images URL into ImageLoader.class
        imageLoader.DisplayImage(resultp.get(ItemShow.LOGO), logo);
        // Capture ListView item click

        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get the position
                resultp = data.get(position);
                Intent intent = new Intent(context, ItemShow.class);
                // Pass all data rank
                intent.putExtra("id", resultp.get(ItemShow.MENU_NAME));
				/*// Pass all data country
				intent.putExtra("country", resultp.get(MainActivity.BRANCH_ADDRESS1));
				// Pass all data population
				intent.putExtra("population",resultp.get(MainActivity.BRANCH_ADDRESS2));
				// Pass all data flag
				intent.putExtra("flag", resultp.get(MainActivity.LOGO));
				// Start SingleItemView Class*/
                context.startActivity(intent);

            }
        });



        return itemView;
    }



}
