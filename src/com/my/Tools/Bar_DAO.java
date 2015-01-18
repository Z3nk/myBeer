package com.my.Tools;

import java.util.ArrayList;

import com.my.Entity.Bar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class Bar_DAO {
    private SQLiteDatabase bd;
    

    public Bar_DAO( ) {
	}

	public void ouverture(Context ctx) {
        bd = new Bar_BDD(ctx, "bar.bd", null, 1).getWritableDatabase();
    }

    public void fermeture() {
        bd.close();
    }

	public Bar getBar(long l) {
	        Cursor curseur = bd.query("bar", null, "id = "+l, null,  null, null, null) ;
	        if (curseur.getCount()==0) return null;
	        curseur.moveToFirst();
	        Bar b = new Bar();
            b.setId(curseur.getInt(0));
            b.setName(curseur.getString(1));
            b.setPosFromToString(curseur.getString(2));
            b.setAdress(curseur.getString(3));
            b.setBeers(curseur.getString(4));
            b.setIdUpdate(curseur.getInt(5));
            b.setIdServer(curseur.getString(6));
	        curseur.close();
	        return b;
	}

	public long add(Bar bar) {
		 ContentValues valeurs = new ContentValues();
	     valeurs.put("name", bar.getName());
	     valeurs.put("pos", GoogleMapTools.fromLatLng(bar.getPos()));
	     valeurs.put("adress", bar.getAdress());
	     valeurs.put("beers", bar.getBeers());
	     valeurs.put("idUpdate", bar.getIdUpdate());
	     valeurs.put("idServer", bar.getIdServer());
	     return bd.insert("bar", null, valeurs);
		
	}

	public long update(Bar bar) {
		ContentValues valeurs = new ContentValues();
		 valeurs.put("name", bar.getName());
	     valeurs.put("pos", bar.getPos().toString());
	     valeurs.put("adress", bar.getAdress());
	     valeurs.put("beers", bar.getBeers());
	     valeurs.put("idUpdate", bar.getIdUpdate());
	     valeurs.put("idServer", bar.getIdServer());
	     bd.update("bar", valeurs, "id = "+bar.getId(), null);
         return bar.getId();
	}
	
	public int updateFromIdServer(Bar bar) {
		ContentValues valeurs = new ContentValues();
		 valeurs.put("name", bar.getName());
	     valeurs.put("pos", GoogleMapTools.fromLatLng(bar.getPos()));
	     valeurs.put("adress", bar.getAdress());
	     valeurs.put("beers", bar.getBeers());
	     valeurs.put("idUpdate", bar.getIdUpdate());
	     valeurs.put("idServer", bar.getIdServer());
	     return bd.update("bar", valeurs, "idServer LIKE ? AND idUpdate<>?", new String[] { bar.getIdServer(), Integer.toString(bar.getIdUpdate())});// AND idUpdate != ? , Integer.toString(bar.getIdUpdate())
	}

	public void delete(Bar bar) {
		// TODO Auto-generated method stub
	}
	
	public ArrayList<Bar> getBars() {
		 ArrayList<Bar> liste = new ArrayList<Bar>();
	        Cursor curseur = bd.query("bar", null, null, null,  null, null, "name") ;
	        if (curseur.getCount()==0) 
	        	return liste;
	        curseur.moveToFirst();
	        do {
	        	Bar b = new Bar();
	            b.setId(curseur.getInt(0));
	            b.setName(curseur.getString(1));
	            b.setPosFromToString(curseur.getString(2));
	            b.setAdress(curseur.getString(3));
	            b.setBeers(curseur.getString(4));
	            b.setIdUpdate(curseur.getInt(5));
	            b.setIdServer(curseur.getString(6));
	            liste.add(b);
	        }
	        while (curseur.moveToNext());
	        	curseur.close();
	        return liste;
	}
}
