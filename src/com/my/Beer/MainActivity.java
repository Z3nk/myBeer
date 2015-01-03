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
	private boolean isSynchronisedWithServer;
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
		isSynchronisedWithServer=false;
		DAO=new Bar_DAO();
		DAO.ouverture(this);
		
		DAO_beer=new Beer_DAO();

		// On check si Google Play Services est bien installé
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
		/**
		 * instantiation && initialisation de googleMap
		 */
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			googleMap.setOnMarkerClickListener(
				new OnMarkerClickListener(){
					@Override
					public boolean onMarkerClick(Marker arg0) {
						Toast.makeText(getApplicationContext(),
								"YOLO", Toast.LENGTH_SHORT)
								.show();
						return false;
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
					"Désolé impossible de créer la map", Toast.LENGTH_SHORT)
					.show();
		} 
		else {
			googleMap.setMyLocationEnabled(true);
		}
	}

	private void addMarkerTest() {
		Marker CSMarker = googleMap.addMarker(new MarkerOptions().position(CS).title("CS").snippet("notre metro"));
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(CS));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
		// on réinitialise la map lorsque l'on résume l'appli
		lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
		
	}

	/**
	 * Lorsque la localisation change ..
	 */
	@Override
	public void onLocationChanged(Location location) {
		
		TextView textView = (TextView) findViewById(R.id.fiche);
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		if(!isSynchronisedWithServer){
			MainController.updateDAO(DAO, latLng);
		}

		// On ajout un marker sur notre position
		//googleMap.addMarker(new MarkerOptions().position(latLng).title("Moi").snippet("Votre position"));
		
		// Showing the current location in Google Map
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		// Zoom in the Google Map
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
		textView.setText("Latitude:" + latLng.latitude + ", Longitude:" + latLng.longitude);
		
		// A mettre dans le onresume et recuperer une derniere position recente en variable de classe !
		if(lastPosition == null || 15<GoogleMapTools.DistanceBetweenPlaces(lastPosition.latitude, latLng.latitude, lastPosition.longitude, latLng.longitude)){
			List<Bar> Bars=MainController.GetAllBarsFromMe(DAO,latLng);
			if(Bars!=null)
				for(Bar b:Bars)
					GoogleMapTools.addMarker(googleMap, new MarkerOptions().position(b.getPos()).title(b.getName()).snippet(String.valueOf(b.getBeers().split(";").length)+" bières"));
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