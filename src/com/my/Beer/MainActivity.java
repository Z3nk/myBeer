package com.my.Beer;

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

public class MainActivity extends FragmentActivity implements
		LocationListener {
	public GoogleMap googleMap;
	private LocationManager lm;
	private static final LatLng CS=new LatLng(50.611677, 3.142345);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// On check si Google Play Services est bien installé
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Si non, on retourne une erreur
		if (status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();
		} else {
			try {
				initilizeMap();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialisation de Google Map
	 */
	private void initilizeMap() {
		if (googleMap == null) {
			// On récupère la Google map du fichier layout.xml
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
					R.id.map)).getMap();
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
			addMarkerTest();
			// check si la map a bien été créée ou pas
			if (googleMap == null) {
				// on affiche un message à l'utilisateur
				Toast.makeText(getApplicationContext(),
						"Désolé impossible de créer la map", Toast.LENGTH_SHORT)
						.show();
			} else {
				// afficher notre position sur la carte
				googleMap.setMyLocationEnabled(true);
			}
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

	@Override
	public void onLocationChanged(Location location) {
		TextView textView = (TextView) findViewById(R.id.fiche);

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		// On ajout un marker sur notre position
		googleMap.addMarker(new MarkerOptions().position(latLng).title("yolo").snippet("swag"));
		
		// Showing the current location in Google Map
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

		// on change le text
		textView.setText("Latitude:" + latitude + ", Longitude:" + longitude);
		
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