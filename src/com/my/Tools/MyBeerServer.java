package com.my.Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.my.Entity.*;
import com.google.android.gms.maps.model.LatLng;

public class MyBeerServer {
	
	public static final String BEER_ADRESSE="http://192.168.0.11:3000";
	
	public static String getBars() throws Exception{
		
		URL url = new URL(BEER_ADRESSE + "/bars");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if (conn.getResponseCode() != 200 && conn.getResponseCode() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}
 
		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
 
		String yolo="";
		String output;
		System.out.println("Output from Server for getBars.... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			yolo+=output;
		}
		conn.disconnect();
		return yolo;
	}
	public static String addBar(Bar bar) throws Exception
	{
		URL url = new URL(BEER_ADRESSE + "/bars");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		
		String input = bar.toString();
		
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
 
		String output;
		System.out.println("Output from Server addBarYolo.... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
			if(output.contains("_id"))
				return output.substring(output.indexOf(':')+1).replaceAll("\"", "").trim();
			// En theorie  "_id": "549454d59ab258241958c25a" devient : 549454d59ab258241958c25a

		}
 
		conn.disconnect();
		return "[MyBeerServer][Erreur][AddBar]";
	}
	
	public static void updateBar(Bar bar) throws IOException
	{
		URL url = new URL(BEER_ADRESSE + "/bars/"+bar.getIdServer());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
		
		String input = bar.toString();
		
		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
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
	public static List<Bar> getAllBarFromMe(LatLng myPos, int rayon)
	{
		return null;
	}
}
