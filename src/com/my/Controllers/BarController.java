package com.my.Controllers;

import java.io.IOException;

import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;
import com.my.Tools.MyBeerServer;

public class BarController {

	public static boolean addBar(Bar_DAO DAO, Bar b)
	{
		long idInLocalBase=DAO.add(b);
		b.setId(idInLocalBase);
		try {
			MyBeerServer.addBar(b);
		} catch (IOException e) {
			System.out.println("t es qu un gros con : " + e.toString());
		}
		return true;
	}

	public static void updateBar(Bar_DAO DAO, Bar b) {
		long idInLocalBase=DAO.update(b);
		b.setId(idInLocalBase);
		try {
			MyBeerServer.addBar(b);
		} catch (IOException e) {
			System.out.println("t es qu un gros con : " + e.toString());
		}
		return true;
	}
}
