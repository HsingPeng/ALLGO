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
import cn.edu.njupt.allgo.logic.RegisterLogic;
import cn.edu.njupt.allgo.util.MD5;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.UserDataVo;

public class RegisterLogicImpl implements RegisterLogic {
	private Context context ;
	private RefreshInterFace refresh ;
	private MyDeclare declare;
	
	public RegisterLogicImpl(Context context , RefreshInterFace refresh){
		this.context = context ;
		this.refresh = refresh ;
		declare =(MyDeclare)context.getApplicationContext();
	}
	
	@Override
	public void register(String uname, int usex, String uemail,
			String upassword) {

        NetUtil netUtil = new NetUtil("register", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("register")){
						 //Log.i("DB","success");
						refresh.refresh(jsonObject.getJSONObject("userinfo").getString("uname") , 1);
         	        }else{
         	        	refresh.refresh("注册出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        netUtil.add("uname", uname);
        netUtil.add("uemail", uemail);
		netUtil.add("upassword",  MD5.digest(upassword));
		netUtil.add("usex", usex+"");
		netUtil.post();

	}

}
