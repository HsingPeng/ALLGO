package cn.edu.njupt.allgo.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.edu.njupt.allgo.activity.LogOffACTIVITY;
import cn.edu.njupt.allgo.application.MyDeclare;
import cn.edu.njupt.allgo.logic.RefreshInterFace;

public class NetUtil {

	private Map<String,String> myParams;
	private RequestParams params;
	private String uri;
	private RefreshInterFace refresh;
	private NetCallBack callback;
	private HttpUtils http;
	private MyDeclare declare;
	private Context context;
	private JSONObject jsonObject;
	private String TAG = "Http";
	
	/**
	 * 初始化
	 * @param uri
	 * @param refresh
	 * @param context
	 * @param callback
	 */
	public NetUtil(String uri, RefreshInterFace refresh, Context context,NetCallBack callback) {
		super();
		this.uri = uri;
		this.refresh = refresh;
		this.context = context;
		this.callback = callback;
		declare =(MyDeclare)context.getApplicationContext();
		myParams= new HashMap<String,String>();
		params = new RequestParams();
	}

	/**
	 * 发送post请求
	 */
	public void post(){
		for (Map.Entry<String, String> entry : this.myParams.entrySet()) {
			   params.addBodyParameter(entry.getKey(), entry.getValue());
			  }
		if(isNetworkConnected(context)){
			send(HttpRequest.HttpMethod.POST,params);
		}else{
			refresh.refresh("没有联网", -1);
		}
	}
	
	/**
	 * 发送get请求
	 */
	public void get(){
		for (Map.Entry<String, String> entry : this.myParams.entrySet()) {
			   params.addQueryStringParameter(entry.getKey(), entry.getValue());
			  }
		if(isNetworkConnected(context)){
			send(HttpRequest.HttpMethod.GET,params);
		}else{
			refresh.refresh("没有联网", -1);
		}
	}

	private void send(HttpRequest.HttpMethod arg0,RequestParams params){
		SharedPreferences sharedPref = context.getSharedPreferences("appdata",Context.MODE_PRIVATE);
		params.addHeader("Cookie","JSESSIONID="+sharedPref.getString("SessionId", ""));
		http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(1000 * 1);
	    http.send(arg0,
	    		declare.getHost_url() + uri,
	    		params,
	            new RequestCallBack<String>() {
	
					@Override
	                public void onStart() {
	                	Log.i(TAG ,"onStart" ) ;
	                }
					
	                @Override
	                public void onSuccess(ResponseInfo<String> responseInfo) {
	                	Log.i(TAG, "Http==>"+responseInfo.result);
						try {
							jsonObject = new JSONObject(responseInfo.result);
							 if(jsonObject.getString("response").equals("error")){
								refresh.refresh(jsonObject.getJSONObject("error").getString("text"), -1);
							 }else if(jsonObject.getString("response").equals("notlogin")){
								 Toast.makeText(context, "登录过期,请重新登录", Toast.LENGTH_SHORT).show();
								 Intent intent = new Intent(context,LogOffACTIVITY.class);
								 intent.putExtra("action", 1);
								 context.startActivity(intent);
							 }else {
								 callback.getResult(jsonObject);
							 }
						} catch (JSONException e) {
							e.printStackTrace();
						}
	                }
	                @Override
	                public void onFailure(HttpException error, String msg) {
	                	Log.i(TAG ,"error==>" +  msg ) ;
	                	refresh.refresh(msg, -1);
	                }
	            });
	}
	
	/**
	 * 添加params
	 * @param key
	 * @param value
	 */
	public void add(String key,String value){
		myParams.put(key, value);
	}
	
	/**
	 * 设置请求位置
	 * @param uri
	 */
	public void setURI(String uri){
		this.uri = uri;
	}
	
	/**
	 * 得到params
	 * @return
	 */
	public RequestParams getParams(){
		return params;
	}
	
	/**
	 * 添加文件
	 * @param key
	 * @param file
	 */
	public void addFile(String key, File file){
		params.addBodyParameter(key, file);
	}
	
	/**
	 * 添加输入流
	 * @param key
	 * @param stream
	 * @param length
	 */
	public void addStream(String key , InputStream stream , long length){
		params.addBodyParameter(key, stream, length);
	}
	
	/**
	 * 得到HttpUtils
	 * @return
	 */
	public HttpUtils getHttp(){
		return http;
	}
	
	public static abstract class NetCallBack{
		
		public NetCallBack(){
			
		}
		
		public abstract void getResult(JSONObject jsonObject);
		
		/*public <T>Object getEntity(String tag,Class<T> vo){
			Object result = null;
			try {
				result = JSON.parseObject(jsonObject.getString(tag) , vo);
			} catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return result;
		}*/
		
	}
	
	/**
	 * 监测联网状态
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) { 
		if (context != null) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
		.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
		if (mNetworkInfo != null) { 
		return mNetworkInfo.isAvailable(); 
		} 
		} 
		return false; 
		} 
	
}
