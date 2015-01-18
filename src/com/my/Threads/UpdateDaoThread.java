package com.my.Threads;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.my.Beer.MainActivity;
import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;
import com.my.Tools.GoogleMapTools;
import com.my.Tools.MyBeerServer;

public class UpdateDaoThread extends Thread {
	private Bar_DAO dAO;
	private LatLng latlng;
	public String tab;
    public UpdateDaoThread(Bar_DAO dAO, LatLng latlng) {
		this.dAO=dAO;
		this.latlng=latlng;
		tab="not init yet";
	}
	public void run(){
		try {
				tab=MyBeerServer.getBars(this.latlng);
				ArrayList<Bar> L=dAO.getBars();
				JSONArray JSON=new JSONArray(tab);
				for(int i=0;i<JSON.length();i++){
					JSONObject b=JSON.getJSONObject(i);
					Bar b2=new Bar();
					b2.setName(b.getString("name"));
					b2.setPosFromToString(b.getString("pos"));
					b2.setAdress(b.getString("adress"));
					b2.setBeers(b.getString("beers"));
					b2.setIdUpdate(b.getInt("idupdate"));
					b2.setIdServer(b.getString("_id"));
					System.out.println("[UpdateDaoThread][Run][39] On recherche un bar :"+b2.toString()+" id Serveur:"+b2.getIdServer());
					int res=dAO.updateFromIdServer(b2);
					if(res==0)
						dAO.add(b2);
				}
				MainActivity.isSynchronisedWithServer = true;
			} catch (Exception e) {
			e.printStackTrace();
			// Afficher ici message erreur connection avec le serveur a l utilisateur 
		}
	}
}
