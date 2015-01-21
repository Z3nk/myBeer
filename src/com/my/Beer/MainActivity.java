package com.my.Beer;

import java.util.List;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.my.Controllers.MainController;
import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;
import com.my.Tools.GoogleMapTools;

public class MainActivity extends FragmentActivity implements
		LocationListener {
	/**
	 * DAO
	 */
	private Beer_DAO DAO_beer;
	private Bar_DAO DAO;
	/**
	 * Boolean
	 */
	public static boolean isSynchronisedWithServer;
	private boolean isSynchroniseWithServerDisplay;
	private boolean threadIsWorking;
	/**
	 * Tools
	 */
	private LatLng lastPosition;
	public GoogleMap googleMap;
	private LocationManager lm;
	private static final LatLng CS=new LatLng(50.611677, 3.142345);
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		DAO=new Bar_DAO();
		DAO.ouverture(this);
		
		DAO_beer=new Beer_DAO();
		DAO_beer.ouverture(this);

		// On check si Google Play Services est bien install�
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

		// Si non, on retourne une erreur
		if (status != ConnectionResult.SUCCESS)
			GooglePlayServicesUtil.getErrorDialog(status, this, 10).show();
		else
			initilizeMap();
	}

	/**
	 * Initialisation de Google Map
	 */
	private void initilizeMap() {
		isSynchronisedWithServer=false;
		isSynchroniseWithServerDisplay=false;
		threadIsWorking=false;
		/**
		 * instantiation && initialisation de googleMap
		 */
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.setOnMarkerClickListener(
				new OnMarkerClickListener(){
					@Override
					public boolean onMarkerClick(Marker marker) {
						/* ne fonctionne pas pour l'instant */
						if (!marker.isInfoWindowShown())
			                marker.showInfoWindow();
			            else 
			                marker.hideInfoWindow();
						/*List<Bar> l = DAO.getBars();
						for(Bar bartmp : l){
							if(bartmp.getPos().equals(arg0.getPosition())){
								Intent intention = new Intent(MainActivity.this.getApplicationContext(), BarActivity.class);
								System.out.println("[MainActivity][onMarkerClick][96]"+bartmp.toString());
								intention.putExtra("id", bartmp.getId());
								intention.putExtra("pos", bartmp.getPos());
						        startActivity(intention);
							}
						}
						Toast.makeText(getApplicationContext(),
								"YOLO", Toast.LENGTH_SHORT)
								.show();*/
						return false;
					}
			});
			googleMap.setOnInfoWindowClickListener(
					new OnInfoWindowClickListener() {
						
						@Override
						public void onInfoWindowClick(Marker arg0) {
							// TODO Auto-generated method stub
							List<Bar> l = DAO.getBars();
							for(Bar bartmp : l){
								if(bartmp.getPos().equals(arg0.getPosition())){
									Intent intention = new Intent(MainActivity.this.getApplicationContext(), BarActivity.class);
									System.out.println("[MainActivity][onMarkerClick][96]"+bartmp.toString());
									intention.putExtra("id", bartmp.getId());
									intention.putExtra("pos", bartmp.getPos());
							        startActivity(intention);
								}
							}
						}
					});
			googleMap.setOnMapLongClickListener(new OnMapLongClickListener(){
				@Override
				public void onMapLongClick(LatLng arg0) {
					Intent intention = new Intent(MainActivity.this.getApplicationContext(), AddBarActivity.class);
					intention.putExtra("id", -1);
					intention.putExtra("pos", arg0);
			        startActivity(intention);
				}
			});
		}

		/**
		 * Action sur googlemap
		 */
		if (googleMap == null) {
			Toast.makeText(getApplicationContext(),
					"Desole impossible de creer la map", Toast.LENGTH_SHORT)
					.show();
		} 
		else {
			googleMap.setMyLocationEnabled(true);
		}
	}	

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
		// on reinitialise la map lorsque l'on resume l'appli
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
		
		List<Bar> Bars=MainController.GetAllBarsFromMe(DAO,lastPosition);
		if(Bars!=null)
			for(Bar b:Bars)
				GoogleMapTools.addMarker(googleMap,
		new MarkerOptions().position(b.getPos()).title(b.getName()).snippet(String.valueOf(b.getBeers().split(";").length)+" bieres"));
		
	}

	/**
	 * Lorsque la localisation change ..
	 */
	@Override
	public void onLocationChanged(Location location) {
		TextView textView = (TextView) findViewById(R.id.fiche);
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		boolean isJustSynchroniseNeedToAddMarker = false;
		if(!isSynchronisedWithServer && !threadIsWorking){
			MainController.updateDAO(DAO, DAO_beer, latLng);
			threadIsWorking=true;
		}else{
			if(isSynchroniseWithServerDisplay==false)
				isJustSynchroniseNeedToAddMarker=true;
		}
		
		
		textView.setText("Latitude:" + latLng.latitude + ", Longitude:" + latLng.longitude);
		
		// A mettre dans le onresume et recuperer une derniere position recente en variable de classe !
		if(isJustSynchroniseNeedToAddMarker || lastPosition == null || 15<GoogleMapTools.DistanceBetweenPlaces(latLng.longitude,latLng.latitude,lastPosition.longitude,lastPosition.latitude)){
			// Showing the current location in Google Map
			googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
			// Zoom in the Google Map
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
			List<Bar> Bars=MainController.GetAllBarsFromMe(DAO,latLng);
			if(Bars!=null)
				for(Bar b:Bars)
					GoogleMapTools.addMarker(googleMap, new MarkerOptions().position(b.getPos()).title(b.getName()).snippet(String.valueOf(b.getBeers().split(";").length - 1)+" bi�res"));
			if(isJustSynchroniseNeedToAddMarker){
				isSynchroniseWithServerDisplay=true;
				isJustSynchroniseNeedToAddMarker=false;
			}
		}
		lastPosition=latLng;
		
	
				
			
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}