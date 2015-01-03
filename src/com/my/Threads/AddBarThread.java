package com.my.Threads;

import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;
import com.my.Tools.MyBeerServer;

public class AddBarThread extends Thread {

	Bar_DAO dAO;
	Bar b;
    public AddBarThread(Bar_DAO dAO, Bar b) {
		this.dAO=dAO;
		this.b=b;
	}

	public void run(){
       System.out.println("AddBarThread running");
       try {
			String _idInServerBase = MyBeerServer.addBar(b);
			b.setIdServer(_idInServerBase);
		} catch (Exception e) {
			System.out.println("[addBar] Erreur base de donnée externe : " + e.toString());
		}
       dAO.add(b);
    }
  }