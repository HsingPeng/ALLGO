package cn.edu.njupt.allgo;


import cn.edu.njupt.allgo.util.DensityUtil;
import cn.edu.njupt.allgo.widget.MyDateSpinnerA;
import cn.edu.njupt.allgo.widget.MyDateSpinnerB;
import cn.edu.njupt.allgo.widget.MyTimeSpinnerA;
import cn.edu.njupt.allgo.widget.MyTimeSpinnerB;
import cn.edu.njupt.allgo.widget.PlaceSpinner;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class FilterACTIVITY extends BaseActivity {

	private Spinner province_spinner;
	private Spinner city_spinner;
	private Spinner county_spinner;
	private String position ;
	
	private RadioGroup group_Category1;
	private String categoryname1 = "旅游" ;
	private Button button_submit1;
	private Button button_cancel1;
	private CheckBox CheckBox_categroy;
	private CheckBox checkBox_StartTime;
	private CheckBox checkBox_place;
	
	private boolean isCheckBox_categroy = false ;
	private boolean ischeckBox_StartTime = false ;
	private boolean ischeckBox_place = false ;
	private int high = 480 ;
	private RelativeLayout RelativeLayout_time;
	private LinearLayout LinearLayout_place;
	private MyDateSpinnerA dateSpinnerA1;
	private MyTimeSpinnerA timeSpinnerA1;
	private MyDateSpinnerB dateSpinnerB1;
	private MyTimeSpinnerB timeSpinnerB1;
	private LinearLayout LinearLayout_place_parent;
	private RelativeLayout relativeLayout_filter_01;
	
	private PlaceSpinner placeSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
		setFinishOnTouchOutside(false);
       
        CheckBox_categroy =(CheckBox) findViewById(R.id.CheckBox_categroy);
		group_Category1 = (RadioGroup)this.findViewById(R.id.radioGroup_Category1);
		checkBox_StartTime =(CheckBox) findViewById(R.id.checkBox_StartTime);
		RelativeLayout_time = (RelativeLayout)findViewById(R.id.RelativeLayout_time);
		dateSpinnerA1 = (MyDateSpinnerA) findViewById(R.id.datespinnerA1);
        timeSpinnerA1 = (MyTimeSpinnerA) findViewById(R.id.timespinnerA1);
        dateSpinnerB1 = (MyDateSpinnerB) findViewById(R.id.datespinnerB1);
        timeSpinnerB1 = (MyTimeSpinnerB) findViewById(R.id.timespinnerB1);
		checkBox_place =(CheckBox) findViewById(R.id.checkBox_place);
		LinearLayout_place_parent = (LinearLayout) findViewById(R.id.LinearLayout_place_parent);
		relativeLayout_filter_01 = (RelativeLayout) findViewById(R.id.relativeLayout_filter_01);
		LinearLayout_place = (LinearLayout) findViewById(R.id.LinearLayout_place);
		button_submit1 = (Button) findViewById(R.id.button_submit1);
        button_cancel1 = (Button) findViewById(R.id.Button_cancel1);
		
        province_spinner = (Spinner) findViewById(R.id.province_spinner);
		city_spinner = (Spinner) findViewById(R.id.city_spinner);
		county_spinner = (Spinner) findViewById(R.id.county_spinner);
        
        Rect frames = new Rect();  
        FilterACTIVITY.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(frames);  
        if(frames.width() < 500 ) { 
        	RadioButton radio_tour1 = (RadioButton)findViewById(R.id.radio_tour1);
        	RadioButton radio_dine1 = (RadioButton)findViewById(R.id.radio_dine1);
        	RadioButton radio_sport1 = (RadioButton)findViewById(R.id.radio_sport1);
        	RadioButton radio_amuse1 = (RadioButton)findViewById(R.id.radio_amuse1);
        	RadioButton radio_meet1 = (RadioButton)findViewById(R.id.radio_meet1);
        	radio_tour1.setPadding(40, 4, 4, 4);
        	radio_dine1.setPadding(40, 4, 4, 4);
        	radio_sport1.setPadding(40, 4, 4, 4);
        	radio_amuse1.setPadding(40, 4, 4, 4);
        	radio_meet1.setPadding(40, 4, 4, 4);
        }
        float scale = getBaseContext().getResources().getDisplayMetrics().density;
        high = (int)(230*scale+0.5f);
        LayoutParams p = getWindow().getAttributes();
        p.height = (int) (high);
        getWindow().setAttributes(p);
        
        CheckBox_categroy.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                 
            	isCheckBox_categroy = arg1 ;
            	group_Category1.setVisibility(arg1?View.VISIBLE:View.GONE);
            		setSize(arg1,1);
            	}
        });
        
        checkBox_StartTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            	ischeckBox_StartTime = arg1 ;
            	RelativeLayout_time.setVisibility(arg1?View.VISIBLE:View.GONE);
                setSize(arg1,4);
            	}
        });
        
        checkBox_place.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            	ischeckBox_place = arg1 ;
            	LinearLayout_place.setVisibility(arg1?View.VISIBLE:View.GONE);
            	setSize(arg1,1);
            	}
        });
        
		group_Category1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 
				int radioButtonId = group.getCheckedRadioButtonId();
	                //根据ID获取RadioButton的实例
	                 RadioButton rb = (RadioButton)FilterACTIVITY.this.findViewById(radioButtonId);
	                 //更新文本内容，以符合选中项
	                 categoryname1 = rb.getText().toString() ;
			}});
        
		button_submit1.setOnClickListener(new View.OnClickListener() {
			//TODO 提交活动选项
			@Override
			public void onClick(View v) {
				/*if(isCheckBox_categroy && ischeckBox_StartTime && ischeckBox_place) {
				String dateA = (String)dateSpinnerA1.getItemAtPosition(0) ;
				String timeA = (String)timeSpinnerA1.getItemAtPosition(0) ;
				String dateB = (String)dateSpinnerB1.getItemAtPosition(0) ;
				String timeB = (String)timeSpinnerB1.getItemAtPosition(0) ;
				
				Toast.makeText(FilterACTIVITY.this,
						"类型==>" + isCheckBox_categroy + categoryname1 + "\n" +
						"时间==>" + ischeckBox_StartTime + dateA + timeA + "-->" + dateB + timeB + "\n" +
						"地点==>" + ischeckBox_place + display
						, Toast.LENGTH_SHORT).show();
				}*/
				
				if(isCheckBox_categroy || ischeckBox_StartTime || ischeckBox_place){
					Intent intent = new Intent(FilterACTIVITY.this,FilterEventACTIVITY.class);
					if(ischeckBox_place){
						position = placeSpinner.getPosition();
						intent.putExtra("place", position);
					}
					if(isCheckBox_categroy){
					intent.putExtra("ecategoryname", categoryname1);
					}
					if(ischeckBox_StartTime){
						String dateA = (String)dateSpinnerA1.getItemAtPosition(0) ;
						String timeA = (String)timeSpinnerA1.getItemAtPosition(0) ;
						String dateB = (String)dateSpinnerB1.getItemAtPosition(0) ;
						String timeB = (String)timeSpinnerB1.getItemAtPosition(0) ;
					intent.putExtra("StartTimeRangA", dateA+"-"+timeA);
					intent.putExtra("StartTimeRangB", dateB+"-"+timeB);
					}
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(FilterACTIVITY.this, "未选择任何条件", Toast.LENGTH_SHORT).show();
				}
			}
		});
        button_cancel1.setOnClickListener(new View.OnClickListener() {
			//TODO 提交活动
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		loadSpinner(); 
	}
	
	private void loadSpinner() {
		placeSpinner = new PlaceSpinner(this , province_spinner , city_spinner , county_spinner);
		placeSpinner.loadSpinner();
	}

	/**
	 * 设置布局大小
	 * @param arg1
	 * @param i
	 */
	private void setSize(boolean arg1 ,int i) {
		int j = CheckBox_categroy.getBottom() - CheckBox_categroy.getTop() ;
		i *= j ;
		if(arg1) 
			{
			if((relativeLayout_filter_01.getTop() - LinearLayout_place_parent.getBottom()) < i ) 
				{
	        	LayoutParams p = getWindow().getAttributes();
	        	high += i;
	        	p.height = (int) (high);
	            getWindow().setAttributes(p);
				}
			}else{
				
				LayoutParams p = getWindow().getAttributes();
	        	high -= i;
	        	p.height = (int) (high);
	            getWindow().setAttributes(p);
			}
	}

	
}
