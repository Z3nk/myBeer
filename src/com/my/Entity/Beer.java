package com.my.Entity;

import com.google.android.gms.maps.model.LatLng;
import com.my.Tools.GoogleMapTools;

public class Beer {
	private long id;
	private String name;
	private String type;
	private String prix;
	private String fiche;
	private String pourcentAlcool;
	private int idUpdate;
	private String idServer;
	
	public Beer(){
		this.idUpdate=0;
		this.fiche="";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrix() {
		return prix;
	}
	public void setPrix(String prix) {
		this.prix = prix;
	}
	public String getPourcentAlcool() {
		return pourcentAlcool;
	}
	public void setPourcentAlcool(String pourcentAlcool) {
		this.pourcentAlcool = pourcentAlcool;
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
	public String getIdServer() {
		return idServer;
	}
	public void setIdServer(String idServer) {
		this.idServer = idServer;
	}
	
	@Override
	public String toString(){
		return "{\"id\":"+id+",\"name\":\""+name+"\",\"type\":\""+type+"\",\"prix\":\""+prix+"\",\"pourcentAlcool\":\""+pourcentAlcool+"\",\"idupdate\":\""+idUpdate+"\"}";
	}
	public String getFiche() {
		return fiche;
	}
	public void setFiche(String fiche) {
		this.fiche = fiche;
	}
	
}
