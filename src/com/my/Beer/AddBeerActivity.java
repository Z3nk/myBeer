package com.my.Beer;

import com.google.android.gms.maps.model.LatLng;
import com.my.Controllers.BarController;
import com.my.Entity.Bar;
import com.my.Entity.Beer;
import com.my.Tools.Bar_DAO;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBeerActivity extends Activity  {
	
	private EditText nom, type, prix, pourcentALcool;
	private Button enregistrer, modifier, supprimer;
	private Beer beer;
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.addbeer_activity);
		beer=new Beer();
		nom = (EditText) findViewById(R.id.nom);
		type= (EditText) findViewById(R.id.type);
		prix = (EditText) findViewById(R.id.prix);
		pourcentALcool = (EditText) findViewById(R.id.pourcentAlcol);
		enregistrer = (Button) findViewById(R.id.enregistrer);
		modifier = (Button) findViewById(R.id.modifier);
		supprimer = (Button) findViewById(R.id.supprimer);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle donnees = getIntent().getExtras();
		/*bar.setPos((LatLng) donnees.getParcelable("pos"));
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
			enregistrer.setEnabled(true);
			modifier.setEnabled(false);
			supprimer.setEnabled(false);
		}*/
	
	}
	
	public void enregistrer(View vue) {
		
	}

	public void modifier(View vue) {
		
	}

	public void supprimer(View vue) {
	
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
