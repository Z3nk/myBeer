package com.my.Entity;

import com.google.android.gms.maps.model.LatLng;

public class Bar {
	private long id;
	private String name;
	private LatLng pos;
	private String adress;
	private String beers;
	private int idUpdate;
	private String idServer;
	
	public Bar(){
		this.idUpdate=0;
		beers="";
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
		if(beers.equals(""))
			beers=b;
		else beers+=";"+b;
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
		this.idUpdate=idUpdate;
	}
	
	@Override
	public String toString(){
		return "{\"id\":"+id+",\"name\":\""+name+"\",\"adress\":\""+adress+"\",\"pos\":\""+pos.toString()+"\",\"beers\":\""+beers+"\",\"idupdate\":\""+idUpdate+"\"}";
	}
	public String getIdServer() {
		return idServer;
	}
	public void setIdServer(String idServer) {
		this.idServer = idServer;
	}
	public void setPosFromToString(String string) {
		//"pos": "lat/lng: (51.012837930934545,2.406480610370636)",
		double lat=Double.parseDouble(string.substring(string.indexOf('(')+1,string.indexOf(')')).split(",")[0]);
		double lng=Double.parseDouble(string.substring(string.indexOf('(')+1,string.indexOf(')')).split(",")[1]);
		pos=new LatLng(lat,lng);
		
	}
	
	
	
}
