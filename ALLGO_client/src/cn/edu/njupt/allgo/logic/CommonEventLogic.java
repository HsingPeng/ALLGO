package cn.edu.njupt.allgo.logic;

import java.util.ArrayList;

import cn.edu.njupt.allgo.vo.EventVo;
import android.content.Context;

public interface CommonEventLogic {

	/**
	 * 联网获取所有活动
	 * @param context
	 * @param refresh
	 * @param page
	 * @param pagenum
	 * @param place
	 * @param ecategoryname
	 * @param StartTimeRangA
	 * @param StartTimeRangB
	 * @return
	 */
	public void getEvent(int page , int pagenum , String place
			, String ecategoryname , String StartTimeRangA , String StartTimeRangB) ;
	
	/**
	 * 初始化活动列表，从数据库得到数据
	 * @param context
	 */
	public void initEvent() ;
	
	/**
	 * 保存列表数据到数据库
	 * @param context
	 * @param eventsData
	 */
	public void saveEvent(ArrayList<EventVo> eventsData) ;
}
