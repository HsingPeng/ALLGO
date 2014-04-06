package cn.edu.njupt.allgo;


import java.util.ArrayList;
import java.util.List;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import cn.edu.njupt.allgo.logic.LoginLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logicImpl.LoginLogicImpl;
import cn.edu.njupt.allgo.service.PullService;
import cn.edu.njupt.allgo.vo.EventVo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class FirstACTIVIY extends BaseActivity implements RefreshInterFace{

	private LoginLogic loginLogic ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtiy_first);
		//启动后台服务
		Log.i("Debug", "启动Service");
		startService(new Intent(FirstACTIVIY.this,
                PullService.class));
		loginLogic = new LoginLogicImpl(this,this) ;
		//判断是否已经登录
		loginLogic.isLogin();
		
	}


	@Override
	public void refresh(Object result, int kind) {
		switch(kind) {
		case 1 :
			Intent intent = new Intent(FirstACTIVIY.this,HomeACTIVITY.class);
			startActivity(intent);
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			finish();
			break;
		case 2:
			Intent intent1 = new Intent(FirstACTIVIY.this,WelcomeACTIVITY.class);
			startActivity(intent1);
			overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			finish();
			break;
		}
		
	}
	
	

}
