package cn.edu.njupt.allgo;


import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.custom.MyDateSpinnerA;
import cn.edu.njupt.allgo.custom.MyDateSpinnerB;
import cn.edu.njupt.allgo.custom.MyTimeSpinnerA;
import cn.edu.njupt.allgo.custom.MyTimeSpinnerB;
import cn.edu.njupt.allgo.fragment.PlaceSpinnerDialogFRAGMENT;
import cn.edu.njupt.allgo.logic.AddEventLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logicImpl.AddEventLogicImpl;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddEventACTIVITY extends BaseActivity implements RefreshInterFace{
	
	private MyDateSpinnerA dateSpinnerA;
	private MyTimeSpinnerA timeSpinnerA;
	private MyDateSpinnerB dateSpinnerB;
	private MyTimeSpinnerB timeSpinnerB;
	private Button button_submit;
	private EditText editText_Outline;
	private CheckBox checkBox_showSpinnerB;
	private EditText editText_place;
	private RadioGroup group_Category;
	private String categoryname = "旅游" ;
	private String visible = "所有人" ;
	private boolean isSpinnerB = false ;
	private RadioGroup group_visiable;
	private EditText event_content;
	private Button button_cancel;
	private ProgressDialog progressDialog;
	private AddEventLogic addEventLogic ;
	private Button button_addevent_position;
	private String position;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addevent);
		addEventLogic = new AddEventLogicImpl(this,this);
        if(getActionBar() != null) {
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		setFinishOnTouchOutside(false);
		
        editText_Outline = (EditText) findViewById(R.id.editText_Outline);
        checkBox_showSpinnerB =(CheckBox) findViewById(R.id.checkBox_showDateB);
        dateSpinnerA = (MyDateSpinnerA) findViewById(R.id.datespinnerA);
        timeSpinnerA = (MyTimeSpinnerA) findViewById(R.id.timespinnerA);
        dateSpinnerB = (MyDateSpinnerB) findViewById(R.id.datespinnerB);
        timeSpinnerB = (MyTimeSpinnerB) findViewById(R.id.timespinnerB);
        editText_place = (EditText) findViewById(R.id.editText_place);
        group_Category = (RadioGroup)this.findViewById(R.id.radioGroup_Category);
        group_visiable = (RadioGroup)this.findViewById(R.id.radioGroup_visiable);
        button_submit = (Button) findViewById(R.id.button_submit);
        button_cancel = (Button) findViewById(R.id.Button_cancel);
        event_content = (EditText) findViewById(R.id.event_content);
        button_addevent_position = (Button)findViewById(R.id.button_addevent_position);
        
        Rect frames = new Rect();  
        AddEventACTIVITY.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frames);  
        if(frames.width() < 500 ) { 
        	RadioButton radio_tour1 = (RadioButton)findViewById(R.id.radio_tour);
        	RadioButton radio_dine1 = (RadioButton)findViewById(R.id.radio_dine);
        	RadioButton radio_sport1 = (RadioButton)findViewById(R.id.radio_sport);
        	RadioButton radio_amuse1 = (RadioButton)findViewById(R.id.radio_amuse);
        	RadioButton radio_meet1 = (RadioButton)findViewById(R.id.radio_meet);
        	radio_tour1.setPadding(40, 4, 4, 4);
        	radio_dine1.setPadding(40, 4, 4, 4);
        	radio_sport1.setPadding(40, 4, 4, 4);
        	radio_amuse1.setPadding(40, 4, 4, 4);
        	radio_meet1.setPadding(40, 4, 4, 4);
        }
        
        checkBox_showSpinnerB.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                 
            	isSpinnerB = arg1 ;
            	dateSpinnerB.setVisibility(arg1?View.VISIBLE:View.GONE);
            	timeSpinnerB.setVisibility(arg1?View.VISIBLE:View.GONE);
            }
        });
        
        group_Category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 
				int radioButtonId = group.getCheckedRadioButtonId();
	                //根据ID获取RadioButton的实例
	                 RadioButton rb = (RadioButton)AddEventACTIVITY.this.findViewById(radioButtonId);
	                 //更新文本内容，以符合选中项
	                 categoryname = rb.getText().toString() ;
/*	                 Toast.makeText(AddEventACTIVITY.this,
	                		 categoryname
	 						, Toast.LENGTH_SHORT).show();*/
			}});
        
        group_visiable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				int radioButtonId = group.getCheckedRadioButtonId();
	                //根据ID获取RadioButton的实例
	                 RadioButton rb = (RadioButton)AddEventACTIVITY.this.findViewById(radioButtonId);
	                 //更新文本内容，以符合选中项
	                 visible = rb.getText().toString() ;
	   /*              Toast.makeText(AddEventACTIVITY.this,
	                		 visible
	 						, Toast.LENGTH_SHORT).show();*/
			}});
        
        button_addevent_position.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 显示PlaceSpinner
				PlaceSpinnerDialogFRAGMENT newFragment = PlaceSpinnerDialogFRAGMENT.newInstance("PostionSpinner");
		        newFragment.show(getSupportFragmentManager(), "dialog");
			}
		});
        
        button_submit.setOnClickListener(new View.OnClickListener() {
			//TODO 提交活动
			@Override
			public void onClick(View v) {
				 
				String dateA = (String)dateSpinnerA.getItemAtPosition(0) ;
				String timeA = (String)timeSpinnerA.getItemAtPosition(0) ;
				/*Toast.makeText(AddEventACTIVITY.this,
						editText_Outline.getText()
						+ dateA + timeA + isSpinnerB + categoryname 
						+ editText_place.getText() + visible 
						+ event_content.getText()  
						, Toast.LENGTH_SHORT).show();*/
				
				if(editText_Outline.getText().toString().equals("")){
						Toast.makeText(AddEventACTIVITY.this, "活动概要没写", Toast.LENGTH_SHORT).show();
					}else if(editText_place.getText().toString().equals("")){
						Toast.makeText(AddEventACTIVITY.this, "活动地点没写", Toast.LENGTH_SHORT).show();
					}else if(event_content.getText().toString().equals("")){
						Toast.makeText(AddEventACTIVITY.this, "请写点详细内容", Toast.LENGTH_SHORT).show();
					}else if(position == null){
						Toast.makeText(AddEventACTIVITY.this, "请点击区域按钮选择地理位置", Toast.LENGTH_SHORT).show();
					}else if(isSpinnerB){
						showProgressDialog("正在提交");
						String dateB = (String)dateSpinnerB.getItemAtPosition(0) ;
						String timeB = (String)timeSpinnerB.getItemAtPosition(0) ;
						addEventLogic.addEvent(editText_Outline.getText().toString(), dateA+"-"+timeA, dateB+"-"+timeB,
								event_content.getText().toString(), editText_place.getText().toString(),
								position, categoryname, visible.equals("所有人")?0:1);
					}else{
						showProgressDialog("正在提交");
						addEventLogic.addEvent(editText_Outline.getText().toString(), dateA + "-"+ timeA, null,
								event_content.getText().toString(), editText_place.getText().toString(),
								position, categoryname, visible.equals("所有人")?0:1);
					}
			}
		});
        button_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
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

    
    
    
    
    /**
	 * 显示提示框
	 */
	private void showProgressDialog(String title) {
		if ((!isFinishing()) && (this.progressDialog == null)) {
			this.progressDialog = new ProgressDialog(this);
			}
			progressDialog.setCancelable(false);
			this.progressDialog.setTitle(title);
			this.progressDialog.setMessage("请稍等...");
			this.progressDialog.show();
	}
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeProgressDialog();
		Log.i("Activity"," onDestroy() ==> AddEventACTIVITY");
	}
	
	/**
	 * 关闭提示框
	 */
	protected void closeProgressDialog() {
		if (this.progressDialog != null)
			this.progressDialog.dismiss();
	}

	public void changePosition(String position){
		this.position = position;
	}
	
	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1:
			Toast.makeText(this,"成功提交活动", Toast.LENGTH_SHORT).show();
			closeProgressDialog();
			finish();
			break;
		case -1:
			String arg1 = (String) result ;
	    	Toast.makeText(this,arg1, Toast.LENGTH_SHORT).show();
	    	closeProgressDialog();
			break;
		
		}
		
	}
    
}
