package cn.edu.njupt.allgo.activity;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.adapter.EventCardsAdapter;
import cn.edu.njupt.allgo.fragment.child.CommonEventFRAGMENT;
import cn.edu.njupt.allgo.logic.CommonEventLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logicImpl.CommonEventLogicImpl;
import cn.edu.njupt.allgo.util.ArrayListUtil;
import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.vo.EventVo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class FilterEventACTIVITY extends BaseActivity implements PullToRefreshAttacher.OnRefreshListener , RefreshInterFace {

	private int page = 1 ;
	private String place = "" ;
	private String ecategoryname = "";
	private String StartTimeRangA = "";
	private String StartTimeRangB = "";
	
	private EventCardsAdapter eventcardsAdapter;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
	private View moreView;
	private ListView listView;
	private ArrayList<EventVo> eventsData =new ArrayList<EventVo>() ;  //要放在listview的内容
	private boolean listflag = false ;  //保证上滑刷新的线程同时只开启一个
	private boolean listfootflag = false ; ////保证页脚只有一个
	private CommonEventLogic commonEventLogic  ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		place = intent.getStringExtra("place");
		ecategoryname = intent.getStringExtra("ecategoryname");
		StartTimeRangA = intent.getStringExtra("StartTimeRangA");
		StartTimeRangB = intent.getStringExtra("StartTimeRangB");
		setContentView(R.layout.fragment_event);
		setView();
		commonEventLogic = new  CommonEventLogicImpl(this , this) ;
		if(StartTimeRangA != null){
		StartTimeRangA = DateUtil.changeDate(StartTimeRangA.replaceAll(" [^a]*\\-", ""));
		StartTimeRangB = DateUtil.changeDate(StartTimeRangB.replaceAll(" [^a]*\\-", ""));
		}
		Log.i("Http","Filter==>" + place + ecategoryname + StartTimeRangA + StartTimeRangB);
		commonEventLogic.getEvent(1, 15,  place, ecategoryname , StartTimeRangA, StartTimeRangB);
	}

	private void setView() {
		 
		listView = (ListView)findViewById(R.id.listView_event);
	     mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

         mPullToRefreshAttacher.addRefreshableView(listView, this);

		eventcardsAdapter = new EventCardsAdapter(this,eventsData);
		swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(eventcardsAdapter);
		swingBottomInAnimationAdapter.setInitialDelayMillis(300);
		swingBottomInAnimationAdapter.setAbsListView(listView);
		
		moreView =   View.inflate(this,R.layout.list_eventlistview_load, null);
		listView.addFooterView(moreView); //添加底部view，一定要在setAdapter之前添加，否则会报错。
		listView.setAdapter(swingBottomInAnimationAdapter);
		listView.removeFooterView(moreView);
		//点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {   
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO 点击方法
				/*Toast.makeText( getActivity(),
						"点击了==》"+eventcardsAdapter.getItem(arg2).uname, 
						Toast.LENGTH_SHORT).show();*/
				
				  Intent intent = new Intent(FilterEventACTIVITY.this,EventPageACTIVITY.class);
				   Bundle bundle = new Bundle();
				   bundle.putSerializable("EventData", eventcardsAdapter.getItem(arg2));
				   intent.putExtras(bundle);
				   startActivity(intent);
				
			}
        });
		listView.setOnScrollListener(new OnScrollListener() {
			int lastItem ;
			int totalItemCount ;
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				lastItem = firstVisibleItem + visibleItemCount ;  
				this.totalItemCount = totalItemCount ;
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) { 
				//下拉到空闲时，且最后一个item的数等于数据的总数时，进行更新
				if(lastItem == totalItemCount  && scrollState == OnScrollListener.SCROLL_STATE_IDLE){ 
					Log.i("Debug", "拉到最底部");
					if(!(listfootflag)) {
					listView.addFooterView(moreView);
					}
					if((!(listflag))&&listfootflag) {
						Log.i("Debug", "=listflag=>" + listflag + "=listfootflag=>" + listfootflag);
						listflag = true ;
					moreView.setVisibility(View.VISIBLE);
					commonEventLogic.getEvent(page, 15, place, ecategoryname , StartTimeRangA, StartTimeRangB);
					}else {
					listfootflag = true ;
				}
				}
			}});

		
	}
	

	@Override
	public void onRefreshStarted(View view) {

		if (!(listflag)) {
			listflag = true ;
			commonEventLogic.getEvent(1, 15,  place, ecategoryname , StartTimeRangA, StartTimeRangB);
	}else {
		mPullToRefreshAttacher.setRefreshComplete();
		Toast.makeText( this, "正在刷新",Toast.LENGTH_SHORT ).show();	
		}
	
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
	        return super.onOptionsItemSelected(item);
	    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1:		//初始化
		if(result != null){
			eventsData.addAll(0,(ArrayList<EventVo>)result);
			ArrayListUtil.removeDuplicate(eventsData);
			ArrayListUtil.sortEventVo(eventsData);
			eventcardsAdapter.notifyDataSetChanged();
			}
		break;
		case 2:		//下拉刷新
			eventsData.addAll(0,(ArrayList<EventVo>)result);
			ArrayListUtil.removeDuplicate(eventsData);
			ArrayListUtil.sortEventVo(eventsData);
            //int count = result.size();
			if(page == 1){page++;}
            eventcardsAdapter.notifyDataSetChanged();
            mPullToRefreshAttacher.setRefreshComplete();
            //listView.setSelection(count) ;
            if(listfootflag){
            	listView.removeFooterView(moreView); //移除底部视图
            	listfootflag = false ;
            }
            listflag =false ;
			break;
		case 3:		//底部刷新
			eventsData.addAll((ArrayList<EventVo>)result); 
			ArrayListUtil.removeDuplicate(eventsData);
			ArrayListUtil.sortEventVo(eventsData);
        	eventcardsAdapter.notifyDataSetChanged();
		    moreView.setVisibility(View.GONE); 
		    page++;
		    listView.removeFooterView(moreView); //移除底部视图
		   	listfootflag = false ;
		   	listflag = false ;
			break;
		case -1 :
			Toast.makeText(this, (String)result, Toast.LENGTH_SHORT).show();
			if(mPullToRefreshAttacher.isRefreshing()){
				mPullToRefreshAttacher.setRefreshComplete();
			}
			if(listfootflag){
            	listView.removeFooterView(moreView); //移除底部视图
            	listfootflag = false ;
            }
            listflag =false ;
			break;
		}
	}
	
}
