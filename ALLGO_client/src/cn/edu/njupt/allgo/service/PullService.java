package cn.edu.njupt.allgo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.edu.njupt.allgo.HomeACTIVITY;
import cn.edu.njupt.allgo.LogOffACTIVITY;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.application.MyDeclare;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.vo.UnreadVo;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketOptions;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;



public class PullService extends Service{

	
	private static final String TAG = "Service" ;
	private boolean isRuning = false ;		//程序是否运行
	private MyDeclare declare;
	private NotificationManager mNotificationManager;
	private WebSocketConnection wsc = new WebSocketConnection();;
	
		class pullThread implements Runnable {

			@Override
			public void run() {
				
				pullWebSocket();		//WebSocket连接
				
				while(isRuning){
					if(NetUtil.isNetworkConnected(PullService.this)&&!wsc.isConnected()){
						wsc.reconnect();
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				if(wsc.isConnected()){
					wsc.disconnect();
				}
			Log.i(TAG, "run方法结束");
			}
			
		}
	
		@Override
		public IBinder onBind(Intent intent) {
			// TODO 自动生成的方法存根
			return null;
		}
		
		@Override
		public void onCreate() {
			// TODO 自动生成的方法存根
			super.onCreate();
			Log.i(TAG,"onCreate===>PullService");
			IntentFilter filter = new IntentFilter();
	        filter.addAction("cn.edu.njupt.allgo.service.PullService");  
	        registerReceiver(TimeReceiver, filter);
	        declare =(MyDeclare)this.getApplicationContext();
		}

		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			// TODO 自动生成的方法存根
			int action = intent.getIntExtra("action", 0);
			Log.i(TAG,"onStartCommand==>PullService==>"+action);
			switch(action){
			case 1:		//用户登录开始实时推送
				isRuning = true;
				stopAlarm();
				pullAction();
				break;
			case 2:		//用户退出
				isRuning = false ;
				if(wsc.isConnected()){
					wsc.disconnect();
				}
				break;
			case 3:		//开启后台推送
				startAlarm();
				break;
			case 4:		//关闭后台推送
				stopAlarm();
				break;
			}
			return START_STICKY;
		}

		
		//开启实时推送
		private void pullAction(){
			new Thread(new pullThread()).start();
		}
		
		private void pullWebSocket(){
			// TODO 使用WebSocket实时推送
			SharedPreferences sharedPref = this.getSharedPreferences("appdata",Context.MODE_PRIVATE);
			List<BasicNameValuePair> headers = new ArrayList<BasicNameValuePair>();
			headers.add(new BasicNameValuePair("Cookie","JSESSIONID="+sharedPref.getString("SessionId", "")));
			/*SharedPreferences sharedPref1 = this.getSharedPreferences("userdata",Context.MODE_PRIVATE);
			headers.add(new BasicNameValuePair("uid",sharedPref1.getInt("uid", -1)+""));*/
			try {
				wsc.connect("ws"+declare.getHost_url().substring(4)+"pull.ws", null, new WebSocketConnectionHandler(){

					@Override
					public void onClose(int code, String reason) {
						Log.i(TAG, "onClose reason="+reason);
					}

					@Override
					public void onOpen() {
						Log.i(TAG, "后台推送已连接");
					}

					//接收文本消息
					@Override
					public void onTextMessage(String payload) {
						readingParse(payload);
					}
				}, new WebSocketOptions(), headers);
			} catch (WebSocketException e) {
				e.printStackTrace();
			}
		}

		//解析收到的String数据
		private void readingParse(String reading) {
			Log.i(TAG, "Service==>"+reading);
				JSONObject jsonObject;
				try {
					jsonObject = new JSONObject(reading);
					 if(jsonObject.getString("response").equals("remind_unread")){
						 SharedPreferences sharedPref = this.getSharedPreferences("userdata",Context.MODE_PRIVATE);
							int uid = sharedPref.getInt("uid", -1) ;
							List<UnreadVo> unreadDate = new ArrayList<UnreadVo>();
					    	try{
							    DbUtils db = DbUtils.create(this,uid + ".db");
							    db.configAllowTransaction(true);
						        db.configDebug(true);
						        unreadDate = JSON.parseArray(jsonObject.getString("remind_unread") , UnreadVo.class);
						        	db.saveAll(unreadDate);
						        	Log.i(TAG, "db.saveAll(unreadDate)==>"+unreadDate.toString());
						        }catch(DbException e){
							    	Log.e("DB", "error :" + e.getMessage() + "\n");
							    }
					    	if(unreadDate.size() > 0){
								 Intent intent = new Intent(this,HomeACTIVITY.class);
						         intent.putExtra("action", 1);
						         notifications(intent);
					    	}
	    	        }else if(jsonObject.getString("response").equals("notlogin")){
	    	        	//把登录过期的消息发给HomeACTIVITY，由它处理
	    	        	Intent intent = new Intent();
	                    intent.setAction("cn.edu.njupt.allgo.HomeACTIVITY");
	                    intent.putExtra("action", 1);
	                    sendBroadcast(intent);
	    	        }else if(jsonObject.getString("response").equals("error")){
	    	        		Toast.makeText(this, jsonObject.getJSONObject("error").getString("text"), Toast.LENGTH_SHORT).show();
	    	        }
			} catch (JSONException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}	
		}
		
		public void notifications(Intent intent){
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(this)
			        .setSmallIcon(R.drawable.ic_launcher)
			        .setAutoCancel(true)
			        .setContentTitle("有新消息");
			PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT) ;
			mBuilder.setContentIntent(resultPendingIntent);
			mNotificationManager =
			    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(1001, mBuilder.build());
		}

		@Override
		public void onDestroy() {
			// TODO 自动生成的方法存根
			Log.i(TAG,"onDestroy==>PullService");
			stopAlarm();
			unregisterReceiver(TimeReceiver);
			super.onDestroy();
		}
		
		public BroadcastReceiver TimeReceiver = new BroadcastReceiver() {  
	        @Override  
	        public void onReceive(Context context, Intent intent) { 
	            String action = intent.getAction();
	            Log.i(TAG, "getAction==>TimeReceiver" );
	            int message = intent.getIntExtra("message", 0);
	            if (action.equals("cn.edu.njupt.allgo.service.PullService")||action=="cn.edu.njupt.allgo.service.PullService") {  
	                 takeAction(message);
	            }  
	        }  
	    };
	    
	    //broadcast的动作
	    private void takeAction(int message){
			switch(message){
				case 1:		//轮询一次
					if(NetUtil.isNetworkConnected(this)){
						getOnePull();
					}
				break;
			}
		}

	    //后台轮询一次数据
	    private void getOnePull() {
			SharedPreferences sharedPref = this.getSharedPreferences("userdata",Context.MODE_PRIVATE);
			RequestParams params = new RequestParams();
	        params.addQueryStringParameter("uid", sharedPref.getInt("uid", -1) + "");
	        SharedPreferences sharedPref1 = this.getSharedPreferences("appdata",Context.MODE_PRIVATE);
			params.addHeader("Cookie","JSESSIONID="+sharedPref1.getString("SessionId", ""));
	        
	        HttpUtils http = new HttpUtils();
	        http.send(HttpRequest.HttpMethod.GET,
	        		declare.getHost_url() + "remind/unread",
	                params,
	                new RequestCallBack<String>() {

						@Override
	                    public void onStart() {
	                    	Log.i(TAG ,"Pull==>onStart" ) ;
	                    }

	                    @Override
	                    public void onLoading(long total, long current, boolean isUploading) {
	                    }

	                    @Override
	                    public void onSuccess(ResponseInfo<String> responseInfo) {
							readingParse(responseInfo.result);
	                    }
	                    @Override
	                    public void onFailure(HttpException error, String msg) {
	                    	Log.i(TAG ,"Pull==>error==>" +  msg ) ;
	                    }
	                });
		}

		private void stopAlarm(){
			AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
			Intent intent =new Intent("cn.edu.njupt.allgo.service.PullService");
	        PendingIntent sender=PendingIntent
	            .getBroadcast(PullService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	        alarm.cancel(sender);
	        Log.i(TAG, "PullService==>alarm.cancel" );
		}

		private void startAlarm(){
			Intent intent =new Intent("cn.edu.njupt.allgo.service.PullService");
			intent.putExtra("message", 1);
	        PendingIntent sender=PendingIntent
	            .getBroadcast(PullService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	        
	        //开始时间
	        long firstime=SystemClock.elapsedRealtime();

	        AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
	    	//3分钟一个周期，不停的发送广播
	        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP
	                , firstime, 1*20*1000, sender);
	        Log.i(TAG, "PullService==>startAlarm" );
		}

	}  
 
