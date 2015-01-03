package com.my.Threads;

import com.my.Entity.Bar;
import com.my.Tools.Bar_DAO;

public class UpdateDaoThread extends Thread {
	Bar_DAO dAO;
	Bar b;
    public UpdateDaoThread(Bar_DAO dAO, Bar b) {
		this.dAO=dAO;
		this.b=b;
	}
    
	public void run(){
		
	}
}
