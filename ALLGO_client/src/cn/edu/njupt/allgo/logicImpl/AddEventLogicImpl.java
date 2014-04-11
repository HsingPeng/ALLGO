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
import cn.edu.njupt.allgo.logic.AddEventLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UserDataVo;


public class AddEventLogicImpl implements AddEventLogic {

	private Context context ;
	private RefreshInterFace refresh ;
	
	public AddEventLogicImpl(Context context , RefreshInterFace refresh){
		this.context = context ;
		this.refresh = refresh ;
	}
	
	@Override
	public void addEvent(String outline,
			String startdate, String enddate, String content, String place,
			String position ,String ecategoryname, int visible) {

		NetUtil netUtil = new NetUtil("event/create", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("event_create")){
						 EventVo event = JSON.parseObject(jsonObject.getString("event_create") , EventVo.class);
                		 Log.i("Http", event.toString());
                		 refresh.refresh(event, 1);
         	        }else{
         	        	refresh.refresh("提交出错", -1);
         	        }
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			
		});
		
		startdate = DateUtil.changeDate(startdate.replaceAll(" [^a]*\\-", ""));
		startdate = DateUtil.saveDate(startdate);
		if(enddate != null){
			enddate = DateUtil.changeDate(enddate.replaceAll(" [^a]*\\-", ""));
			enddate = DateUtil.saveDate(enddate);
		}
		
		netUtil.add("outline", outline);
		netUtil.add("startdate", startdate);
		netUtil.add("enddate", enddate);
		netUtil.add("content", content);
		netUtil.add("place", place);
		netUtil.add("position", position);
		netUtil.add("ecategoryname", ecategoryname);
		netUtil.add("visible", visible+"");
		netUtil.post();

	}
}
