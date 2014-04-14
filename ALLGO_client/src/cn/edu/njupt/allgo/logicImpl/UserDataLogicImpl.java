package cn.edu.njupt.allgo.logicImpl;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import cn.edu.njupt.allgo.application.MyDeclare;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UserDataLogic;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.UserDataVo;

public class UserDataLogicImpl implements UserDataLogic {

	private Context context ;
	private RefreshInterFace refresh ;
	private MyDeclare declare;
	private UserDataVo userdata;
	
	public UserDataLogicImpl (Context context , RefreshInterFace refresh){
		this.context = context;
		this.refresh = refresh;
		declare =(MyDeclare)context.getApplicationContext();
	}

	/**
	 * 从本地获取UserData
	 * @param context
	 * @return
	 */
	@Override
	public void initUserData() {
		userdata = new UserDataVo();
		SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		userdata.setUid(sharedPref.getInt("uid", -1));
		userdata.setUname(sharedPref.getString("uname", ""));
		userdata.setUsatement(sharedPref.getString("usatement", ""));
		userdata.setUsex(sharedPref.getInt("usex", 1));
		userdata.setUemail(sharedPref.getString("uemail", ""));
		userdata.setUbirthday(sharedPref.getString("ubirthday", ""));
		userdata.setUregdate(sharedPref.getString("uregdate", ""));
		userdata.setUaddress(sharedPref.getString("uaddress", ""));
		refresh.refresh(userdata, 1);
	}

	/**
	 * 从网络获取UserData
	 * @param context
	 * @return
	 */
	@Override
	public void getUserData() {

        NetUtil netUtil = new NetUtil("user/data", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("userdata")){
						 userdata = JSON.parseObject(jsonObject.getString("userdata") , UserDataVo.class);
						refresh.refresh(userdata , 2);
         	        }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		netUtil.add("uid", sharedPref.getInt("uid", -1) + "");
		netUtil.get();
		
	}

	@Override
	public void saveUserData() {
		SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor = sharedPref.edit();
		 editor.putString("uname", userdata.getUname());
		 editor.putString("usatement", userdata.getUsatement());
		 editor.putInt("usex", userdata.getUsex());
		 editor.putString("uemail", userdata.getUemail());
		 editor.putString("ubirthday", userdata.getUbirthday());
		 editor.putString("uregdate", userdata.getUregdate());
		 editor.putString("uaddress", userdata.getUaddress());
		 editor.commit();
		
	}

}
