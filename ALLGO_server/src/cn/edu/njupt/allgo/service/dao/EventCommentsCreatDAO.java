package cn.edu.njupt.allgo.service.dao;

import cn.edu.njupt.allgo.service.vo.EventCommentVo;

public interface EventCommentsCreatDAO {

	/**
	 * 添加活动评论
	 * @param comment
	 * @param uid
	 * @param eid
	 * @param uname
	 * @param replyuid
	 * @param replyuname
	 * @return
	 */
	public EventCommentVo creatComment(String comment ,int uid,int eid,String uname , int replyuid ,String replyuname);
	
}
