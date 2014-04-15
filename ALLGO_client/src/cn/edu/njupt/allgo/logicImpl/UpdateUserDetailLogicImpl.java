package cn.edu.njupt.allgo.logicImpl;

import java.io.IOException;
import java.io.InputStream;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.activity.UpdateUserDetailACTIVITY;
import cn.edu.njupt.allgo.application.MyDeclare;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UpdateUserDetailLogic;
import cn.edu.njupt.allgo.logic.UserDataLogic;
import cn.edu.njupt.allgo.util.ImageUtil;
import cn.edu.njupt.allgo.util.MD5;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.UserDataVo;

public class UpdateUserDetailLogicImpl implements UpdateUserDetailLogic{

	private Context context ;
	private RefreshInterFace refresh ;
	private MyDeclare declare;
	private UserDataVo userdata;
	
	public UpdateUserDetailLogicImpl (Context context , RefreshInterFace refresh){
		this.context = context;
		this.refresh = refresh;
		declare =(MyDeclare)context.getApplicationContext();
	}

	public void submitDetail(UserDataVo vo){

		NetUtil netUtil = new NetUtil("user/detail", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("user_detail")){
						 userdata = JSON.parseObject(jsonObject.getString("user_detail") , UserDataVo.class);
						refresh.refresh(userdata , 1);
         	        }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		netUtil.add("uid", vo.getUid()+"");
		netUtil.add("usex", vo.getUsex()+"");
		netUtil.add("ubirthday", vo.getUbirthday());
		netUtil.add("uaddress", vo.getUaddress());
		netUtil.add("usatement", vo.getUsatement());
		netUtil.post();
	}

	@Override
	public void updateAvatar(Bitmap avatar) {
		   NetUtil netUtil = new NetUtil("user/avatar", refresh, context, new NetCallBack(){
				@Override
				public void getResult(JSONObject jsonObject) {
					try {
						 if(jsonObject.getString("response").equals("user_avatar")){
							 //Log.i("DB","success");
							refresh.refresh(null , 1);
	         	        }else{
	         	        	refresh.refresh("注册出错", -1);
	         	        }
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
			});
			
			Bitmap bit = null;
			if(avatar == null){
				bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_avatar_120);
			}else{
				bit = avatar;
			}
			InputStream s = ImageUtil.Bitmap2InputStream(bit);
			try {
				netUtil.addStream("avatar",s,s.available());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
			int uid = sharedPref.getInt("uid", -1) ;
			
			netUtil.add("uid", uid+"");
			netUtil.post();
		
	}

}
