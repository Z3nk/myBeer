package com.my.Beer;

import com.google.android.gms.maps.model.LatLng;
import com.my.Controllers.BarController;
import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BarActivity extends Activity {
	private Bar_DAO DAO;
	private Bar bar;
	private TextView nom, adress;
	private Button enregistrer;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.bar_activity);
		DAO=new Bar_DAO();
		DAO.ouverture(this);
		bar=new Bar();
		nom = (TextView) findViewById(R.id.nameBar);
		adress= (TextView) findViewById(R.id.adress);
		enregistrer = (Button) findViewById(R.id.addBeer);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle donnees = getIntent().getExtras();
		bar.setPos((LatLng) donnees.getParcelable("pos"));
		if (donnees.getLong("id") != -1) {
			bar = DAO.getBar(donnees.getLong("id"));
			System.out.println(bar.toString());
			nom.setText(bar.getName());
			adress.setText(bar.getAdress());
		}
		// sinon on peut ajouter un bar
		else {
			enregistrer.setEnabled(true);
		}
		
	}
	
	public void addNewBeer(View vue) {
		Intent i = new Intent(this, AddBeerActivity.class);
		startActivityForResult(i, 1);
		finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == RESULT_OK){
	            String result=data.getStringExtra("result");
	        }
	        if (resultCode == RESULT_CANCELED) {
	            //Write your code if there's no result
	        }
	    }
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
