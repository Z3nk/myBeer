package com.my.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Bar_BDD extends SQLiteOpenHelper {

	public Bar_BDD(Context context, String name,
			SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE bar ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "name TEXT NOT NULL," 
				+ "pos Text NOT NULL,"
				+ "adress TEXT NOT NULL,"
				+ "beers TEXT NOT NULL,"
				+ "idUpdate INTEGER,"
				+ "idServer TEXT NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int ancienneVersion,
			int nouvelleVersion) {
		db.execSQL("DROP TABLE bar;");
		onCreate(db);
	}

}
