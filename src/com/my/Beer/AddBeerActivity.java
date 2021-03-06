package com.my.Beer;

import com.my.Controllers.BeerController;
import com.my.Entity.Beer;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBeerActivity extends Activity {

	private EditText nom, type, prix, pourcentALcool, fiche;
	@SuppressWarnings("unused")
	private Button enregistrer;
	private Beer beer;
	private Beer_DAO DAO;
	private Bar_DAO DAO_Bar;
	private long idBar;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.addbeer_activity);
		DAO = new Beer_DAO();
		DAO.ouverture(this);
		DAO_Bar = new Bar_DAO();
		DAO_Bar.ouverture(this);
		beer = new Beer();
		nom = (EditText) findViewById(R.id.nom);
		type = (EditText) findViewById(R.id.type);
		prix = (EditText) findViewById(R.id.prix);
		fiche = (EditText) findViewById(R.id.Fiche);
		pourcentALcool = (EditText) findViewById(R.id.pourcentAlcol);
		enregistrer = (Button) findViewById(R.id.enregistrer);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle donnees = getIntent().getExtras();
		idBar = donnees.getLong("idBar");
		/*
		 * bar.setPos((LatLng) donnees.getParcelable("pos")); if
		 * (donnees.getInt("id") != -1) { enregistrer.setEnabled(false);
		 * modifier.setEnabled(true); supprimer.setEnabled(true); bar =
		 * DAO.getBar(donnees.getInt("id")); nom.setText(bar.getName());
		 * adresse.setText(bar.getAdress()); } // sinon on peut ajouter un bar
		 * else { enregistrer.setEnabled(true); modifier.setEnabled(false);
		 * supprimer.setEnabled(false); }
		 */

	}

	public void enregistrer(View vue) {
		
		if(nom.getText().toString().trim().equals("")){
			Toast.makeText(getApplicationContext(), "Il vous faut indiquer un nom  � la bi�re", Toast.LENGTH_SHORT).show();
			return;
		}		
		if(type.getText().toString().trim().equals("")){
			Toast.makeText(getApplicationContext(), "Il vous faut indiquer une adresse � la bi�re", Toast.LENGTH_SHORT).show();
			return;
		}
		if(prix.getText().toString().trim().equals("")){
			Toast.makeText(getApplicationContext(), "Il vous faut indiquer un prix � la bi�re", Toast.LENGTH_SHORT).show();
			return;
		}		
		if(pourcentALcool.getText().toString().trim().equals("")){
			Toast.makeText(getApplicationContext(), "Il vous faut indiquer une % d'alcoolimie � la bi�re", Toast.LENGTH_SHORT).show();
			return;
		}
		if(fiche.getText().toString().trim().equals("")){
			Toast.makeText(getApplicationContext(), "Il vous faut indiquer une fiche � la bi�re", Toast.LENGTH_SHORT).show();
			return;
		}
		
		beer.setName(nom.getText().toString());
		beer.setType(type.getText().toString());
		beer.setPrix(prix.getText().toString());
		beer.setFiche(fiche.getText().toString());
		beer.setPourcentAlcool(pourcentALcool.getText().toString());
		BeerController.addBeer(DAO, beer, idBar, DAO_Bar, this);
		finish();
	}

	/*
	 * public void modifier(View vue) {
	 * 
	 * }
	 * 
	 * public void supprimer(View vue) {
	 * 
	 * }
	 */

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
