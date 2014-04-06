package cn.edu.njupt.allgo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class BaseActivity extends FragmentActivity {

	private CloseReceiver closeReceiver;
	
	public class CloseReceiver extends BroadcastReceiver
	{   
	       @Override  
	       public void onReceive(Context context, Intent intent)  
	       {   
	    	   int action = intent.getIntExtra("action", -1);
	    	   switch(action){
	    	   case 0:			//关闭activiy信号
	    		finish();
	    		break;
	    	}
	       }  
	   }  
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(closeReceiver);
		super.onDestroy();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		closeReceiver = new CloseReceiver();  
		IntentFilter intentFilter = new IntentFilter("cn.edu.njupt.allgo.HomeACTIVITY");  
		registerReceiver(closeReceiver, intentFilter);

	}
}
