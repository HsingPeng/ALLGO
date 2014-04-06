package cn.edu.njupt.allgo.logicImpl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.njupt.allgo.application.MyDeclare;
import cn.edu.njupt.allgo.logic.PastEventLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.util.ArrayListUtil;
import cn.edu.njupt.allgo.util.ChangEventVo;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.FriendEventVo;
import cn.edu.njupt.allgo.vo.PastEventVo;

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


public class PastEventLogicImpl implements PastEventLogic {

	private Context context ;
	private RefreshInterFace refresh ;
	private int page1;
	private MyDeclare declare;
	
	public PastEventLogicImpl(Context context , RefreshInterFace refresh) {
		this.context = context ;
		this.refresh = refresh ;
		declare =(MyDeclare)context.getApplicationContext();
	}
	
	@Override
	public void getEvent(int page, int pagenum) {

        NetUtil netUtil = new NetUtil("event/user_follow", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("user_follow")){
							 if(page1 == 1){
								 refresh.refresh(JSON.parseArray(jsonObject.getString("user_follow") , EventVo.class) , 2);
							 }else{
								 refresh.refresh(JSON.parseArray(jsonObject.getString("user_follow") , EventVo.class) , 3);
							 }
         	        }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        this.page1 = page;
		SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		netUtil.add("uid", sharedPref.getInt("uid", -1) + "");
		netUtil.add("page", page + "");
		netUtil.add("pagenum", pagenum + "");
		netUtil.get();
        
	}

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
    	        List<EventVo> event = db.findAll(Selector.from(PastEventVo.class));
    	        if(event != null){
    	        Log.i("DB", "initPastEvent.size()==>" + event.size());
    	        for(int i=0 ; i<event.size() ; i++) {
    	        	events.add(ChangEventVo.event2Event(event.get(i))) ;
    	        	//Log.i("DB", "=initPastEvent=>" + event.get(i).toString());
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

	@Override
	public void saveEvent(ArrayList<EventVo> eventsData) {
		
		SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		int uid = sharedPref.getInt("uid", -1) ;
    	try{
		    DbUtils db = DbUtils.create(context,uid + ".db");
		    db.configAllowTransaction(true);
	        db.configDebug(true);
	        db.deleteAll(PastEventVo.class);
	        Log.i("DB", "deleteAll =saveBindingId=>" + eventsData.size()) ;
	        for(int i=0 ; i<20 && i<eventsData.size() ; i++){
	        	db.save(ChangEventVo.event2PastEvent(eventsData.get(i)));
	        }
			}catch(DbException e){
		    	Log.e("DB", "error :" + e.getMessage() + "\n");
		    }
	
	}


}
