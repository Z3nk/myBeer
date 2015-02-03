package com.my.Controllers;

import java.io.IOException;

import com.my.Entity.Bar;
import com.my.Threads.AddBarThread;
import com.my.Tools.Bar_DAO;
import com.my.Tools.MyBeerServer;

public class BarController {

	public static boolean addBar(Bar_DAO DAO, Bar b)
	{
		new AddBarThread(DAO,b).start();
		return true;
	}

	public static boolean updateBar(Bar_DAO DAO, Bar b) {
		b.addIdUpdate();
		try {
			MyBeerServer.updateBar(b);
		} catch (IOException e) {
			System.out.println("[BarController][updateBar][23] Erreur base de donnée externe : " + e.toString());
		}
		DAO.update(b);
		DAO.fermeture();
		return true;
	}
}
