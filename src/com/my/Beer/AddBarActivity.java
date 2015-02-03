package com.my.Beer;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.my.Controllers.BarController;
import com.my.Entity.Bar;
import com.my.Threads.AddBarAutoCompleteAdressThread;
import com.my.Tools.Bar_DAO;
import com.my.Tools.MyBeerServer;

public class AddBarActivity extends Activity {
	
	private Bar_DAO DAO;
	private Bar bar;
	private EditText nom, adresse;
	private Button enregistrer, modifier, supprimer;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.addbar_activity);
		DAO=new Bar_DAO();
		DAO.ouverture(this);
		bar=new Bar();
		nom = (EditText) findViewById(R.id.nom);
		adresse = (EditText) findViewById(R.id.adresse);
		enregistrer = (Button) findViewById(R.id.enregistrer);
		modifier = (Button) findViewById(R.id.modifier);
		supprimer = (Button) findViewById(R.id.supprimer);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle donnees = getIntent().getExtras();
		bar.setPos((LatLng) donnees.getParcelable("pos"));
		if (donnees.getInt("id") != -1) {
			enregistrer.setEnabled(false);
			modifier.setEnabled(true);
			supprimer.setEnabled(true);
			bar = DAO.getBar(donnees.getInt("id"));
			nom.setText(bar.getName());
			adresse.setText(bar.getAdress());
		}
		// sinon on peut ajouter un bar
		else {
			
//			runOnUiThread(new AddBarAutoCompleteAdressThread(bar,nom,adresse));
			new AddBarAutoCompleteAdressThread(bar,nom,adresse,this).start();
			enregistrer.setEnabled(true);
			modifier.setEnabled(false);
			supprimer.setEnabled(false);
		}
	}
	
	public void enregistrer(View vue) {
		bar.setName(nom.getText().toString());
		bar.setAdress(adresse.getText().toString());
		BarController.addBar(DAO, bar);
		finish();
	}

	public void modifier(View vue) {
		bar.setName(nom.getText().toString());
		bar.setAdress(adresse.getText().toString());
		BarController.updateBar(DAO, bar);
		DAO.update(bar);
		finish();
	}

	public void supprimer(View vue) {
		DAO.delete(bar);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
