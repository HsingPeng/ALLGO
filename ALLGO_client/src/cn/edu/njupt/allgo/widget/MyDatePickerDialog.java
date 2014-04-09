package cn.edu.njupt.allgo.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
 
public class MyDatePickerDialog extends DatePickerDialog{
     
    public MyDatePickerDialog(Context context, OnDateSetListener callBack,
            int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        // TODO Auto-generated constructor stub
        setTitle(year,monthOfYear,dayOfMonth);
    }
 
    public MyDatePickerDialog(Context context, int theme,
            OnDateSetListener callBack, int year, int monthOfYear,
            int dayOfMonth) {
        super(context, theme, callBack, year, monthOfYear, dayOfMonth);
        // TODO Auto-generated constructor stub
    }
     
    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day){
        super.onDateChanged(view, year, month, day);
        setTitle(year,month,day);
    }
     
    //计算当前选择的日期为周几
    public static String CaculateWeekDay(int y,int m,int d){
        if(m==1){m=13;y--;}
        if(m==2){m=14;y--;}
        int c=y/100;
        y%=100;
        int week=(c/4-2*c+y+y/4+13*(m+1)/5+d-1)%7;
        if(week<0){week=7-(-week)%7;}
        switch(week){
        case 1:return "周一";
        case 2:return "周二";
        case 3:return "周三";
        case 4:return "周四";
        case 5:return "周五";
        case 6:return "周六";
        default:return "周日";
        }
    }
     
    public void setTitle(int year,int month,int day){
        this.setTitle(year+"-"+(month+1)+"-"+day+MyDatePickerDialog.CaculateWeekDay(year,month+1,day));
    }
}
