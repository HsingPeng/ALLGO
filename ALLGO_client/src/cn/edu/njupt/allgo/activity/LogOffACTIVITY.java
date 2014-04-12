package cn.edu.njupt.allgo.activity;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.logic.LoginLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logicImpl.LoginLogicImpl;
import cn.edu.njupt.allgo.service.PullService;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class LogOffACTIVITY extends BaseActivity implements RefreshInterFace{

	private LoginLogic loginLogic ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_processdialog);
		Intent intent = this.getIntent();
		loginLogic = new LoginLogicImpl(this,this);
		switch(intent.getIntExtra("action", 0)){
		case 0:
		loginLogic.logOff();
		break;
		case 1:
			loginLogic.logOffLocal();
		break;
		}
	}
	
	private void logOff() {
				loginLogic.logOffDB();
                Intent intent = new Intent();
                intent.setAction("cn.edu.njupt.allgo.HomeACTIVITY");
                intent.putExtra("action", 0);
                sendBroadcast(intent);
                Intent intent1 = new Intent(LogOffACTIVITY.this,WelcomeACTIVITY.class);
       			startActivity(intent1);
       			Intent intent2 = new Intent(this,PullService.class);
       			intent2.putExtra("action", 2);
       			startService(intent2);
        		finish();
	}

	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1 :
			Toast.makeText(LogOffACTIVITY.this, "下线成功", Toast.LENGTH_SHORT).show();
			logOff();
			break;
		case -1 :
			Toast.makeText(LogOffACTIVITY.this, (String)result, Toast.LENGTH_SHORT).show();
			logOff();
			break;
		}
	}
	
}
