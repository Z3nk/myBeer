package com.my.Tools;

import com.my.Beer.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.os.Debug;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BarArrayAdapter extends BaseAdapter{
	String[] title, type, prix;
    int[] img;
    Context context;

	public BarArrayAdapter(String[] title, String[] type, String[] prix,
			int[] img, Context context) {
		super();
		this.title = title;
		this.type = type;
		this.prix = prix;
		this.img = img;
		this.context = context;
	}
	@Override
	public int getCount() {
		return title.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 LayoutInflater inflater = LayoutInflater.from(context);
	        // Nous utilisons custom.xml pour afficher une ligne
	        View row = inflater.inflate(R.layout.itembar_activity, parent, false);
	        TextView title, type,prix;
	        ImageView i1;
	        title = (TextView) row.findViewById(R.id.title);
	        type = (TextView) row.findViewById(R.id.type);
	        prix = (TextView) row.findViewById(R.id.prix);
	        //i1=(ImageView)row.findViewById(R.id.img);
	        title.setText(this.title[position]);
	        type.setText(this.type[position]);
	        prix.setText(this.prix[position]);
	        //i1.setImageResource(this.img[position]);

	        return (row);
	}
}
