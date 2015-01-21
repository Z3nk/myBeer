package com.my.Controllers;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.my.Entity.Bar;
import com.my.Threads.UpdateDaoBeerThread;
import com.my.Threads.UpdateDaoThread;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;
import com.my.Tools.GoogleMapTools;
import com.my.Tools.MyBeerServer;

public class MainController {
	public static void updateDAO(Bar_DAO DAO, Beer_DAO beer_dao, LatLng latlng)
	{
		new UpdateDaoThread(DAO, beer_dao, latlng).start();
	}
	
	
	public static void updateBeerDAO(Beer_DAO DAO, LatLng latlng)
	{
		new UpdateDaoBeerThread(DAO, latlng).start();
	}
	/*
	 * A optimiser, ici j extrais de maniere greedy toute les biere de la bdd local
	 */
	public static List<Bar> GetAllBarsFromMe(Bar_DAO DAO, LatLng latLng) {
		List<Bar> L=DAO.getBars();
		List<Bar> L2=new ArrayList<Bar>();
		System.out.println("[MainController][GetAllBarsFromMe][32] Taille de la liste en BDD locale" + L.size());
		for(Bar b:L)
				L2.add(b);//if(GoogleMapTools.DistanceBetweenPlaces(b.getPos().longitude, b.getPos().latitude, latLng.longitude, latLng.latitude)<15)
		if(L2.size()>0)
			return L2;
		System.out.println("[MainController][GetAllBarsFromMe][37] Apparement ils sont trop loin les bars");
		return null;
	}

}
