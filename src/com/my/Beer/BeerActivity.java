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

	private TextView nom, prix, content;
	private Beer beer;
	private Beer_DAO DAO;
	@SuppressWarnings("unused")
	private Button fiche, avis;

	@SuppressLint("NewApi") // permet d'éviter l'erreur générée sur setAllCaps, ici on vérifie de toute façon que la version de l'API correspond afin de pouvoir appeler setAllCaps..
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.beer_activity);
		DAO = new Beer_DAO();
		DAO.ouverture(this);
		content = (TextView) findViewById(R.id.content);
		nom = (TextView) findViewById(R.id.nameBeer);
		prix = (TextView) findViewById(R.id.price);
		fiche = (Button) findViewById(R.id.fiche);
		avis = (Button) findViewById(R.id.avis);
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
			nom.setText(beer.getName());
			prix.setText(beer.getPrix());
			content.setText(beer.getFiche());
		} else {

		}
	}

	public void fiche(View vue) {
		try {
			synchronized (content) {
				content.setText(beer.getFiche());
			}
		} catch (Exception e) {

		}
	}

	public void avis(View vue) {
		content.setText("");
		GetAvisBeerThread threadGetAvis = new GetAvisBeerThread(DAO, beer,
				content);
		threadGetAvis.start();
	}

	@Override
	protected void onDestroy() {
		DAO.fermeture();
		super.onDestroy();
	}

}
