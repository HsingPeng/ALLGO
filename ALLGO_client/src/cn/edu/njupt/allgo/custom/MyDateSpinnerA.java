package cn.edu.njupt.allgo.custom;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.graphics.Color;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
 
public class MyDateSpinnerA extends Spinner  {
	
    public MyDateSpinnerA(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
 
    public MyDateSpinnerA(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        final Time time = new Time();
        time.setToNow();
        //为MyDateSpinner设置adapter，主要用于显示spinner的text值
        MyDateSpinnerA.this.setAdapter(new BaseAdapter() {
        	private TextView text ;
        	
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return 1;
            }
 
            @Override
            public Object getItem(int arg0) {
                // TODO getItem
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
                text = new TextView(MyDateSpinnerA.this.getContext());
                text.setText(time.year
                        + "年"
                        + (time.month + 1)
                        + "月"
                        + time.monthDay
                        + "日 "
                        + MyDatePickerDialog.CaculateWeekDay(time.year,
                                time.month+1, time.monthDay));
                text.setTextColor(Color.GRAY);
                
                return text;
            }
        });
    }
 
    @Override
    public boolean performClick() {
        Time time = new Time();
        time.setToNow();
        MyDatePickerDialog tpd = new MyDatePickerDialog(getContext(),
                new OnDateSetListener() {
 
                    @Override
                    public void onDateSet(DatePicker view, final int year,
                            final int month, final int day) {
                        // TODO Auto-generated method stub
                        //为MyDateSpinner动态设置adapter，主要用于修改spinner的text值
                        MyDateSpinnerA.this.setAdapter(new BaseAdapter() {
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
                                // TODO Auto-generated method stub
                            	
                                text = new TextView(MyDateSpinnerA.this
                                        .getContext());
                                text.setText(year
                                        + "年"
                                        + (month + 1)
                                        + "月"
                                        + day
                                        + "日 "
                                        + MyDatePickerDialog.CaculateWeekDay(
                                                year, month + 1, day));
                                text.setTextColor(Color.BLACK);
                                return text;
                            }
                        });
                    }
 
                }, time.year, time.month, time.monthDay);
        tpd.show();
        return true;
    }

}
