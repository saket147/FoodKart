package com.cebs.foodkart;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
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
		// Declare Variables
		TextView branch_name,branch_address1,branch_address2,branch_id;

		ImageView logo=null;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_item, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		branch_name = (TextView) itemView.findViewById(R.id.branch_name);
		branch_address1 = (TextView) itemView.findViewById(R.id.branch_address1);
		branch_address2 = (TextView) itemView.findViewById(R.id.branch_address2);
		branch_id=(TextView)itemView.findViewById(R.id.branch_id) ;
		// Locate the ImageView in listview_item.xml
		logo = (ImageView) itemView.findViewById(R.id.logo);

		// Capture position and set results to the TextViews
		branch_name.setText(resultp.get(MainActivity.BRANCH_NAME));
		branch_address1.setText(resultp.get(MainActivity.BRANCH_ADDRESS1));
		branch_address2.setText(resultp.get(MainActivity.BRANCH_ADDRESS2));
		branch_id.setText(resultp.get(MainActivity.ID));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(MainActivity.LOGO), logo);
		// Capture ListView item click
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data rank
				intent.putExtra("id", resultp.get(MainActivity.ID));
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
