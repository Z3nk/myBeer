package com.my.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.my.Beer.R;
import com.my.Entity.Bar;

public class GoogleMapTools {
	private static final double PI = Math.PI;
	private static final double RADIO = 6378.16;
	private static HashMap<LatLng, MarkerOptions> listMarker=new HashMap<LatLng, MarkerOptions>();
	
	public static double Radians(double x)
    {
        return x * PI / 180;
    }
	public static double DistanceBetweenPlaces(double lon1,double lat1,double lon2,double lat2)
	{
        double dlon =  Radians(lon2 - lon1);
        double dlat =  Radians(lat2 - lat1);

        double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2)) + Math.cos(Radians(lat1)) * Math.cos(Radians(lat2)) * (Math.sin(dlon / 2) * Math.sin(dlon / 2));
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (angle * RADIO) * 0.62137;// distance en miles
	}

	public static void addMarker(GoogleMap googleMap, MarkerOptions snippet) {
		if(!listMarker.containsKey(snippet.getPosition())){
			googleMap.addMarker(snippet);
			listMarker.put(snippet.getPosition(),snippet);
		}
	}

	public static String fromLatLng(LatLng pos) {
		return "["+pos.longitude+","+pos.latitude+"]";
	}
	public static String getNbBeers(String valueOf) {
		String[] tab2=valueOf.split(";");
		int i=0;
		for(int y=0;y<tab2.length && tab2[y] != "";y++){
			i++;
		}
		return Integer.toString(i);
	}
	
	public static void displayMarkerOnMap(List<Bar> Bars, GoogleMap googleMap){
		if(Bars!=null)
			for(Bar b:Bars){
				MarkerOptions M=new MarkerOptions();
				M.position(b.getPos());
				M.title(b.getName());
				M.snippet(GoogleMapTools.getNbBeers(b.getBeers())+" bieres");
				BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.beericonmap);
				M.icon(bitmap);
				GoogleMapTools.addMarker(googleMap,M);
			}
	}
}
