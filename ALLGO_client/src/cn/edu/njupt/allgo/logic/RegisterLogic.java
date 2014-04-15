package cn.edu.njupt.allgo.logic;

import java.io.File;

import android.graphics.Bitmap;

public interface RegisterLogic {
	
	/**
	 * 注册
	 * @param uname
	 * @param usex
	 * @param uemail
	 * @param upassword
	 * @param avatar 
	 */
	public void register(String uname ,int usex ,String uemail ,String upassword, Bitmap avatar );
	
}
