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
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UnreadLogic;
import cn.edu.njupt.allgo.util.ArrayListUtil;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UnreadVo;
import cn.edu.njupt.allgo.vo.UserDataVo;

public class UnreadLogicImpl implements UnreadLogic {

	private Context context ;
	private RefreshInterFace refresh ;
	private MyDeclare declare;
	
	
	public UnreadLogicImpl(Context context , RefreshInterFace refresh) {
		this.context = context ;
		this.refresh = refresh ;
		declare =(MyDeclare)context.getApplicationContext();
	}
	
	/**
	 * 联网获得消息
	 */
	@Override
	public void getUnread() {
		
        NetUtil netUtil = new NetUtil("remind/unread", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("remind_unread")){
								 refresh.refresh(JSON.parseArray(jsonObject.getString("remind_unread") , UnreadVo.class) , 2);
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
	
	/**
	 * 开程序初始化
	 */
	@Override
	public void initUnread() {
		
		
		new AsyncTask<Void, Void, ArrayList<UnreadVo>>() {
            @Override
            protected ArrayList<UnreadVo> doInBackground(Void... params) {
            	SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
        		int uid = sharedPref.getInt("uid", -1) ;
        		ArrayList<UnreadVo> unreads =new ArrayList<UnreadVo>();
            	try{
    		    DbUtils db = DbUtils.create(context,uid + ".db");
    		    db.configAllowTransaction(true);
    	        db.configDebug(true);
    	        List<UnreadVo> unread = db.findAll(Selector.from(UnreadVo.class));
    	        if(unread != null){
    	        Log.i("DB", "initUnread.size()==>" + unread.size());
    	        for(int i=0 ; i<unread.size() ; i++) {
    	        	unreads.add(unread.get(i)) ;
    	        	//Log.i("DB", "=initUnreadEvent=>" + unread.get(i).toString());
    	        	}
    	        }
    		}catch(DbException e){
    	    	Log.e("DB", "error :" + e.getMessage() + "\n");
    	    }
    		return unreads;
    		}

            @Override
            protected void onPostExecute(ArrayList<UnreadVo> result) {
                super.onPostExecute(result);
                ArrayListUtil.sortUnreadVo(result);
                refresh.refresh(result, 1);
            }
        }.execute();
        
	
	}

	/**
	 * 结束保存
	 */
	@Override
	public void saveUnread(ArrayList<UnreadVo> unreadDate) {

		SharedPreferences sharedPref = context.getSharedPreferences("userdata",Context.MODE_PRIVATE);
		int uid = sharedPref.getInt("uid", -1) ;
    	try{
		    DbUtils db = DbUtils.create(context,uid + ".db");
		    db.configAllowTransaction(true);
	        db.configDebug(true);
	        Log.i("DB", "deleteNo =saveUnread=>" + unreadDate.size()) ;
	        for(int i=0 ; i<unreadDate.size() ; i++){
	        	db.save(unreadDate.get(i));
	        }
			}catch(DbException e){
		    	Log.e("DB", "error :" + e.getMessage() + "\n");
		    }
	}

}
