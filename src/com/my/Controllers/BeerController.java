package com.my.Controllers;

import com.my.Entity.Beer;
import com.my.Threads.AddBeerThread;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;

public class BeerController {
	public static boolean addBeer(Beer_DAO DAO, Beer b, long idBar,
			Bar_DAO barDAO) {
		new AddBeerThread(DAO, b, idBar, barDAO).start();
		return true;
	}
}
