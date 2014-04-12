package cn.edu.njupt.allgo.logicImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.njupt.allgo.activity.EventPageACTIVITY;
import cn.edu.njupt.allgo.application.MyDeclare;
import cn.edu.njupt.allgo.logic.LoginLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.util.ArrayListUtil;
import cn.edu.njupt.allgo.util.MD5;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.CommonEventVo;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.FriendEventVo;
import cn.edu.njupt.allgo.vo.MyEventVo;
import cn.edu.njupt.allgo.vo.PastEventVo;
import cn.edu.njupt.allgo.vo.UnreadVo;
import cn.edu.njupt.allgo.vo.UserDataVo;

public class LoginLogicImpl implements LoginLogic {

	private Context context ;
	private RefreshInterFace refresh ;
	private UserDataVo userData = null;
	private HttpUtils http = null;
	private boolean flag = true;
	private MyDeclare declare;
	
	
	public LoginLogicImpl (Context context , RefreshInterFace refresh) {
		this.context = context ;
		this.refresh = refresh ;
		declare =(MyDeclare)context.getApplicationContext();
	}
	/**
	 * 登录
	 * @param context  this
	 * @param name
	 * @param password
	 * @return
	 */
	@Override
	public void login(String name , String password) {
        loginNet(name , password);
        
        
	}

	//联网登录
	private void loginNet(String name, String password) {
		NetUtil netUtil = new NetUtil("login", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					if(jsonObject.getString("response").equals("login")){
					 userData = JSON.parseObject(jsonObject.getString("userdata") , UserDataVo.class);
					 
					 String session = jsonObject.getString("SessionId");
					 SharedPreferences sharedPref = context.getSharedPreferences("appdata",Context.MODE_PRIVATE);
					 SharedPreferences.Editor editor = sharedPref.edit();
					 editor.putString("SessionId", session);
					 editor.commit();
					 loginSave();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
		netUtil.add("uname", name);
		netUtil.add("upassword", MD5.digest(password));
		http = netUtil.getHttp();
		netUtil.post();
	}
	
	public void loginSave(){
		if(userData != null){
        	
    		isFirstRun(userData);
	        //登录成功将用户数据存入SharedPreferences
    		SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            Log.i("DB", "UID==>" + userData.getUid());
	        editor.putInt("uid", userData.getUid());
	        editor.putString("uname", userData.getUname());
	        editor.putString("upassword", userData.getUpassword());
	        editor.putString("uemail", userData.getUemail());
	        editor.putInt("usex", userData.getUsex());
	        editor.putString("uhead", userData.getUhead());
	        editor.putString("ubirthday", userData.getUbirthday());
	        editor.putString("uregdate", userData.getUregdate());
	        editor.putString("uaddress", userData.getUaddress());
	        editor.putString("usatement", userData.getUsatement());
	        editor.commit();
	        refresh.refresh(true, 1);
    	}
	}
	
	/**
	 * 如果是某个用第一次运行，则初始化数据库，模拟些数据出来
	 */
	private void isFirstRun(UserDataVo userData) {
		if(getFlag(userData)){
		initCommonDateBase(userData);
		}
	}
	
	//SharedPreferences里的值为true，则是运行过的
	private boolean getFlag(UserDataVo userData) {
		boolean flag = false ;
		SharedPreferences sharedPref = this.context.getSharedPreferences("appdata",Context.MODE_PRIVATE);
		if(!sharedPref.getBoolean(userData.getUid() + "", false)){
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putBoolean(userData.getUid() + "", true) ;
			editor.commit();
			flag = true ;
		}
		return flag;
	}

	//模拟生成commonevent数据
	private void initCommonDateBase(UserDataVo userData) {

		
		
		/*try{
		    DbUtils db = DbUtils.create(context,userData.getUid() + ".db");
		    db.configAllowTransaction(true);
	        db.configDebug(true);
	        db.save(new CommonEventVo(11002617,"去栖霞山爬山",123456,"千军万马1",
	    			"Mon Feb 15 08:00:00 GMT+08:00 2014","","去栖霞山爬山","栖霞山",
	    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
	    			0,0));
	        db.save(new CommonEventVo(11002618,"去栖霞山爬山",123458,"千军万马2",
	    			"Mon Feb 15 08:00:00 GMT+08:00 2014","","去栖霞山爬山","栖霞山",
	    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
	    			0,0));
	        db.save(new FriendEventVo(11002620,"去栖霞山爬山",123459,"千军万马4",
	    			"Mon Feb 15 08:00:00 GMT+08:00 2014","","去栖霞山爬山","栖霞山",
	    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
	    			0,0));
	        db.save(new MyEventVo(11002620,"去栖霞山爬山",123459,"千军万马4",
	    			"Mon Feb 15 08:00:00 GMT+08:00 2014","","去栖霞山爬山","栖霞山",
	    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
	    			0,0));
	        db.save(new PastEventVo(11002620,"去栖霞山爬山",123459,"千军万马4",
	    			"Mon Feb 15 08:00:00 GMT+08:00 2014","","去栖霞山爬山","栖霞山",
	    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
	    			0,0));
	        db.save(new UnreadVo(554322440, 123456, 06, 223232323, 03,
	        		"完美猫：好的好的！", "Mon Feb 13 8:45:00 GMT+08:00 2013"));

	        
		}catch(DbException e){
	    	Log.e("DB", "error :" + e.getMessage() + "\n");
	    }*/
	
	}
	
	/**
	 * 取消登录
	 * @return
	 */
	@Override
	public void cancelLogin() {
		if(http != null){
		flag = false ;
		http.getHttpClient().getConnectionManager().shutdown();
		refresh.refresh("取消登录", -1);
		}
	}

	/**
	 * 检查是否已经登录
	 * @return
	 */
	@Override
	public void isLogin() {
		


		new AsyncTask<Void, Void, Void>() {
			
            @Override
            protected Void doInBackground(Void... params) {
            	try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
            	SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
        		if(sharedPref.getInt("uid", -1) != -1){
        			refresh.refresh(null, 1);		//已经登录
        		}else{
        		refresh.refresh(null, 2);
        		}
        		SharedPreferences shared = context.getSharedPreferences("appdata",Context.MODE_PRIVATE);
        		String host_url = shared.getString("host_url", "http://10.0.2.2:8080/ALLGO_server/");
        		MyDeclare declare =(MyDeclare)context.getApplicationContext();
        		declare.setHost_url(host_url);
				return null;
    		}
        }.execute();

	}

	/**
	 * 下线
	 */
	@Override
	public void logOff() {
		loginOffNet();
	}
	
	public void logOffDB(){
		SharedPreferences sharedPref1 = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor1 = sharedPref1.edit();
		editor1.clear() ;
		editor1.commit();
		SharedPreferences sharedPref2 = context.getSharedPreferences("appdata",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor2 = sharedPref2.edit();
		editor2.putString("SessionId", "");
		editor1.commit();
	}

	private void loginOffNet() {

        NetUtil netUtil = new NetUtil("logout", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("logout")){
						refresh.refresh(null, 1);
         	        }else{
         	        	refresh.refresh("联网出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		int uid = sharedPref.getInt("uid", -1) ;
		String uname = sharedPref.getString("uname", null) ;
		String upassword = sharedPref.getString("upassword", null) ;
		netUtil.add("uid", uid + "");
		netUtil.add("uname", uname);
		netUtil.add("upassword", upassword);
		netUtil.post();
		
	}
	
	/**
	 * 设置服务器地址
	 */
	@Override
	public void setURL() {
		MyDeclare declare =(MyDeclare)context.getApplicationContext();
		final EditText edit  = new EditText(context);
			edit.setText(declare.getHost_url());
	   	  new AlertDialog.Builder(context)
	        .setTitle("修改服务器地址")
	        .setView(edit)
	        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	                //EventPageACTIVITY.this.sendAdd(edit.getText().toString());
	            	SharedPreferences sharedPref = context.getSharedPreferences("appdata",Context.MODE_PRIVATE);
	                SharedPreferences.Editor editor = sharedPref.edit();
	                MyDeclare declare =(MyDeclare)context.getApplicationContext();
	                declare.setHost_url(edit.getText().toString());
	                editor.putString("host_url", edit.getText().toString());
	                editor.commit();
	            }
	        })
	        .setNegativeButton("取消", null)
	        .show();
		
	}
	@Override
	public void logOffLocal() {
		refresh.refresh("已下线", -1);
	}

}
