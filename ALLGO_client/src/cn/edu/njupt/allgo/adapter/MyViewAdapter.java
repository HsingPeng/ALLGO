package cn.edu.njupt.allgo.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
     * 自定义viewpager的适配器
     * @author 深蓝
     *
     */
	public class MyViewAdapter extends FragmentPagerAdapter {
		public boolean isCanScroll = true; 
		private ArrayList<Fragment> fragmentsList;
/*		private int mChildCount = 0;

		     @Override
		     public void notifyDataSetChanged() {    
		           mChildCount = getCount();
		           super.notifyDataSetChanged();
		     }

		     @Override
		     public int getItemPosition(Object object)   {
		     Log.w("debug","getItemPosition==>" + mChildCount);
		           if ( mChildCount > 0) {
		           mChildCount --;
		           return POSITION_NONE;
		           }
		           return super.getItemPosition(object);
		     }
		*/
		public MyViewAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		  public MyViewAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		        super(fm);
		        this.fragmentsList = fragments;
		    }


		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return fragmentsList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragmentsList.size();
		}
		
	}