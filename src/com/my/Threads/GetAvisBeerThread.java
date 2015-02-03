package com.my.Threads;

import android.widget.TextView;

import com.my.Entity.Bar;
import com.my.Entity.Beer;
import com.my.Tools.Bar_DAO;
import com.my.Tools.Beer_DAO;
import com.my.Tools.MyBeerServer;

public class GetAvisBeerThread extends Thread {

	Beer_DAO dAO;
	Beer b;
	private TextView content;
    public GetAvisBeerThread(Beer_DAO dAO, Beer b, TextView content) {
		this.dAO=dAO;
		this.b=b;
		this.content=content;
	}

	public void run(){
		synchronized(content) {
			try{
				content.setText("toto");
			}catch(Exception e){
				
			}
		}
    }
  }
