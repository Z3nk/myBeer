package com.my.Entity;

import com.google.android.gms.maps.model.LatLng;

public class Bar {
	private long id;
	private String name;
	private LatLng pos;
	private String adress;
	private String beers;
	private int idUpdate;
	
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
	public void setIdUpdate() {
		this.idUpdate++;;
	}
	
	@Override
	public String toString(){
		return "{\"id\":"+id+",\"name\":\""+name+"\",\"adress\":\""+adress+"\",\"pos\":\""+pos.toString()+"\",\"beers\":\""+beers+"\",\"idupdate\":\""+idUpdate+"\"}";
	}
	
	
	
}
