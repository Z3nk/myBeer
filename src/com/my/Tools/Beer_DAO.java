package com.my.Tools;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.my.Entity.Bar;
import com.my.Entity.Beer;

public class Beer_DAO {
	   private SQLiteDatabase bd;
	    

	    public Beer_DAO( ) {
		}

		public void ouverture(Context ctx) {
	        bd = new Beer_BDD(ctx, "beer.bd", null, 1).getWritableDatabase();
	    }

	    public void fermeture() {
	        bd.close();
	    }

		public Beer getBeer(String tab) {
		        Cursor curseur = bd.query("beer", null, "idServer = '"+tab+"'", null,  null, null, null) ;
		        if (curseur.getCount()==0) return null;
		        curseur.moveToFirst();
		        Beer b = new Beer();
	            b.setId(curseur.getInt(0));
	            b.setName(curseur.getString(1));
	            b.setType(curseur.getString(2));
	            b.setPrix(curseur.getString(3));
	            b.setPourcentAlcool(curseur.getString(4));
	            b.setIdUpdate(curseur.getInt(5));
	            b.setIdServer(curseur.getString(6));
		        curseur.close();
		        return b;
		}

		public long add(Beer b) {
			 ContentValues valeurs = new ContentValues();
		     valeurs.put("name", b.getName());
		     valeurs.put("type", b.getType());
		     valeurs.put("prix", b.getPrix());
		     valeurs.put("pourcentAlcool", b.getPourcentAlcool());
		     valeurs.put("idUpdate", b.getIdUpdate());
		     valeurs.put("idServer", b.getIdServer());
		     return bd.insert("beer", null, valeurs);
			
		}

		public long update(Beer b) {
			 ContentValues valeurs = new ContentValues();
			  valeurs.put("name", b.getName());
		      valeurs.put("type", b.getType());
		      valeurs.put("prix", b.getPrix());
		      valeurs.put("pourcentAlcool", b.getPourcentAlcool());
		      valeurs.put("idUpdate", b.getIdUpdate());
		      valeurs.put("idServer", b.getIdServer());
		     bd.update("beer", valeurs, "id = "+b.getId(), null);
	         return b.getId();
		}
		
		public int updateFromIdServer(Beer b) {
			ContentValues valeurs = new ContentValues();
			  valeurs.put("name", b.getName());
		      valeurs.put("type", b.getType());
		      valeurs.put("prix", b.getPrix());
		      valeurs.put("pourcentAlcool", b.getPourcentAlcool());
		      valeurs.put("idUpdate", b.getIdUpdate());
		      valeurs.put("idServer", b.getIdServer());
		     return bd.update("beer", valeurs, "idServer = ? ", new String[] { b.getIdServer()});// AND idUpdate != ? , Integer.toString(bar.getIdUpdate())
		}

		public void delete(Bar bar) {
			// TODO Auto-generated method stub
		}
		
		public ArrayList<Beer> getBeers() {
			 ArrayList<Beer> liste = new ArrayList<Beer>();
		        Cursor curseur = bd.query("bar", null, null, null,  null, null, "name") ;
		        if (curseur.getCount()==0) 
		        	return liste;
		        curseur.moveToFirst();
		        do {
		        	Beer b = new Beer();
		        	b.setId(curseur.getInt(0));
		            b.setName(curseur.getString(1));
		            b.setType(curseur.getString(2));
		            b.setPrix(curseur.getString(3));
		            b.setPourcentAlcool(curseur.getString(4));
		            b.setIdUpdate(curseur.getInt(5));
		            b.setIdServer(curseur.getString(6));
		            liste.add(b);
		        }
		        while (curseur.moveToNext());
		        	curseur.close();
		        return liste;
		}
}
