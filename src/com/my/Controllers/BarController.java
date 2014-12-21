package com.my.Controllers;

import java.io.IOException;

import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;
import com.my.Tools.MyBeerServer;

public class BarController {

	public static boolean addBar(Bar_DAO DAO, Bar b)
	{
		try {
			String _idInServerBase = MyBeerServer.addBar(b);
			b.setIdServer(_idInServerBase);
		} catch (IOException e) {
			System.out.println("[addBar] Erreur base de donnée externe : " + e.toString());
		}
		DAO.add(b);
		return true;
	}

	public static boolean updateBar(Bar_DAO DAO, Bar b) {
		b.addIdUpdate();
		try {
			MyBeerServer.updateBar(b);
		} catch (IOException e) {
			System.out.println("[updateBar] Erreur base de donnée externe : " + e.toString());
		}
		DAO.update(b);
		return true;
	}
}
