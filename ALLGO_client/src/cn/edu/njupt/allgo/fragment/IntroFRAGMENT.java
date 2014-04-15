package cn.edu.njupt.allgo.fragment;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.activity.EventPageACTIVITY;
import cn.edu.njupt.allgo.activity.HomeACTIVITY;
import cn.edu.njupt.allgo.activity.IntroACTIVITY;
import cn.edu.njupt.allgo.activity.MyEventACTIVITY;
import cn.edu.njupt.allgo.activity.UpdateAvatarACTIVTIY;
import cn.edu.njupt.allgo.activity.UpdateUserDetailACTIVITY;
import cn.edu.njupt.allgo.fragment.child.BaseChildFRAGMENT;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UserDataLogic;
import cn.edu.njupt.allgo.logicImpl.UserDataLogicImpl;
import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.util.ImageUtil;
import cn.edu.njupt.allgo.vo.UserDataVo;
import cn.edu.njupt.allgo.widget.CircularImage;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;  
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;  
import android.view.View.OnClickListener;
import android.view.ViewGroup; 
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class IntroFRAGMENT extends BaseChildFRAGMENT implements RefreshInterFace {
 


	private TextView textView_intro_UName;
	private TextView textView_intro_USatement;
	private TextView TextView_intro_USex;
	private TextView TextView_intro_UEmail;
	private TextView TextView_intro_UBirthday;
	private TextView TextView_intro_URegDate;
	private TextView TextView_intro_UAddress;

	private UserDataVo userdata ;
	boolean freshFlag = false;
	
	private UserDataLogic userDataLogic ;
	private FadingActionBarHelper mFadingHelper;
	private CircularImage cover_user_photo;
	private Button button_openMyEvent;
	private ImageUtil imageUtil;
	private Button button_UpdateDetail1;
	private Button button_UpdateDetail2;
	private Button button_UpdateDetail3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		setFragmentFlag("IntroFRAGMENT");
		userdata = new UserDataVo();
		userDataLogic = new UserDataLogicImpl(getActivity(),this,userdata);
		View view = mFadingHelper.createView(inflater);
		return view;
	}



	@Override
	public void onAttach(Activity activity) {
		// TODO 自动生成的方法存根
		super.onAttach(activity);
		
		mFadingHelper = new FadingActionBarHelper()
		.actionBarBackground(R.drawable.ActionBarColor)
        .headerLayout(R.layout.intro_header_light)
        .contentLayout(R.layout.fragment_intro)
        .headerOverlayLayout(R.layout.intro_header_overlay);
		mFadingHelper.initActionBar(activity);
		
	}



	@Override 
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		findViewById();
		setView();
	}

	private void findViewById() {
		textView_intro_UName = (TextView) getView().findViewById(R.id.textView_intro_UName);
		textView_intro_USatement = (TextView) getView().findViewById(R.id.textView_intro_USatement);
		TextView_intro_USex = (TextView) getView().findViewById(R.id.TextView_intro_USex);
		TextView_intro_UEmail = (TextView) getView().findViewById(R.id.TextView_intro_UEmail);
		TextView_intro_UBirthday = (TextView) getView().findViewById(R.id.TextView_intro_UBirthday);
		TextView_intro_URegDate = (TextView) getView().findViewById(R.id.TextView_intro_URegDate);
		TextView_intro_UAddress = (TextView) getView().findViewById(R.id.TextView_intro_UAddress);
		cover_user_photo = (CircularImage) getView().findViewById(R.id.cover_user_photo);
		button_openMyEvent = (Button)getView().findViewById(R.id.button_openMyEvent);
		button_UpdateDetail1 = (Button)getView().findViewById(R.id.button_UpdateDetail1);
		button_UpdateDetail2 = (Button)getView().findViewById(R.id.button_UpdateDetail2);
		button_UpdateDetail3 = (Button)getView().findViewById(R.id.button_UpdateDetail3);
		imageUtil = new ImageUtil(getActivity());
		imageUtil.configDefaultLoadFailedImage(R.drawable.ic_avatar_120);
	}
	


	private void setView() {
		initUserData();
		
		 OnClickListener clickListener = new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						new AlertDialog.Builder(getActivity())
		                .setItems(R.array.update_userdetail_array, new DialogInterface.OnClickListener() {
				               public void onClick(DialogInterface dialog, int which) {
				               ((IntroACTIVITY)getActivity()).dialog_click(which);
				           }
		                })
		                .show();
						
					}
				};
		
		button_UpdateDetail1.setOnClickListener(clickListener);
		button_UpdateDetail2.setOnClickListener(clickListener);
		button_UpdateDetail3.setOnClickListener(clickListener);
		
		button_openMyEvent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),MyEventACTIVITY.class);
				startActivity(intent);
			}
		});
	}


	private void initUserData() {
		userDataLogic.initUserData();
	
	}


	private void setUserData(UserDataVo userdata) {
		this.userdata = userdata ;
		
		textView_intro_UName.setText(isNull(userdata.getUname())?"用户名":userdata.getUname());
		textView_intro_USatement.setText(isNull(userdata.getUsatement())?"还没写个性宣言":userdata.getUsatement());
		TextView_intro_USex.setText((userdata.getUsex() == 1)?"男":"女");
		TextView_intro_UEmail.setText(userdata.getUemail());
		TextView_intro_UBirthday.setText(isNull(userdata.getUbirthday())?"还没填写出生日期":DateUtil.showDate(userdata.getUbirthday() , "yyyy年MM月d日"));
		TextView_intro_URegDate.setText(isNull(DateUtil.showDate(userdata.getUregdate() , "yyyy年MM月d日"))?"用户的注册日期":DateUtil.showDate(userdata.getUregdate() , "yyyy年MM月d日"));
		TextView_intro_UAddress.setText(isNull(userdata.getUaddress())?"用户的所在地":userdata.getUaddress());

		imageUtil.displayAvatar(cover_user_photo, userdata.getUid());
	}

	private boolean isNull(String arg){
		boolean flag = false ;
		
		if(arg == null|| arg.equals("")){
			flag = true;
		}
		
		return flag;
	}


	@Override
	public void saveData() {
		userDataLogic.saveUserData();
		
	}
	
	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1:
			this.userdata = (UserDataVo)result;
			setUserData(this.userdata);
			break;
		case 2:		//联网更新
			this.userdata = (UserDataVo)result;
			setUserData(this.userdata);
			freshFlag = false;
			Toast.makeText(getActivity(), "已更新", Toast.LENGTH_SHORT).show();
			break;
		case -1:
			Toast.makeText(getActivity(), (String)result, Toast.LENGTH_SHORT).show();
			freshFlag = false;
			break;
		}
		
	}



	public void get() {
		if(!freshFlag){
			userDataLogic.getUserData();
			freshFlag = true;
		}else{
			Toast.makeText(getActivity(), "正在刷新", Toast.LENGTH_SHORT).show();
		}
		
	}



	public void dialog_click(int which) {
		switch(which){
        case 0:		//修改头像
        	Intent intent = new Intent(getActivity(),UpdateAvatarACTIVTIY.class);
        	Bundle bundle = new Bundle();
		   bundle.putSerializable("user", userdata);
		   intent.putExtras(bundle);
        	startActivity(intent);
     	   break;
        case 1:		//修改个人信息
        	
        	Intent intent2 = new Intent(getActivity(),UpdateUserDetailACTIVITY.class);
        	Bundle bundle2 = new Bundle();
		   bundle2.putSerializable("user", userdata);
		   intent2.putExtras(bundle2);
        	startActivity(intent2);
        }
		
	}



	public void refresh(UserDataVo user) {
		this.userdata =user;
		setUserData(this.userdata);
		Toast.makeText(getActivity(), "已更新", Toast.LENGTH_SHORT).show();
		saveData();
	}

	public void updateAvatar() {
		imageUtil.clearCacheAvatar(userdata.getUid());
		this.get();
	}

	
}
