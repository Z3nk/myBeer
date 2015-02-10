package com.my.Beer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.my.Controllers.BarController;
import com.my.Entity.Bar;
import com.my.Threads.AddBarAutoCompleteAdressThread;
import com.my.Tools.Bar_DAO;

public class AddBarActivity extends Activity {

	private Bar_DAO DAO;
	private Bar bar;
	private EditText nom, adresse;
	private Button enregistrer;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.addbar_activity);
		DAO = new Bar_DAO();
		DAO.ouverture(this);
		bar = new Bar();
		nom = (EditText) findViewById(R.id.nom);
		adresse = (EditText) findViewById(R.id.adresse);
		enregistrer = (Button) findViewById(R.id.enregistrer);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle donnees = getIntent().getExtras();
		bar.setPos((LatLng) donnees.getParcelable("pos"));
		if (donnees.getInt("id") != -1) {
			enregistrer.setEnabled(false);
			bar = DAO.getBar(donnees.getInt("id"));
			nom.setText(bar.getName());
			adresse.setText(bar.getAdress());
		}
		// sinon on peut ajouter un bar
		else {
			// runOnUiThread(new
			// AddBarAutoCompleteAdressThread(bar,nom,adresse));
			new AddBarAutoCompleteAdressThread(bar, nom, adresse, this).start();
			enregistrer.setEnabled(true);
		}
	}

	public void enregistrer(View vue) {
		
		if(nom.getText().toString().trim().equals("")){
			Toast.makeText(getApplicationContext(), "Il vous faut indiquer un nom au bar", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(adresse.getText().toString().trim().equals("")){
			Toast.makeText(getApplicationContext(), "Il vous faut indiquer une adresse au bar", Toast.LENGTH_SHORT).show();
			return;
		}
			
		bar.setName(nom.getText().toString());
		bar.setAdress(adresse.getText().toString());
		BarController.addBar(DAO, bar, this);
		finish();
	}

	/*
	 * public void supprimer(View vue) { DAO.delete(bar); finish(); }
	 */

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
