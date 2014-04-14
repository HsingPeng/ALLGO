package cn.edu.njupt.allgo.activity;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import cn.edu.njupt.allgo.fragment.MyHomePageFRAGMENT;

public class MyEventACTIVITY extends BaseActivity {
	private PullToRefreshAttacher mPullToRefreshAttacher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                new MyHomePageFRAGMENT()).commit();
		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

	public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	finish();
        }
        return super.onOptionsItemSelected(item);
    }
	
}
