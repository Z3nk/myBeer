package com.my.Threads;

import java.util.ArrayList;

import android.widget.EditText;
//import android.widget.TextView;

import com.my.Beer.AddBarActivity;
import com.my.Entity.Bar;
import com.my.Entity.Place;
//import com.my.Tools.Bar_DAO;
//import com.my.Tools.MyBeerServer;
import com.my.Tools.PlacesService;

public class AddBarAutoCompleteAdressThread extends Thread {
	private EditText name, adresse;
	private Bar b;
	private AddBarActivity activity;
	private Place p;

	public AddBarAutoCompleteAdressThread(Bar b, EditText name,
			EditText adresse, AddBarActivity activity) {
		this.name = name;
		this.adresse = adresse;
		this.b = b;
		this.activity = activity;
	}

	public void run() {
		p = new Place();
		ArrayList<Place> list = PlacesService.search(b.getPos().latitude,
				b.getPos().longitude, 5);
		if (list.size() > 0) {
			p = PlacesService.details(list.get(0).reference);

			this.activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (!p.formatted_address.contains(p.name))
						name.setText(p.name);
					adresse.setText(p.formatted_address);
				}
			});
		}
	}
}
