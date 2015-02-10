package com.my.Threads;

//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
import java.util.ArrayList;
//import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;

import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.my.Beer.MainActivity;
import com.my.Entity.Bar;
import com.my.Entity.Beer;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;
//import com.my.Tools.GoogleMapTools;
import com.my.Tools.MyBeerServer;

public class UpdateDaoThread extends Thread {
	private Bar_DAO dAO;
	private Beer_DAO beer_dao;
	private LatLng latlng;
	public String tab;
	private MainActivity activity;

	public UpdateDaoThread(Bar_DAO dAO, Beer_DAO beer_dao, LatLng latlng, MainActivity activity) {
		this.dAO = dAO;
		this.beer_dao = beer_dao;
		this.latlng = latlng;
		this.activity=activity;
		tab = "not init yet";
	}

	public void run() {
		try {
			tab = MyBeerServer.getBars(this.latlng);
			@SuppressWarnings("unused")
			ArrayList<Bar> L = dAO.getBars();
			JSONArray JSON = new JSONArray(tab);
			for (int i = 0; i < JSON.length(); i++) {
				JSONObject b = JSON.getJSONObject(i);
				Bar b2 = new Bar();
				b2.setName(b.getString("name"));
				b2.setPosFromToString(b.getString("pos"));
				b2.setAdress(b.getString("adress"));
				b2.setBeers(b.getString("beers"));
				b2.setIdUpdate(b.getInt("idupdate"));
				b2.setIdServer(b.getString("_id"));
				// System.out.println("[UpdateDaoThread][Run][39] On recherche un bar :"+b2.toString()+" id Serveur:"+b2.getIdServer());
				int res = dAO.updateFromIdServer(b2);
				if (res == 0) {
					dAO.add(b2);
					// System.out.println("[UpdateDaoThread][Run][43] On rajoute un bar");
				} else {
					// System.out.println("[UpdateDaoThread][Run][46] On update "
					// + res + " bar");
				}

				String[] tab2;
				tab2 = b2.getBeers().split(";");
				for (int y = 0; y < tab2.length && tab2[y] != ""; y++) {
					String s = MyBeerServer.getBeer(tab2[y]);
					JSONObject J = new JSONObject(s);
					Beer beer2 = new Beer();
					beer2.setName(J.getString("name"));
					beer2.setFiche(J.getString("fiche"));
					beer2.setType(J.getString("type"));
					beer2.setPrix(J.getString("prix"));
					beer2.setPourcentAlcool(J.getString("pourcentAlcool"));
					beer2.setIdUpdate(J.getInt("idupdate"));
					beer2.setIdServer(J.getString("_id"));
					int res2 = beer_dao.updateFromIdServer(beer2);
					if (res2 == 0) {
						beer_dao.add(beer2);
						// System.out.println("[UpdateDaoThread][Run][43] On rajoute une biere");
					} else {
						// System.out.println("[UpdateDaoThread][Run][46] On update "
						// + res2 + " biere");
					}
				}
			}
			MainActivity.isSynchronisedWithServer = true;
		} catch (Exception e) {
			activity.runOnUiThread(new Runnable() {
			    public void run() {
			        Toast.makeText(activity, "Impossible de se connecter au serveur", Toast.LENGTH_SHORT).show();
			    }
			});
			e.printStackTrace();
			// Afficher ici message erreur connexion avec le serveur à l
			// utilisateur
		}
	}
}
