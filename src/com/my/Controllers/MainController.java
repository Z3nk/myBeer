package com.my.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;
import com.my.Tools.GoogleMapTools;
import com.my.Tools.MyBeerServer;

public class MainController {
	
	public static void updateDAO(Bar_DAO DAO, LatLng latlng)
	{
		for(Bar b:DAO.getBars()){
			if(GoogleMapTools.DistanceBetweenPlaces(b.getPos().longitude, b.getPos().latitude, latlng.longitude, latlng.latitude)<15){
				
			}
		}
	}
	
	
	public static void updateBeerDAO(Beer_DAO DAO, LatLng latlng)
	{
		
	}
	/*
	 * A optimiser, ici j extrais de maniere greedy toute les biere de la bdd local
	 */
	public static List<Bar> GetAllBarsFromMe(Bar_DAO DAO, LatLng latLng) {
		List<Bar> L=DAO.getBars();
		List<Bar> L2=new ArrayList<Bar>();
		for(Bar b:L)
			if(GoogleMapTools.DistanceBetweenPlaces(b.getPos().longitude, b.getPos().latitude, latLng.longitude, latLng.latitude)<15)
				L2.add(b);
		if(L2.size()>0)
			return L2;
		return null;
	}

}
