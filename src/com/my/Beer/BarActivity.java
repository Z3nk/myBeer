package com.my.Beer;

import java.util.ArrayList;

import com.google.android.gms.maps.model.LatLng;
import com.my.Controllers.BarController;
import com.my.Entity.Bar;
import com.my.Entity.Beer;
import com.my.Tools.BarArrayAdapter;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class BarActivity extends ListActivity {
	private Bar_DAO DAO;
	private Beer_DAO beer_DAO;
	private Bar bar;
	private TextView nom, adress;
	private Button enregistrer;
	
	/*
	 * Affichage liste
	 */
	private ArrayList<Beer> beers;
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.bar_activity);
		DAO=new Bar_DAO();
		DAO.ouverture(this);
		
		beer_DAO=new Beer_DAO();
		beer_DAO.ouverture(this);
		beers = new ArrayList<Beer>();
		bar=new Bar();
		nom = (TextView) findViewById(R.id.nameBar);
		adress= (TextView) findViewById(R.id.adress);
		enregistrer = (Button) findViewById(R.id.addBeer);
		
		 /*
         * Au long click nous arrosons la plante
         */
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	 @Override
        	 public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        		 Beer b=beers.get(position);
        		 Intent i = new Intent(BarActivity.this.getApplicationContext(), BeerActivity.class);
        		 i.putExtra("id", b.getIdServer());
        		 startActivity(i);
        		 
        	 }
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		Bundle donnees = getIntent().getExtras();
		bar.setPos((LatLng) donnees.getParcelable("pos"));
		if (donnees.getLong("id") != -1) {
			bar = DAO.getBar(donnees.getLong("id"));
			String[] tab=bar.getBeers().split(";");
			String[] noms = new String[tab.length];
		    String[] prix = new String[tab.length];
		    String[] type = new String[tab.length];
		    Long[] ids= new Long[tab.length];
		    int[] img=new int[tab.length];
			for(int i=0;i<tab.length && tab[i] != "";i++)
			{
				beers.add(beer_DAO.getBeer(tab[i]));
				ids[i]=beers.get(i).getId();
				noms[i]=beers.get(i).getName();
				prix[i]=beers.get(i).getPrix();
				type[i]=beers.get(i).getType();
				img[i]=R.drawable.beer;
			}
			
		
		    
			nom.setText(bar.getName());
			adress.setText(bar.getAdress());
			/*
	         * On définit comment on veut que les éléments s'affichent
	         */
	        if(tab[0] != "")
	        	setListAdapter(new BarArrayAdapter(noms,prix,type,img,ids, this));
		}
		// sinon on peut ajouter un bar
		else {
			enregistrer.setEnabled(true);
		}
		
	}
	
	public void addNewBeer(View vue) {
		Intent i = new Intent(this, AddBeerActivity.class);
		i.putExtra("idBar", bar.getId());
		startActivity(i);
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
		DAO.fermeture();
	}

}
