package cn.edu.njupt.allgo.fragment.child;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import cn.edu.njupt.allgo.HomeACTIVITY;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UserDataLogic;
import cn.edu.njupt.allgo.logicImpl.UserDataLogicImpl;
import cn.edu.njupt.allgo.util.ChangeDateUtil;
import cn.edu.njupt.allgo.vo.UserDataVo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup; 
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class IntroFRAGMENT extends BaseChildFRAGMENT implements OnRefreshListener , RefreshInterFace {
 


	private TextView textView_intro_UName;
	private TextView textView_intro_USatement;
	private TextView TextView_intro_USex;
	private TextView TextView_intro_UEmail;
	private TextView TextView_intro_UBirthday;
	private TextView TextView_intro_URegDate;
	private TextView TextView_intro_UAddress;

	private UserDataVo userdata ;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private ScrollView scrollView_intro;
	
	private UserDataLogic userDataLogic ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		setFragmentFlag("IntroFRAGMENT");
		userDataLogic = new UserDataLogicImpl(getActivity(),this);
		View view = inflater.inflate(R.layout.fragment_intro, null);
		return view;
	}

	
	@Override 
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		findViewById();
		setView();
	}

	private void findViewById() {
		scrollView_intro = (ScrollView)getView().findViewById(R.id.scrollView_intro);
		textView_intro_UName = (TextView) getView().findViewById(R.id.textView_intro_UName);
		textView_intro_USatement = (TextView) getView().findViewById(R.id.textView_intro_USatement);
		TextView_intro_USex = (TextView) getView().findViewById(R.id.TextView_intro_USex);
		TextView_intro_UEmail = (TextView) getView().findViewById(R.id.TextView_intro_UEmail);
		TextView_intro_UBirthday = (TextView) getView().findViewById(R.id.TextView_intro_UBirthday);
		TextView_intro_URegDate = (TextView) getView().findViewById(R.id.TextView_intro_URegDate);
		TextView_intro_UAddress = (TextView) getView().findViewById(R.id.TextView_intro_UAddress);
	}
	


	private void setView() {
		initUserData();
		mPullToRefreshAttacher = ((HomeACTIVITY)getActivity())
                .getPullToRefreshAttacher();
        mPullToRefreshAttacher.addRefreshableView(scrollView_intro, this);

	}


	private void initUserData() {
		userDataLogic.initUserData();
	}


	private void setUserData(UserDataVo userdata) {
		this.userdata = userdata ;
		
		textView_intro_UName.setText(userdata.getUname());
		textView_intro_USatement.setText(userdata.getUsatement());
		TextView_intro_USex.setText((userdata.getUsex() == 1)?"男":"女");
		TextView_intro_UEmail.setText(userdata.getUemail());
		TextView_intro_UBirthday.setText(userdata.getUbirthday());
		TextView_intro_URegDate.setText(ChangeDateUtil.showDate(userdata.getUregdate() , "yyyy'年'MMMd日"));
		TextView_intro_UAddress.setText(userdata.getUaddress());
		
	}


	@Override
	public void onRefreshStarted(View view) {
		//  下拉刷新的动作
		userDataLogic.getUserData();
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
			mPullToRefreshAttacher.setRefreshComplete();
			break;
		case -1:
			Toast.makeText(getActivity(), (String)result, Toast.LENGTH_SHORT).show();
			mPullToRefreshAttacher.setRefreshComplete();
			break;
		}
		
	}

	
}
