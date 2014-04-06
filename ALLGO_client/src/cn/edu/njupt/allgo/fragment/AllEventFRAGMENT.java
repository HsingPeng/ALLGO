package cn.edu.njupt.allgo.fragment;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.adapter.MyViewAdapter;
import cn.edu.njupt.allgo.custom.CustomViewPager;
import cn.edu.njupt.allgo.fragment.child.CommonEventFRAGMENT;
import cn.edu.njupt.allgo.fragment.child.FriendsEventFRAGMENT;
import cn.edu.njupt.allgo.vo.EventVo;

public class AllEventFRAGMENT extends BaseFRAGMENT implements TabListener{
	
	private CustomViewPager mViewPager1 ;
	private ArrayList<Fragment> list1 = new ArrayList<Fragment>();
	private MyViewAdapter adapter1;
	private CommonEventFRAGMENT commoneventFragment;
	private FriendsEventFRAGMENT friendseventFragment;
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		setFragmentFlag("AllEventFRAGMENT");
		View view = inflater.inflate(R.layout.fragment_allevent, null);
		mViewPager1 = (CustomViewPager) view.findViewById(R.id.pager_allevent);
		if(commoneventFragment == null){
			commoneventFragment = new CommonEventFRAGMENT();
			friendseventFragment= new FriendsEventFRAGMENT();
	    	list1.add(commoneventFragment);
	    	list1.add(friendseventFragment);
		}
		initViewPager(adapter1, list1, mViewPager1);
		return view;
	}


	@Override
	public void initActionBar() {
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
    	getActivity().getActionBar().addTab(getActivity().getActionBar().newTab().setText("活动广场").setTabListener(this));
    	getActivity().getActionBar().addTab(getActivity().getActionBar().newTab().setText("好友的活动").setTabListener(this));
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		String text = (String) tab.getText() ;
		if(text.equals("活动广场")) {
			mViewPager1.setCurrentItem(0);
		}
		if(text.equals("好友的活动")) {
			mViewPager1.setCurrentItem(1);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		
	}
	
	public void deleteEvent(EventVo event){
		commoneventFragment.deleteEvent(event);
		friendseventFragment.deleteEvent(event);
	}

	

}
