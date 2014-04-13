package cn.edu.njupt.allgo.logic;

import java.util.ArrayList;

import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UnreadVo;
import android.content.Context;

public interface UnreadLogic {

	/**
	 * 联网获取所有未读消息
	 */
	public void getUnread() ;
	
	/**
	 * 初始化活动列表，从数据库得到数据
	 */
	public void initUnread() ;
	
	/**
	 * 保存列表数据到数据库
	 * @param context
	 * @param eventsData
	 */
	public void saveUnread(ArrayList<UnreadVo> eventsData) ;
	
	public void setRead(UnreadVo unread);
}
