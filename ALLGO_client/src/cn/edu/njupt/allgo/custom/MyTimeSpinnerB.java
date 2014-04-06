package cn.edu.njupt.allgo.custom;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
 
public class MyTimeSpinnerB extends Spinner {
	
    public MyTimeSpinnerB(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
 
    public MyTimeSpinnerB(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        final Time time = new Time();
        time.setToNow();
        //为MyDateSpinner设置adapter，主要用于显示spinner的text值
        MyTimeSpinnerB.this.setAdapter(new BaseAdapter() {
        	private TextView text ;
        	
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return 1;
            }
 
            @Override
            public Object getItem(int arg0) {
                // TODO Auto-generated method stub
                return text.getText().toString();
            }
 
            @Override
            public long getItemId(int arg0) {
                // TODO Auto-generated method stub
                return 0;
            }
 
            @Override
            public View getView(int arg0, View arg1, ViewGroup arg2) {
                // TODO Auto-generated method stub
                text = new TextView(MyTimeSpinnerB.this.getContext());
                text.setText(
                		time.hour+3
                		+":"
                		+time.minute
                		);
                text.setTextColor(Color.GRAY);
                return text;
            }
        });
    }
 
    @Override
    public boolean performClick() {
        Time time = new Time();
        time.setToNow();
        TimePickerDialog dialog2 = new TimePickerDialog(getContext(),
				new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view,final int hourOfDay,
							final int minute) {
						// TODO Auto-generated method stub

                        // TODO Auto-generated method stub
                        //为MyDateSpinner动态设置adapter，主要用于修改spinner的text值
						MyTimeSpinnerB.this.setAdapter(new BaseAdapter() {
							private TextView text ;
							
                            @Override
                            public int getCount() {
                                // TODO Auto-generated method stub
                                return 1;
                            }
 
                            @Override
                            public Object getItem(int arg0) {
                                // TODO Auto-generated method stub
                                return text.getText().toString();
                            }
 
                            @Override
                            public long getItemId(int arg0) {
                                // TODO Auto-generated method stub
                                return 0;
                            }
 
                            @Override
                            public View getView(int arg0, View arg1,
                                    ViewGroup arg2) {
                            	text = new TextView(MyTimeSpinnerB.this
                                        .getContext());
        						text.setText(
        								hourOfDay
        		                		+":"
        		                		+minute
        		                		);
        						text.setTextColor(Color.BLACK);
        						return text ;
                            }
                        });
					}
				},time.hour, time.minute, true);
        dialog2.show();
        return true;
    }
}
