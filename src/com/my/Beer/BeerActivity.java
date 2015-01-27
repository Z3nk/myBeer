package com.my.Beer;

import com.google.android.gms.maps.model.LatLng;
import com.my.Entity.Beer;
import com.my.Threads.GetAvisBeerThread;
import com.my.Tools.Beer_DAO;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BeerActivity extends Activity {

	private TextView nom, prix;
	private Beer beer;
	private Beer_DAO DAO;
	private TextView content;
	private Button fiche, avis;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.beer_activity);
		DAO=new Beer_DAO();
		DAO.ouverture(this);
		content = (TextView) findViewById(R.id.content);
		nom = (TextView) findViewById(R.id.nameBeer);
		prix= (TextView) findViewById(R.id.price);
		fiche = (Button) findViewById(R.id.fiche);
		avis = (Button) findViewById(R.id.avis);
		
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
			fiche.setEnabled(false);
			avis.setEnabled(true);
		}
		else {
		
		}
	}
	
	public void fiche(View vue) {
		synchronized(content) {
			content.setText(beer.getFiche());
		}
		avis.setEnabled(true);
		fiche.setEnabled(false);
	}
	
	public void avis(View vue) {
		content.setText("");
		GetAvisBeerThread threadGetAvis = new GetAvisBeerThread(DAO,beer,content);
		threadGetAvis.start();
		avis.setEnabled(false);
		fiche.setEnabled(true);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
