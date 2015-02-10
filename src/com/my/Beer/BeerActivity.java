package com.my.Beer;

import com.my.Entity.Beer;
import com.my.Threads.GetAvisBeerThread;
import com.my.Tools.Beer_DAO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BeerActivity extends Activity {

	private TextView nom, type, prix, pourcentAlcol, fiche;
	private TextView titreNom, titrePrix;
	private Beer beer;
	private Beer_DAO DAO;

	@SuppressLint("NewApi") // permet d'éviter l'erreur générée sur setAllCaps, ici on vérifie de toute façon que la version de l'API correspond afin de pouvoir appeler setAllCaps..
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.beer_activity);
		DAO = new Beer_DAO();
		DAO.ouverture(this);
		titreNom = (TextView) findViewById(R.id.nameBeer);
		titrePrix=(TextView) findViewById(R.id.price);
		nom = (TextView) findViewById(R.id.nom);
		type=(TextView) findViewById(R.id.type);
		prix = (TextView) findViewById(R.id.prix);
		fiche = (TextView) findViewById(R.id.Fiche);
		pourcentAlcol = (TextView) findViewById(R.id.pourcentAlcol);
		int currentAPIversion = android.os.Build.VERSION.SDK_INT;
		if (currentAPIversion >= 14)
				nom.setAllCaps(true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle donnees = getIntent().getExtras();
		if (donnees.getString("id") != "-1") {
			beer = DAO.getBeer(donnees.getString("id"));
			titreNom.setText(beer.getName());
			titrePrix.setText(beer.getPrix());
			nom.setText(beer.getName());
			prix.setText(beer.getPrix());
			type.setText(beer.getType());
			pourcentAlcol.setText(beer.getPourcentAlcool());
			fiche.setText(beer.getFiche());
		} else {

		}
	}

	@Override
	protected void onDestroy() {
		DAO.fermeture();
		super.onDestroy();
	}

}
