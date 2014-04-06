package cn.edu.njupt.allgo.logicImpl;

import java.util.ArrayList;
import java.util.List;

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
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import cn.edu.njupt.allgo.application.MyDeclare;
import cn.edu.njupt.allgo.logic.CommonEventLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.util.ArrayListUtil;
import cn.edu.njupt.allgo.util.ChangEventVo;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.CommonEventVo;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UserDataVo;

public class CommonEventLogicImpl implements CommonEventLogic {
	private Context context ;
	private RefreshInterFace refresh ;
	private int page1;
	private MyDeclare declare;


	public CommonEventLogicImpl(Context context , RefreshInterFace refresh) {
		this.context = context ;
		this.refresh = refresh ;
		declare =(MyDeclare)context.getApplicationContext();
	}
	
	/**
	 * 联网获取所有活动
	 * @param context
	 * @param page
	 * @param pagenum
	 * @param place
	 * @param ecategoryname
	 * @param StartTimeRangA
	 * @param StartTimeRangB
	 */
	@Override
	public void getEvent(int page, int pagenum,
			String place, String ecategoryname, String StartTimeRangA,
			String StartTimeRangB) {
		
		this.page1 = page;
		SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		
		NetUtil netUtil = new NetUtil("event/timeline", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
				 if(jsonObject.getString("response").equals("commonevent_timeline")){
						 if(page1 == 1){
							 refresh.refresh(JSON.parseArray(jsonObject.getString("timeline") , EventVo.class) , 2);
						 }else{
							 refresh.refresh(JSON.parseArray(jsonObject.getString("timeline") , EventVo.class) , 3);
						 }
    	        }else{
    	        	refresh.refresh("更新出错", -1);
    	        }
			} catch (JSONException e) {
				e.printStackTrace();
			}}

		});
		netUtil.add("uid", sharedPref.getInt("uid", -1) + "");
		netUtil.add("page", page + "");
		netUtil.add("pagenum", pagenum + "");
		netUtil.add("position", place);
		netUtil.add("ecategoryname", ecategoryname);
		netUtil.add("StartTimeRangA", StartTimeRangA);
		netUtil.add("StartTimeRangB", StartTimeRangB);
		netUtil.get();
	}

	/**
	 * 初始化所有活动列表，从数据库得到数据
	 * @param context
	 */
	@Override
	public void initEvent() {
		
		
		new AsyncTask<Void, Void, ArrayList<EventVo>>() {
            @Override
            protected ArrayList<EventVo> doInBackground(Void... params) {
            	SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
        		int uid = sharedPref.getInt("uid", -1) ;
        		ArrayList<EventVo> events =new ArrayList<EventVo>();
            	try{
    		    DbUtils db = DbUtils.create(context,uid + ".db");
    		    db.configAllowTransaction(true);
    	        db.configDebug(true);
    	        List<EventVo> event = db.findAll(Selector.from(CommonEventVo.class));
    	        if(event != null){
    	        Log.i("DB", "initcommonEvent.size()==>" + event.size());
    	        for(int i=0 ; i<event.size() ; i++) {
    	        	events.add(ChangEventVo.event2Event(event.get(i))) ;
    	        	}
    	        }
    		}catch(DbException e){
    	    	Log.e("DB", "error :" + e.getMessage() + "\n");
    	    }
    		return events;
    		}

            @Override
            protected void onPostExecute(ArrayList<EventVo> result) {
                super.onPostExecute(result);
                ArrayListUtil.sortEventVo(result);
                refresh.refresh(result, 1);
            }
        }.execute();
        
	
	}

	/**
	 * 保存列表数据到数据库
	 * @param context
	 * @param eventsData
	 */
	@Override
	public void saveEvent(ArrayList<EventVo> eventsData) {
		
		SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		int uid = sharedPref.getInt("uid", -1) ;
    	try{
		    DbUtils db = DbUtils.create(context,uid + ".db");
		    db.configAllowTransaction(true);
	        db.configDebug(true);
	        db.deleteAll(CommonEventVo.class);
	        Log.i("DB", "deleteAll =saveCommonEvent=>" + eventsData.size()) ;
	        for(int i=0 ; i<20 && i<eventsData.size() ; i++){
	        	db.save(ChangEventVo.event2CommonEvent(eventsData.get(i)));
	        }
			}catch(DbException e){
		    	Log.e("DB", "error :" + e.getMessage() + "\n");
		    }
	}

	

}
