package com.my.Threads;

import java.io.IOException;

import android.widget.Toast;

import com.my.Beer.AddBeerActivity;
import com.my.Entity.Bar;
import com.my.Entity.Beer;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;
import com.my.Tools.MyBeerServer;

public class AddBeerThread extends Thread {
	Beer_DAO dAO;
	Beer b;
	long idBar;
	Bar_DAO daoBar;
	AddBeerActivity activity;

	public AddBeerThread(Beer_DAO dAO, Beer b, long idBar, Bar_DAO daoBar, AddBeerActivity activity) {
		this.dAO = dAO;
		this.b = b;
		this.idBar = idBar;
		this.daoBar = daoBar;
		this.activity=activity;
	}

	public void run() {
		System.out.println("AddBeerThread running");
		try {
			String _idInServerBase = MyBeerServer.addBeer(b);
			// System.out.println("[AddBeerThread][Run][18] On a ajouté un bar avec un idserveur :"+
			// _idInServerBase);
			b.setIdServer(_idInServerBase);
		} catch (Exception e) {
			// System.out.println("[AddBeerThread][Run][22] Erreur base de donnée externe : "+
			// e.toString());
		}
		if (b.getIdServer() != null) {
			b.setId(dAO.add(b));
			Bar b2 = this.daoBar.getBar(idBar);
			b2.addBeers(b.getIdServer());
			this.daoBar.update(b2);
			try {
				MyBeerServer.updateBar(b2);
			} catch (IOException e) {
				// System.out.println("[AddBeerThread][Run][39] Erreur update bar avec le serveur externe");
				e.printStackTrace();
			} finally {
				dAO.fermeture();
				daoBar.fermeture();
			}

		}
		else{
			activity.runOnUiThread(new Runnable() {
			    public void run() {
			        Toast.makeText(activity, "Impossible de rajouter une bi�re, nous n'avons pas acc�s au serveur ! :(", Toast.LENGTH_SHORT).show();
			    }
			});
		}

	}
}
