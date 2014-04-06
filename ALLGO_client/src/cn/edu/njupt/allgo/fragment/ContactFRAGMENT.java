package cn.edu.njupt.allgo.fragment;

import java.util.ArrayList;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.adapter.MyViewAdapter;
import cn.edu.njupt.allgo.custom.CustomViewPager;
import cn.edu.njupt.allgo.fragment.child.ContactListFRAGMENT;
import cn.edu.njupt.allgo.fragment.child.ChatListFRAGMENT;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ContactFRAGMENT extends BaseFRAGMENT implements TabListener{

		
		private CustomViewPager mViewPager2 ;
		private ArrayList<Fragment> list2 = new ArrayList<Fragment>();
		private MyViewAdapter adapter2;
		private ContactListFRAGMENT contactlistFragment;
		private ChatListFRAGMENT chatlistFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO onCreateView
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_contact, null);
		setFragmentFlag("ContactFRAGMENT");
		mViewPager2 = (CustomViewPager) view.findViewById(R.id.pager_contact);
		if(contactlistFragment == null){
			contactlistFragment = new ContactListFRAGMENT();
			chatlistFragment= new ChatListFRAGMENT();
	    	list2.add(contactlistFragment);
	    	list2.add(chatlistFragment);
		}
		initViewPager(adapter2, list2, mViewPager2);
		return view;
	}


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		String text = (String) tab.getText() ;
		if(text.equals("联系人")) {
			mViewPager2.setCurrentItem(0);
		}
		if(text.equals("会话")) {
			mViewPager2.setCurrentItem(1);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void initActionBar() {
		// TODO 自动生成的方法存根
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
    	getActivity().getActionBar().addTab(getActivity().getActionBar().newTab().setText("联系人").setTabListener(this));
    	getActivity().getActionBar().addTab(getActivity().getActionBar().newTab().setText("会话").setTabListener(this));
	}
}
