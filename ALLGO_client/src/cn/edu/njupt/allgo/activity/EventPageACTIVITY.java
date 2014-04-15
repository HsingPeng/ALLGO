package cn.edu.njupt.allgo.activity;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.adapter.EventPageFollowersAdapter;
import cn.edu.njupt.allgo.fragment.AddCommentDialogFRAGMENT;
import cn.edu.njupt.allgo.fragment.UpdateEventDialogFRAGMENT;
import cn.edu.njupt.allgo.logic.EventPageLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logicImpl.EventPageLogicImpl;
import cn.edu.njupt.allgo.util.ArrayListUtil;
import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.util.ImageUtil;
import cn.edu.njupt.allgo.vo.EventAddVo;
import cn.edu.njupt.allgo.vo.EventCommentVo;
import cn.edu.njupt.allgo.vo.EventFollowerVo;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.widget.GrapeGridview;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EventPageACTIVITY extends BaseActivity implements RefreshInterFace , PullToRefreshAttacher.OnRefreshListener{
	
	
	private TextView outlineshow;
	private TextView textView_eventpage_StartDate;
	private TextView TextView_EndDate;
	private TextView textView_eventpage_place;
	private TextView TextView_ECategoryName;
	private TextView TextView_Visible;
	private TextView TextView_eventpage_Content;
	private TextView TextView_eventpage_Dateline;
	private TextView textView_eventpage_followerscount;
	private TextView textView_eventpage_commentscount;
	private Button button_follow;
	private LinearLayout LinearLayout_EventAdd;
	private LinearLayout LinearLayout_EventComments;
	private EventVo eventdata;
	private TextView TextView_eventpage_UName;
	
	private ProgressDialog progressDialog;
	private EventPageLogic eventPageLogic ;
	private Button Button_comment;
	private boolean isFollow = false;
	private TextView textView_eventpage_position;
	private LinearLayout linearLayout_eventpage_endtime;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private PullToRefreshLayout ptrLayout;
	private ImageView imageView_eventpage_when;
	private ImageView imageView_event_userhead;
	private ImageUtil imageUtil;
	private GrapeGridview gridView_eventpage_followers;
	
	private ArrayList<Integer> followersUids = new ArrayList<Integer>() ;
	private EventPageFollowersAdapter followersAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventpage);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		eventPageLogic = new EventPageLogicImpl(this,this);
		imageUtil = new ImageUtil(this);
		imageUtil.configDefaultLoadFailedImage(R.drawable.default_head_widget);
		iniView();
		inflaterEvent();
		inflaterEventdetails();

	}

	private void iniView() {
		
		outlineshow = (TextView) findViewById(R.id.outlineshow);
		textView_eventpage_StartDate = (TextView) findViewById(R.id.textView_eventpage_StartDate);
		TextView_EndDate = (TextView) findViewById(R.id.TextView_EndDate);
		textView_eventpage_place = (TextView) findViewById(R.id.textView_eventpage_place);
		TextView_ECategoryName = (TextView) findViewById(R.id.TextView_ECategoryName);
		TextView_Visible = (TextView) findViewById(R.id.TextView_Visible);
		TextView_eventpage_Content = (TextView) findViewById(R.id.TextView_eventpage_Content);
		TextView_eventpage_Dateline = (TextView) findViewById(R.id.TextView_eventpage_Dateline);
		TextView_eventpage_UName = (TextView) findViewById(R.id.TextView_eventpage_UName);
		textView_eventpage_followerscount = (TextView) findViewById(R.id.textView_eventpage_followerscount);
		textView_eventpage_commentscount = (TextView) findViewById(R.id.textView_eventpage_commentscount);
		textView_eventpage_position = (TextView) findViewById(R.id.TextView_eventpage_position);
		button_follow = (Button) findViewById(R.id.button_follow);
		Button_comment = (Button) findViewById(R.id.Button_comment);
		imageView_eventpage_when = (ImageView) findViewById(R.id.imageView_eventpage_when);
		imageView_event_userhead = (ImageView) findViewById(R.id.imageView_event_userhead);
		LinearLayout_EventAdd = (LinearLayout) findViewById(R.id.LinearLayout_EventAdd);
		LinearLayout_EventComments = (LinearLayout) findViewById(R.id.LinearLayout_EventComments);
		linearLayout_eventpage_endtime = (LinearLayout) findViewById(R.id.linearLayout_eventpage_endtime);
		ptrLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);
		gridView_eventpage_followers = (GrapeGridview) this.findViewById(R.id.gridView_eventpage_followers);

		followersAdapter = new EventPageFollowersAdapter(this,followersUids,imageUtil);
		gridView_eventpage_followers.setAdapter(followersAdapter);
		
		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
		ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);
		
		button_follow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//加入活动
				if(checkFollow()){
					if(!isFollow){
						showProgressDialog("正在加入");
						eventPageLogic.follow(eventdata.getEid());
						}else{
							showProgressDialog("正在取消加入");
							eventPageLogic.unfollow(eventdata.getEid());
						}
				}else{
					Toast.makeText(EventPageACTIVITY.this, "活动已经结束", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Button_comment.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				//评论
				SharedPreferences share = EventPageACTIVITY.this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
				//share.getInt("uid", -1);
				//share.getString("uname", "");
				AddCommentDialogFRAGMENT newFragment = AddCommentDialogFRAGMENT.newInstance("addComment");
				newFragment.setValue(share.getInt("uid", -1),eventdata.getEid(),share.getString("uname", ""),-1,"",eventPageLogic);
		        newFragment.show(getSupportFragmentManager(), "dialog");
			}
		});

		
	}
	
	private boolean checkFollow(){
		boolean flag = false;
			switch(DateUtil.judgeDate(eventdata.getStartdate(), eventdata.getEnddate())){
			case 1:			//活动已经结束
				
				break;
			default:
				flag = true;
				break;
		}
		return flag;
	}
	
	//检测活动创建者是不是用户
	private void isUpZhu() {
		SharedPreferences share = EventPageACTIVITY.this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
		int uid = share.getInt("uid", -1);
		if (uid == eventdata.getUid()){
			setFollowButton();
			button_follow.setTextColor(Color.GRAY);
			button_follow.setClickable(false);
		}
		
	}

	private void setFollowButton(){
		isFollow = true;
		button_follow.setText("已经加入");
		Drawable left = getResources().getDrawable(R.drawable.icon_like_red) ;
		left.setBounds(0, 0, left.getIntrinsicWidth(), left.getIntrinsicHeight());
		button_follow.setCompoundDrawables(left, null, null, null);
	}
	
	private void setUnFollowButton(){
		isFollow = false;
		button_follow.setText("我要加入");
		Drawable left = getResources().getDrawable(R.drawable.icon_like) ;
		left.setBounds(0, 0, left.getIntrinsicWidth(), left.getIntrinsicHeight());
		button_follow.setCompoundDrawables(left, null, null, null);
	}
	
	private void inflaterEvent() {
		Intent intent=getIntent();
		eventdata = (EventVo)intent.getSerializableExtra("EventData");
		outlineshow.setText(eventdata.getOutline()); 
		textView_eventpage_StartDate.setText(DateUtil.showDate(eventdata.getStartdate()));
		if(eventdata.getEnddate() != null&&!eventdata.getEnddate().equals("")) {
			TextView_EndDate.setText(DateUtil.showDate(eventdata.getEnddate()));
		}else{
			linearLayout_eventpage_endtime.setVisibility(View.GONE);
		}
		textView_eventpage_place.setText(eventdata.getPlace());
		TextView_ECategoryName.setText(eventdata.getEcategroyname());
		TextView_Visible.setText((eventdata.getVisible() == 0)?"所有人":"仅好友");
		TextView_eventpage_Content.setText(eventdata.getContent());
		TextView_eventpage_Dateline.setText(DateUtil.showDate(eventdata.getDateline()));
		TextView_eventpage_UName.setText(eventdata.getUname());
		textView_eventpage_followerscount.setText(eventdata.getFollowerscount() + "人");
		textView_eventpage_commentscount.setText(eventdata.getCommentscount() + "");
		textView_eventpage_position.setText(eventdata.getPosition().split(" ")[0]+
				"省 "+eventdata.getPosition().split(" ")[1]+"市 "+eventdata.getPosition().split(" ")[2]);
		imageView_eventpage_when.setImageResource(setWhenImage(eventdata.getStartdate(),eventdata.getEnddate()));
		isUpZhu();

		imageUtil.displayAvatar(imageView_event_userhead, eventdata.getUid());
		
	}

	/**
	 * 动态设置丝带图片
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private int setWhenImage(String startdate, String enddate) {
		int image = 1;
		switch(DateUtil.judgeDate(startdate, enddate)){
		case 1:
			image =  R.drawable.silk_riband_red_past;
			break;
		case 2:
			image =  R.drawable.silk_riband_blue_being;
			break;
		case 3:
			image =  R.drawable.silk_riband_green_goingto;
			break;
		}
		return image;
	}
	
	/**
	 * 填充活动补充和评论
	 */
	private void inflaterEventdetails() {
		mPullToRefreshAttacher.setRefreshing(true);
		eventPageLogic.getEventDetails(eventdata.getEid());
		
	}
	
	/**
	 * 填充活动补充
	 * @param arg0
	 */
	private void addEventAddVo(EventAddVo arg0) {
		LayoutInflater inflater = getLayoutInflater();	// 通过inflate方法将layout转化为view
		View layout = inflater.inflate(R.layout.list_eventadd, null);
		TextView TextView_eventadd_Content = (TextView)layout.findViewById(R.id.TextView_eventadd_Content);
		TextView TextView_eventadd_AddTime = (TextView)layout.findViewById(R.id.TextView_eventadd_AddTime);
		TextView_eventadd_Content.setText(arg0.getContent());
		TextView_eventadd_AddTime.setText(DateUtil.showDate(arg0.getAddtime()));
		LinearLayout_EventAdd.addView(layout);
	}

	/**
	 * 添加活动评论
	 * @param arg0
	 */
	private void addEventCommentVo(EventCommentVo arg0) {
		LayoutInflater inflater = getLayoutInflater();	// 通过inflate方法将layout转化为view
		View layout = inflater.inflate(R.layout.list_eventcomments, null);
		TextView textView_eventcomments_UName = (TextView)layout.findViewById(R.id.textView_eventcomments_UName);
		TextView textView_eventcomments_ReplyUName = (TextView)layout.findViewById(R.id.textView_eventcomments_ReplyUName);
		TextView textView_eventcomments_Texts = (TextView)layout.findViewById(R.id.textView_eventcomments_Texts);
		TextView TextView_eventcomments_AddTime = (TextView)layout.findViewById(R.id.TextView_eventcomments_SendTime);
		ImageView imageView_eventcomment_userhead = (ImageView)layout.findViewById(R.id.imageView_eventcomment_userhead);
		Button button_comments = (Button)layout.findViewById(R.id.button_eventcomments);
		button_comments.setTag(arg0);
		button_comments.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//显示评论窗口
				EventCommentVo i = (EventCommentVo)v.getTag();

				SharedPreferences share = EventPageACTIVITY.this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
				AddCommentDialogFRAGMENT newFragment = AddCommentDialogFRAGMENT.newInstance("addComment");
				newFragment.setValue(share.getInt("uid", -1),eventdata.getEid(),share.getString("uname", ""),i.getUid(),i.getUname(),eventPageLogic);
		        newFragment.show(getSupportFragmentManager(), "dialog");
		        
			}
		});
		
		textView_eventcomments_UName.setText(arg0.getUname() + ":");
		
		if(arg0.getReplyuname() != null&&!arg0.getReplyuname().equals("")) {
			textView_eventcomments_ReplyUName.setText("回复" + arg0.getReplyuname()  + ":");
		}else {
			textView_eventcomments_ReplyUName.setVisibility(View.GONE);
		}
		textView_eventcomments_Texts.setText(arg0.getTexts());
		TextView_eventcomments_AddTime.setText(DateUtil.showDate(arg0.getSendtime()));
		imageUtil.displayAvatar(imageView_eventcomment_userhead, arg0.getUid());
		
		LinearLayout_EventComments.addView(layout);
	}
	
	/**
	 * 清空所有补充
	 */
	private void removeAdd(){
		LinearLayout_EventAdd.removeAllViews();
		}
	
	/**
	 * 清空所有评论
	 */
	private void removeComments(){
		LinearLayout_EventComments.removeAllViews();
	}
	
		//添加actionbar菜单
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	           // TODO Auto-generated method stub

			SharedPreferences share = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
			if(eventdata.getUid() == share.getInt("uid", -1)){
			 MenuItem actionItem2 = menu.add("修改") ;
	         actionItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	         actionItem2.setIcon(R.drawable.ic_action_edit);
			}
		
			return true;
		   }
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
		if(item.getTitle().equals("修改")) {
			//showDialog ;
			UpdateEventDialogFRAGMENT newFragment = UpdateEventDialogFRAGMENT.newInstance(
	                R.string.alert_dialog_update_title);
	        newFragment.show(getFragmentManager(), "dialog");
		}
        return super.onOptionsItemSelected(item);
    }

	public void dialog_click (int which){
		switch(which){
        case 0:		//添加活动补充
     	   final EditText edit  = new EditText(this);
	     	  new AlertDialog.Builder(this)
	          .setTitle("活动补充")
	          .setView(edit)
	          .setPositiveButton("发送", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int whichButton) {
	            	  showProgressDialog("正在发送");
	                  EventPageACTIVITY.this.sendAdd(edit.getText().toString());
	                  
	              }
	          })
	          .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int whichButton) {
	            	  
	              }
	          }).show();
     	   break;
        case 1:		//删除活动
        	new AlertDialog.Builder(this)
	          .setIconAttribute(android.R.attr.alertDialogIcon)
	          .setTitle("删除")
	          .setMessage("确认删除此活动!")
	          .setPositiveButton("发送", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int whichButton) {
	            	  showProgressDialog("正在删除");
	            	  EventPageACTIVITY.this.destroyEvent();
	              }
	          })
	          .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int whichButton) {
	
	            	  
	              }
	          }).show();
     	   break;
        }
	}
	
	public void sendAdd(String text){
		SharedPreferences share = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
		eventPageLogic.sendAdd(share.getInt("uid", -1), eventdata.getEid() , text);
	}
	
	public void destroyEvent(){
		SharedPreferences share = this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
		eventPageLogic.destroyEvent(share.getInt("uid", -1), eventdata.getEid());
		}
	
	 /**
		 * 显示提示框
		 */
		public void showProgressDialog(String title) {
			if ((!isFinishing()) && (this.progressDialog == null)) {
				this.progressDialog = new ProgressDialog(this);
				}
				//progressDialog.setCancelable(false);
				this.progressDialog.setTitle(title);
				this.progressDialog.setMessage("请稍等...");
				this.progressDialog.show();
		}
		@Override
		protected void onDestroy() {
			super.onDestroy();
			closeProgressDialog();
			Log.i("Activity"," onDestroy() ==> AddEventACTIVITY");
		}
		
		/**
		 * 关闭提示框
		 */
		public void closeProgressDialog() {
			if (this.progressDialog != null)
				this.progressDialog.dismiss();
		}
		
		
		
		@SuppressWarnings("unchecked")
		@Override
		public void refresh(Object result, int kind) {
			switch(kind){
			case 1:		//添加活动补充
				if(mPullToRefreshAttacher.isRefreshing()){
					mPullToRefreshAttacher.setRefreshComplete();
				}
				removeAdd();
				List<EventAddVo> list1 = (ArrayList<EventAddVo>)result ;
				for(EventAddVo eventAdd : list1){
					addEventAddVo(eventAdd);
				}
				closeProgressDialog();
				break;
			case 2:		//添加活动评论
				if(mPullToRefreshAttacher.isRefreshing()){
					mPullToRefreshAttacher.setRefreshComplete();
				}
				removeComments();
				List<EventCommentVo> list2 = (ArrayList<EventCommentVo>)result ;
				for(EventCommentVo eventComment : list2){
					addEventCommentVo(eventComment);
				}
				closeProgressDialog();
				break;
			case -1:
				Toast.makeText(this, (String)result, Toast.LENGTH_SHORT).show();
				if(mPullToRefreshAttacher.isRefreshing()){
					mPullToRefreshAttacher.setRefreshComplete();
				}
				closeProgressDialog();
				break;
			case 3:
				addEventCommentVo((EventCommentVo)result);
				closeProgressDialog();
				break;
			case 4:
				Toast.makeText(this, "加入成功", Toast.LENGTH_SHORT).show();
				setFollowButton();
				
				closeProgressDialog();
				break;
			case 5:
				Toast.makeText(this, "取消加入", Toast.LENGTH_SHORT).show();
				setUnFollowButton();
				
				closeProgressDialog();
				break;
			case 6:		//设置跟随人数
				textView_eventpage_followerscount.setText((String)result);
				break;
			case 7:		//设置评论人数
				textView_eventpage_commentscount.setText((String)result);
				break;
			case 8:		//添加活动补充
				Toast.makeText(this, "添加补充成功", Toast.LENGTH_SHORT).show();
				addEventAddVo((EventAddVo)result);
				closeProgressDialog();
				break;
			case 9:		//删除活动
				Toast.makeText(this, "活动已删除", Toast.LENGTH_SHORT).show();
				closeProgressDialog();
				Intent intent = new Intent(this,HomeACTIVITY.class);
				intent.putExtra("action", 2);
				Bundle bundle = new Bundle();
				bundle.putSerializable("deletevent", eventdata);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
				break;
			case 10:	//获取跟随用户
				if(mPullToRefreshAttacher.isRefreshing()){
					mPullToRefreshAttacher.setRefreshComplete();
				}
				SharedPreferences share = EventPageACTIVITY.this.getSharedPreferences("userdata", Context.MODE_PRIVATE);
				int uid = share.getInt("uid", -1);
				List<EventFollowerVo> list3 = (ArrayList<EventFollowerVo>)result ;
				for(EventFollowerVo eventFollower : list3){
					
					
					followersUids.add(eventFollower.getUid());
					ArrayListUtil.removeDuplicate(followersUids);
					followersAdapter.notifyDataSetChanged();
					
					if(eventFollower.getUid() == uid){
						setFollowButton();
					}	
				}

				setFollowerGridViewHight();
				
				closeProgressDialog();
				break;
			}
			
		}

		
		private void setFollowerGridViewHight(){
			int lieshu = followersUids.size()/gridView_eventpage_followers.getNumColumns();
			if(followersUids.size()%gridView_eventpage_followers.getNumColumns() != 0){
				lieshu ++;
			}
			gridView_eventpage_followers.getLayoutParams().height = lieshu*62 ;
		}
		
		@Override
		public void onRefreshStarted(View view) {
			eventPageLogic.getEventDetails(eventdata.getEid());
			imageView_eventpage_when.setImageResource(setWhenImage(eventdata.getStartdate(),eventdata.getEnddate()));
		}
		
}
