package cn.edu.njupt.allgo.service.dao;

import java.util.List;

import cn.edu.njupt.allgo.service.vo.UnreadVo;

public interface ActionDAO {

	/**
	 * 存储未读消息
	 * @param UID
	 * @param RCategroy
	 * @param ID
	 * @param Action
	 * @param Annotation
	 * @param time
	 * @param isread
	 * @return UnreadVo
	 */
	public UnreadVo putUnread(int UID ,int RCategroy , int ID ,
			int Action ,String Annotation , String time , boolean isread);
	
	/**
	 * 得到活动的创建者
	 * @param EID
	 * @return
	 */
	public int getEventUID(int eid);
	
	/**
	 * 得到所有参与活动的人
	 * @param eid
	 * @return
	 */
	public List<Integer> getAllFollowerUser(int eid);
	
	/**
	 * 删除活动参与者
	 * @param eid
	 */
	public void deleteFollower(int eid);
	
	/**
	 * 删除活动评论
	 * @param eid
	 */
	public void deleteComment(int eid);
	
	/**
	 * 删除活动补充
	 * @param add
	 */
	public void deleteAdd(int eid);
	
	/**
	 * 活动补充计数加一
	 * @param eid
	 */
	public void countFollower(int eid);
	
	/**
	 * 活动评论计数加一
	 * @param eid
	 */
	public void countComment(int eid);
}
