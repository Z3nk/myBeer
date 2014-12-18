package com.my.Beer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BeerActivity extends Activity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.beer_activity);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
