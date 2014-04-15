package cn.edu.njupt.allgo.activity;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import cn.edu.njupt.allgo.fragment.UnreadFRAGMENT;

public class UnreadACTIVITY extends BaseActivity {

	private PullToRefreshAttacher mPullToRefreshAttacher;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                new UnreadFRAGMENT()).commit();
    }

	public PullToRefreshAttacher getPullToRefreshAttacher() {
        return mPullToRefreshAttacher;
    }

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               finish();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onDestroy() {
		Log.i("UnreadAcitivity", "ondestroy");
		super.onDestroy();
	}
	
	
	
}
