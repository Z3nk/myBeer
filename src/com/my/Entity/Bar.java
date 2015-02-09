package com.my.Entity;

import com.google.android.gms.maps.model.LatLng;
import com.my.Tools.GoogleMapTools;

public class Bar {
	private long id;
	private String name;
	private LatLng pos;
	private String adress;
	private String beers;
	private int idUpdate;
	private String idServer;

	public Bar() {
		this.idUpdate = 0;
		beers = "";
	}

	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	public LatLng getPos() {
		return pos;
	}

	public void setPos(LatLng pos) {
		this.pos = pos;
	}

	public String getBeers() {
		return beers;
	}

	public void addBeers(String b) {
		if (beers.equals(""))
			beers = b;
		else
			beers += ";" + b;
	}

	public void setBeers(String beers) {
		this.beers = beers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getIdUpdate() {
		return idUpdate;
	}

	public void addIdUpdate() {
		this.idUpdate++;
	}

	public void setIdUpdate(int idUpdate) {
		this.idUpdate = idUpdate;
	}

	@Override
	public String toString() {
		return "{\"id\":" + id + ",\"name\":\"" + name + "\",\"adress\":\""
				+ adress + "\",\"pos\":\"" + GoogleMapTools.fromLatLng(pos)
				+ "\",\"beers\":\"" + beers + "\",\"idupdate\":\"" + idUpdate
				+ "\"}";
	}

	public String getIdServer() {
		return idServer;
	}

	public void setIdServer(String idServer) {
		this.idServer = idServer;
	}

	public void setPosFromToString(String string) {
		string = string.replace("[", "").replace("]", "").trim();
		double lat;
		double lng;
		if (string.contains(")")) {
			string = string.split(":")[1].replace("(", "").replace(")", "")
					.trim();
			lat = Double.parseDouble(string.split(",")[0]);
			lng = Double.parseDouble(string.split(",")[1]);
		} else {
			lat = Double.parseDouble(string.split(",")[1]);
			lng = Double.parseDouble(string.split(",")[0]);
		}
		pos = new LatLng(lat, lng);

	}

}
