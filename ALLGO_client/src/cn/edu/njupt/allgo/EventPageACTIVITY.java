package cn.edu.njupt.allgo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


import cn.edu.njupt.allgo.fragment.AddCommentDialogFRAGMENT;
import cn.edu.njupt.allgo.fragment.LoginDialogFRAGMENT;
import cn.edu.njupt.allgo.fragment.PlaceSpinnerDialogFRAGMENT;
import cn.edu.njupt.allgo.fragment.UpdateEventDialogFRAGMENT;
import cn.edu.njupt.allgo.logic.EventPageLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logicImpl.EventPageLogicImpl;
import cn.edu.njupt.allgo.util.ChangeDateUtil;
import cn.edu.njupt.allgo.vo.EventAddVo;
import cn.edu.njupt.allgo.vo.EventCommentVo;
import cn.edu.njupt.allgo.vo.EventVo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EventPageACTIVITY extends BaseActivity implements RefreshInterFace{
	
	
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
	private TextView TextView03;
	private TextView TextView_eventpage_UName;
	//private List<Button> buttonSet = new ArrayList<Button>() ;		//收集评论里的button
	//private List<EventCommentVo> eventCommentSet = new ArrayList<EventCommentVo>() ;	//收集评论对象
	
	private ProgressDialog progressDialog;
	private EventPageLogic eventPageLogic ;
	private Button Button_comment;
	private boolean isFollow = false;
	private RefreshInterFace refresh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_eventpage);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		eventPageLogic = new EventPageLogicImpl(this,this);
		iniView();
		inflaterEvent();
		inflaterEventdetails();

	}

	private void iniView() {
		
		outlineshow = (TextView) findViewById(R.id.outlineshow);
		textView_eventpage_StartDate = (TextView) findViewById(R.id.textView_eventpage_StartDate);
		TextView_EndDate = (TextView) findViewById(R.id.TextView_EndDate);
		TextView03 = (TextView) findViewById(R.id.TextView03);
		textView_eventpage_place = (TextView) findViewById(R.id.textView_eventpage_place);
		TextView_ECategoryName = (TextView) findViewById(R.id.TextView_ECategoryName);
		TextView_Visible = (TextView) findViewById(R.id.TextView_Visible);
		TextView_eventpage_Content = (TextView) findViewById(R.id.TextView_eventpage_Content);
		TextView_eventpage_Dateline = (TextView) findViewById(R.id.TextView_eventpage_Dateline);
		TextView_eventpage_UName = (TextView) findViewById(R.id.TextView_eventpage_UName);
		textView_eventpage_followerscount = (TextView) findViewById(R.id.textView_eventpage_followerscount);
		textView_eventpage_commentscount = (TextView) findViewById(R.id.textView_eventpage_commentscount);
		button_follow = (Button) findViewById(R.id.button_follow);
		Button_comment = (Button) findViewById(R.id.Button_comment);
		LinearLayout_EventAdd = (LinearLayout) findViewById(R.id.LinearLayout_EventAdd);
		LinearLayout_EventComments = (LinearLayout) findViewById(R.id.LinearLayout_EventComments);
		
		button_follow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//加入活动
				if(!isFollow){
					showProgressDialog("正在加入");
					eventPageLogic.follow(eventdata.getEid());
					}else{
						showProgressDialog("正在取消加入");
						eventPageLogic.unfollow(eventdata.getEid());
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
	
	private void inflaterEvent() {
		Intent intent=getIntent();
		eventdata = (EventVo)intent.getSerializableExtra("EventData");
		refresh = (RefreshInterFace) intent.getSerializableExtra("context");
		outlineshow.setText(eventdata.getOutline()); 
		textView_eventpage_StartDate.setText(ChangeDateUtil.showDate(eventdata.getStartdate()));
		if(eventdata.getEnddate() != null&&!eventdata.getEnddate().equals("")) {
			TextView_EndDate.setText(ChangeDateUtil.showDate(eventdata.getEnddate()));
		}else{
			TextView_EndDate.setVisibility(View.GONE);
			TextView03.setVisibility(View.GONE);
		}
		textView_eventpage_place.setText(eventdata.getPlace());
		TextView_ECategoryName.setText(eventdata.getEcategroyname());
		TextView_Visible.setText((eventdata.getVisible() == 0)?"所有人":"仅好友");
		TextView_eventpage_Content.setText(eventdata.getContent());
		TextView_eventpage_Dateline.setText(ChangeDateUtil.showDate(eventdata.getDateline()));
		TextView_eventpage_UName.setText(eventdata.getUname());
		textView_eventpage_followerscount.setText(eventdata.getFollowerscount() + "人");
		textView_eventpage_commentscount.setText(eventdata.getCommentscount() + "");
		
	}
	

	/**
	 * 填充活动补充和评论
	 */
	private void inflaterEventdetails() {
		/*addEventAddVo(new EventAddVo(1300130, eventdate.getEid(), "我要补充一点，xxxxx", "Mon Feb 15 09:00:00 GMT+08:00 2014"));
		addEventAddVo(new EventAddVo(1300131, eventdate.getEid(), "我再说一下，yyyyyyyy", "Mon Feb 15 09:03:00 GMT+08:00 2014"));
		
		addEventCommentVo(new EventCommentVo(21300130, 54321, "完美猫", eventdate.getEid(), "Mon Feb 15 09:30:00 GMT+08:00 2014", 0, null, "好的好的！"));
		addEventCommentVo(new EventCommentVo(21300131, 654321, "PINK", eventdate.getEid(), "Mon Feb 15 10:30:00 GMT+08:00 2014", 54321, "完美猫", "好的好的！"));*/
		
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
		TextView_eventadd_AddTime.setText(ChangeDateUtil.showDate(arg0.getAddtime()));
		LinearLayout_EventAdd.addView(layout);
	}

	private void addEventCommentVo(EventCommentVo arg0) {
		LayoutInflater inflater = getLayoutInflater();	// 通过inflate方法将layout转化为view
		View layout = inflater.inflate(R.layout.list_eventcomments, null);
		TextView textView_eventcomments_UName = (TextView)layout.findViewById(R.id.textView_eventcomments_UName);
		TextView textView_eventcomments_ReplyUName = (TextView)layout.findViewById(R.id.textView_eventcomments_ReplyUName);
		TextView textView_eventcomments_Texts = (TextView)layout.findViewById(R.id.textView_eventcomments_Texts);
		TextView TextView_eventcomments_AddTime = (TextView)layout.findViewById(R.id.TextView_eventcomments_SendTime);
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
		
		//buttonSet.add(button_comments);
		//eventCommentSet.add(arg0);
		
		textView_eventcomments_UName.setText(arg0.getUname() + ":");
		
		if(arg0.getReplyuname() != null&&!arg0.getReplyuname().equals("")) {
			textView_eventcomments_ReplyUName.setText("回复" + arg0.getReplyuname()  + ":");
		}else {
			textView_eventcomments_ReplyUName.setVisibility(View.GONE);
		}
		textView_eventcomments_Texts.setText(arg0.getTexts());
		TextView_eventcomments_AddTime.setText(ChangeDateUtil.showDate(arg0.getSendtime()));
		LinearLayout_EventComments.addView(layout);
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
                Intent upIntent = new Intent(this, HomeACTIVITY.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.from(this)
                            //如果这里有很多原始的Activity,它们应该被添加在这里
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
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
				List<EventAddVo> list1 = (ArrayList<EventAddVo>)result ;
				for(EventAddVo eventAdd : list1){
					addEventAddVo(eventAdd);
				}
				closeProgressDialog();
				break;
			case 2:		//添加活动评论
				List<EventCommentVo> list2 = (ArrayList<EventCommentVo>)result ;
				for(EventCommentVo eventComment : list2){
					addEventCommentVo(eventComment);
				}
				closeProgressDialog();
				break;
			case -1:
				Toast.makeText(this, (String)result, Toast.LENGTH_SHORT).show();
				closeProgressDialog();
				break;
			case 3:
				addEventCommentVo((EventCommentVo)result);
				closeProgressDialog();
				break;
			case 4:
				Toast.makeText(this, "加入成功", Toast.LENGTH_SHORT).show();
				button_follow.setText("已加入");
				isFollow = true;
				closeProgressDialog();
				break;
			case 5:
				Toast.makeText(this, "取消加入", Toast.LENGTH_SHORT).show();
				button_follow.setText("我要加入");
				isFollow = false;
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
			}
			
		}
		
}
