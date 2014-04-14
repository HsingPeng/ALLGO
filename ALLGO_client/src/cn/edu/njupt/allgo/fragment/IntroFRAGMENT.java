package cn.edu.njupt.allgo.fragment;

import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.activity.HomeACTIVITY;
import cn.edu.njupt.allgo.activity.MyEventACTIVITY;
import cn.edu.njupt.allgo.fragment.child.BaseChildFRAGMENT;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UserDataLogic;
import cn.edu.njupt.allgo.logicImpl.UserDataLogicImpl;
import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.util.ImageUtil;
import cn.edu.njupt.allgo.vo.UserDataVo;
import cn.edu.njupt.allgo.widget.CircularImage;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;  
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;  
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		setFragmentFlag("IntroFRAGMENT");
		userDataLogic = new UserDataLogicImpl(getActivity(),this);
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
		
		imageUtil = new ImageUtil(getActivity());
		imageUtil.configDefaultLoadFailedImage(R.drawable.ic_avatar_120);
	}
	


	private void setView() {
		initUserData();
		
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
		TextView_intro_UBirthday.setText(isNull(userdata.getUbirthday())?"还没填写出生日期":userdata.getUbirthday());
		TextView_intro_URegDate.setText(isNull(DateUtil.showDate(userdata.getUregdate() , "yyyy'年'MMMd日"))?"用户的注册日期":DateUtil.showDate(userdata.getUregdate() , "yyyy'年'MMMd日"));
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

	
}
