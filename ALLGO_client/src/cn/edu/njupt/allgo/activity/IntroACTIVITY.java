package cn.edu.njupt.allgo.activity;


import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.fragment.IntroFRAGMENT;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class IntroACTIVITY extends BaseActivity {

	

	private IntroFRAGMENT intro;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		intro = new IntroFRAGMENT();
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
			intro).commit();
		
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuItem actionItem1 = menu.add("刷新");
        actionItem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        actionItem1.setIcon(R.drawable.ic_action_refresh);
		
		return super.onCreateOptionsMenu(menu);
	}



	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	finish();
            	return true ;
        }
        if(item.getTitle().equals("刷新")) {
        	intro.get();
			return true ;
		}
        return super.onOptionsItemSelected(item);
    }
	
}
