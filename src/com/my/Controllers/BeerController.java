package com.my.Controllers;

import java.io.IOException;

import com.my.Entity.Bar;
import com.my.Entity.Beer;
import com.my.Threads.AddBarThread;
import com.my.Threads.AddBeerThread;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;
import com.my.Tools.MyBeerServer;

public class BeerController {
	public static boolean addBeer(Beer_DAO DAO, Beer b, long idBar, Bar_DAO barDAO)
	{
		new AddBeerThread(DAO,b,idBar, barDAO).start();
		return true;
	}

	/*public static boolean updateBar(Beer_DAO DAO, Beer b) {
		b.addIdUpdate();
		try {
			MyBeerServer.updateBeer(b);
		} catch (IOException e) {
			System.out.println("[BarController][updateBar][23] Erreur base de donnée externe : " + e.toString());
		}
		DAO.update(b);
		return true;
	}*/
}
