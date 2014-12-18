package com.my.Tools;

import com.my.Entity.Bar;

import android.content.ContentValues;
import android.content.Context;
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

	public Bar getBar(int int1) {
		// TODO Auto-generated method stub
		return null;
	}

	public long add(Bar bar) {
		 ContentValues valeurs = new ContentValues();
	     valeurs.put("name", bar.getName());
	     valeurs.put("pos", bar.getPos().toString());
	     valeurs.put("adress", bar.getAdress());
	     valeurs.put("beers", bar.getBeers());
	     valeurs.put("idUpdate", bar.getIdUpdate());
	     return bd.insert("bar", null, valeurs);
		
	}

	public long update(Bar bar) {
		ContentValues valeurs = new ContentValues();
		 valeurs.put("name", bar.getName());
	     valeurs.put("pos", bar.getPos().toString());
	     valeurs.put("adress", bar.getAdress());
	     valeurs.put("beers", bar.getBeers());
	     valeurs.put("idUpdate", bar.getIdUpdate());
	     bd.update("bar", valeurs, "id = "+bar.getId(), null);
         return bar.getId();
	}

	public void delete(Bar bar) {
		// TODO Auto-generated method stub
		
	}
}
