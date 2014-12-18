package com.my.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.my.Entity.*;
import com.google.android.gms.maps.model.LatLng;

public class MyBeerServer {
	
	public static final String BEER_ADRESSE="http://192.168.0.11:3000";
	
	public static void addBar(Bar bar) throws IOException
	{
		URL url = new URL(BEER_ADRESSE + "/bars");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		
		String input2 = bar.toString();
		System.out.println(input2);
		
		OutputStream os = conn.getOutputStream();
		os.write(input2.getBytes());
		os.flush();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
 
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
		conn.disconnect();
	}
	public static Beer getAllBarFromMe(LatLng myPos, int rayon)
	{
		return null;
	}
}
