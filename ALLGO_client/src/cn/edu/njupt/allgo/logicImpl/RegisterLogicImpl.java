package cn.edu.njupt.allgo.logicImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
			String upassword,Bitmap avatar) {
	
		
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
		
		Bitmap bit = null;
		if(avatar == null){
			bit = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_avatar_120);
		}else{
			bit = avatar;
		}
		InputStream s = Bitmap2InputStream(bit);
		try {
			netUtil.addStream("avatar",s,s.available());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		netUtil.post();

	}

	// 将Bitmap转换成InputStream  
    public InputStream Bitmap2InputStream(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    }
	
}
