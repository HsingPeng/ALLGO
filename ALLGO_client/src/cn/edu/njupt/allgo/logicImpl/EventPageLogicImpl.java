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
import cn.edu.njupt.allgo.activity.EventPageACTIVITY;
import cn.edu.njupt.allgo.application.MyDeclare;
import cn.edu.njupt.allgo.logic.EventPageLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.EventAddVo;
import cn.edu.njupt.allgo.vo.EventCommentVo;
import cn.edu.njupt.allgo.vo.EventFollowerVo;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UserDataVo;

public class EventPageLogicImpl implements EventPageLogic {

	private Context context;
	private RefreshInterFace refresh;
	private int followers_count =0;
	private int comments_count =0;
	private MyDeclare declare;
	
	
	public EventPageLogicImpl(Context context,RefreshInterFace refresh){
		this.context = context ;
		this.refresh = refresh ;
		declare =(MyDeclare)context.getApplicationContext();
	}
	
	@Override
	public void getEventDetails(int eid) {


		NetUtil netUtil = new NetUtil("event/details", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("details")){
						followers_count = Integer.parseInt(jsonObject.getString("followers_count"));
						comments_count = Integer.parseInt(jsonObject.getString("comments_count"));
						refresh.refresh(followers_count+"人", 6);
						refresh.refresh(comments_count+"", 7);
						refresh.refresh(JSON.parseArray(jsonObject.getString("followers") , EventFollowerVo.class),10);
						refresh.refresh(JSON.parseArray(jsonObject.getString("adds") , EventAddVo.class),1);
						refresh.refresh(JSON.parseArray(jsonObject.getString("comments"),EventCommentVo.class),2);

					 }else if(jsonObject.getString("response").equals("event_destroy")){
						 refresh.refresh(null,9);
					 }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
		netUtil.add("eid" , eid+"");
		netUtil.get();

	}

	@Override
	public void sendComment(String comment ,int uid,int eid,String uname , int replyuid ,String replyuname) {

        NetUtil netUtil = new NetUtil("event/comments/create", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("create_comment")){
						 comments_count++;
						refresh.refresh(comments_count+"", 7);
						refresh.refresh(JSON.parseObject(jsonObject.getString("create_comment") , EventCommentVo.class),3);
						
					 }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        ((EventPageACTIVITY)context).showProgressDialog("正在提交评论");
        netUtil.add("texts" , comment);
        netUtil.add("uid" , uid+"");
        netUtil.add("eid" , eid+"");
        netUtil.add("uname" , uname);
        if(replyuid != -1){
        netUtil.add("replyuid" , replyuid+"");
        }
        netUtil.add("replyuname" , replyuname);
		netUtil.post();
        
	}

	@Override
	public void follow(int eid) {
        
        NetUtil netUtil = new NetUtil("event/follow", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("follow")){
						followers_count++;
						refresh.refresh(followers_count+"人", 6);
						refresh.refresh(JSON.parseObject(jsonObject.getString("follow") , EventFollowerVo.class),4);
						
					 }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        SharedPreferences share = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        netUtil.add("uid" , share.getInt("uid", -1)+"");
        netUtil.add("uname" , share.getString("uname", ""));
        netUtil.add("eid" , eid+"");
		netUtil.get();
		
	}

	@Override
	public void unfollow(int eid) {
        
        NetUtil netUtil = new NetUtil("event/unfollow", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("unfollow")){
						followers_count--;
						refresh.refresh(followers_count+"人", 6);
						refresh.refresh(null,5);

					 }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        SharedPreferences share = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
        netUtil.add("uid" , share.getInt("uid", -1)+"");
        netUtil.add("uname" , share.getString("uname", ""));
        netUtil.add("eid" , eid+"");
		netUtil.get();
        
	}

	@Override		//添加活动补充
	public void sendAdd(int uid, int eid, String text) {
		
        NetUtil netUtil = new NetUtil("event/add", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("event_add")){
						 refresh.refresh(JSON.parseObject(jsonObject.getString("add") , EventAddVo.class),8);
					 }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        netUtil.add("uid" , uid+"");
        netUtil.add("eid" , eid+"");
        netUtil.add("content" , text);
		netUtil.post();
		
	}

	@Override		//删除活动
	public void destroyEvent(int uid, int eid) {
        
        NetUtil netUtil = new NetUtil("event/destroy", refresh, context, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("event_destroy")){
						 refresh.refresh(jsonObject.getString("eid"),9);
					 }else{
         	        	refresh.refresh("更新出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
        netUtil.add("uid" , uid+"");
        netUtil.add("eid" , eid+"");
		netUtil.get();
		
	}


}
