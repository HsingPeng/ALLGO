package cn.edu.njupt.allgo.service.dao;

import java.util.List;

import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.EventCommentVo;
import cn.edu.njupt.allgo.service.vo.EventFollowerVo;

public interface EventDetailsDAO {

	/**
	 * 得到活动参与者
	 * @param eid
	 * @return
	 */
	public List<EventFollowerVo> getFollowers(int eid);

	/**
	 * 得到活动补充
	 * @param eid
	 * @return
	 */
	public List<EventAddVo> getAdd(int eid);
	
	/**
	 * 得到活动评论
	 * @param eid
	 * @return
	 */
	public List<EventCommentVo> getComments(int eid);
	
	/**
	 * 检测活动是否存在
	 * @return
	 */
	public boolean isExist(int eid);
	
}
