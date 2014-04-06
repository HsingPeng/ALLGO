package cn.edu.njupt.allgo.application;

import android.app.Application;

public class MyDeclare extends Application {

	private String host_url = "" ;

	public String getHost_url() {
		return host_url;
	}

	public void setHost_url(String host_url) {
		this.host_url = host_url;
	}
	
	
	
}
