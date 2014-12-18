package com.my.Beer;

import com.google.android.gms.maps.model.LatLng;
import com.my.Entity.Bar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BarActivity extends Activity {
	Bar bar;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.bar_activity);
		bar=new Bar();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle donnees = getIntent().getExtras();
		Bar myParcelableObject = (Bar) donnees.getParcelable("bar");
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
