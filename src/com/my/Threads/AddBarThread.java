package com.my.Threads;

import android.widget.Toast;

import com.my.Beer.AddBarActivity;
import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;
import com.my.Tools.MyBeerServer;

public class AddBarThread extends Thread {

	Bar_DAO dAO;
	Bar b;
	private AddBarActivity activity;

	public AddBarThread(Bar_DAO dAO, Bar b, AddBarActivity activity) {
		this.dAO = dAO;
		this.b = b;
		this.activity=activity;
	}

	public void run() {
		System.out.println("AddBarThread running");
		try {
			String _idInServerBase = MyBeerServer.addBar(b);
			// System.out.println("[AddBarThread][Run][20] On a ajouter un bar avec un idserveur :"+_idInServerBase);
			b.setIdServer(_idInServerBase);
		} catch (Exception e) {
			// System.out.println("[AddBarThread][Run][23] Erreur base de donnÃ©e externe : "
			// + e.toString());
		}
		if(b.getIdServer() != null){
			dAO.add(b);
		}
		else{
			activity.runOnUiThread(new Runnable() {
			    public void run() {
			        Toast.makeText(activity, "Impossible de rajouter un bar, nous n'avons pas accès au serveur ! :(", Toast.LENGTH_SHORT).show();
			    }
			});
		}
		dAO.fermeture();
	}
}