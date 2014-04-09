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
import cn.edu.njupt.allgo.fragment.child.IntroFRAGMENT;
import cn.edu.njupt.allgo.fragment.child.MyEventFRAGMENT;
import cn.edu.njupt.allgo.fragment.child.PastEventFRAGMENT;
import cn.edu.njupt.allgo.widget.CustomViewPager;

public class MyHomePageFRAGMENT extends BaseFRAGMENT implements TabListener {


	private CustomViewPager mViewPager0 ;
	private ArrayList<Fragment> list0 = new ArrayList<Fragment>();
	private MyViewAdapter adapter0;
	private IntroFRAGMENT introFragment;
	private MyEventFRAGMENT myeventFragment;
	private PastEventFRAGMENT pasteventFragment ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO onCreateView
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_myhomepage, null);
		setFragmentFlag("MyHomePageFRAGMENT");
		mViewPager0 = (CustomViewPager) view.findViewById(R.id.pager_myhome);
		if(introFragment == null){
    		introFragment = new IntroFRAGMENT();
    		myeventFragment= new MyEventFRAGMENT();
    		pasteventFragment = new PastEventFRAGMENT();
	    	list0.add(introFragment);
	    	list0.add(myeventFragment);
	    	list0.add(pasteventFragment);
		}
		initViewPager(adapter0, list0, mViewPager0);
		return view;
	}

	@Override
	public void initActionBar() {
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getActivity().getActionBar().addTab(getActivity().getActionBar().newTab().setText("简介").setTabListener(this));
		getActivity().getActionBar().addTab(getActivity().getActionBar().newTab().setText("我的活动").setTabListener(this));
		getActivity().getActionBar().addTab(getActivity().getActionBar().newTab().setText("历史活动").setTabListener(this));
	}
	

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		String text = (String) tab.getText() ;
		if(text.equals("简介")) {
			mViewPager0.setCurrentItem(0);
		}
		if(text.equals("我的活动")) {
			mViewPager0.setCurrentItem(1);
		}
		if(text.equals("历史活动")) {
			mViewPager0.setCurrentItem(2);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		
	}
	
	

}
