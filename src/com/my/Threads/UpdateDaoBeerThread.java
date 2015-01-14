package com.my.Threads;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.my.Entity.Bar;
import com.my.Tools.Beer_DAO;
import com.my.Tools.MyBeerServer;

public class UpdateDaoBeerThread extends Thread {

	Beer_DAO dAO;
	LatLng latlng;
	String tab;
	
	public UpdateDaoBeerThread(Beer_DAO dAO, LatLng latlng) {
		this.dAO=dAO;
		this.latlng=latlng;
		tab="";
	}

	public void run(){
		try {
				tab=MyBeerServer.getBars(latlng);
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
					//dAO.updateFromIdServer(b2);
				}
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
