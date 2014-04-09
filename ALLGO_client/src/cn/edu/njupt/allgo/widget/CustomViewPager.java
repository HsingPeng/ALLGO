package cn.edu.njupt.allgo.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class CustomViewPager extends ViewPager {  
	  
    private boolean isCanScroll = true;  
  
    public CustomViewPager(Context context) {  
        super(context);  
    }  
  
    public CustomViewPager(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public void setScanScroll(boolean isCanScroll){  
        this.isCanScroll = isCanScroll;  
    }  
  
  
    @Override  
    public void scrollTo(int x, int y){  
        if (isCanScroll){  
            super.scrollTo(x, y);  
        }  
    } 

    
}