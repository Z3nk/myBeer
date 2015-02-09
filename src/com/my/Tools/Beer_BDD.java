package com.my.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Beer_BDD extends SQLiteOpenHelper {
	public Beer_BDD(Context context, String name,
			SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE beer ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT NOT NULL," + "type Text NOT NULL,"
				+ "prix TEXT NOT NULL," + "pourcentAlcool TEXT NOT NULL,"
				+ "fiche TEXT NOT NULL," + "idUpdate INTEGER,"
				+ "idServer TEXT NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int ancienneVersion,
			int nouvelleVersion) {
		db.execSQL("DROP TABLE beer;");
		onCreate(db);
	}
}
