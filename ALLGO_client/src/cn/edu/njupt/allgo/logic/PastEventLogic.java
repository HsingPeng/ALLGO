package cn.edu.njupt.allgo.logic;

import java.util.ArrayList;

import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.FriendEventVo;

public interface PastEventLogic {

	/**
	 * 联网更新
	 * @param page
	 * @param pagenum
	 */
	public void getEvent(int page , int pagenum);
	
	/**
	 * 从数据库初始化
	 */
	public void initEvent();
	
	/**
	 * 保存列表数据到数据库
	 * @param eventsData
	 */
	public void saveEvent(ArrayList<EventVo> eventsData);
	
}
