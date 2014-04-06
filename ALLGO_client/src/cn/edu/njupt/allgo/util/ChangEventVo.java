package cn.edu.njupt.allgo.util;

import cn.edu.njupt.allgo.vo.CommonEventVo;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.FriendEventVo;
import cn.edu.njupt.allgo.vo.MyEventVo;
import cn.edu.njupt.allgo.vo.PastEventVo;

public class ChangEventVo {
	
	/**
	 * EventVo转到FriendEventVo
	 * 方便数据库操作
	 * @param event
	 * @return
	 */
	public static FriendEventVo event2FriendEvent(EventVo event) {
		
		FriendEventVo firendEvent = new FriendEventVo();
		firendEvent.setEid(event.getEid());
		firendEvent.setOutline(event.getOutline());
		firendEvent.setUid(event.getUid());
		firendEvent.setUname(event.getUname());
		firendEvent.setStartdate(event.getStartdate());
		firendEvent.setEnddate(event.getEnddate());
		firendEvent.setContent(event.getContent());
		firendEvent.setPlace(event.getPlace());
		firendEvent.setPosition(event.getPosition());
		firendEvent.setDateline(event.getDateline());
		firendEvent.setEcategroyname(event.getEcategroyname());
		firendEvent.setVisible(event.getVisible());
		firendEvent.setCommentscount(event.getCommentscount());
		firendEvent.setFollowerscount(event.getFollowerscount());
		return firendEvent;
	}
	
	/**
	 * EventVo转到MyEventVo
	 * 方便数据库操作
	 * @param event
	 * @return
	 */
	public static MyEventVo event2MyEvent(EventVo event) {
			
			MyEventVo myEvent = new MyEventVo();
			myEvent.setEid(event.getEid());
			myEvent.setOutline(event.getOutline());
			myEvent.setUid(event.getUid());
			myEvent.setUname(event.getUname());
			myEvent.setStartdate(event.getStartdate());
			myEvent.setEnddate(event.getEnddate());
			myEvent.setContent(event.getContent());
			myEvent.setPlace(event.getPlace());
			myEvent.setPosition(event.getPosition());
			myEvent.setDateline(event.getDateline());
			myEvent.setEcategroyname(event.getEcategroyname());
			myEvent.setVisible(event.getVisible());
			myEvent.setCommentscount(event.getCommentscount());
			myEvent.setFollowerscount(event.getFollowerscount());
			return myEvent;
		}
	
	/**
	 * EventVo转到PastEventVo
	 * 方便数据库操作
	 * @param event
	 * @return
	 */
	public static PastEventVo event2PastEvent(EventVo event) {
		
		PastEventVo pastEvent = new PastEventVo();
		pastEvent.setEid(event.getEid());
		pastEvent.setOutline(event.getOutline());
		pastEvent.setUid(event.getUid());
		pastEvent.setUname(event.getUname());
		pastEvent.setStartdate(event.getStartdate());
		pastEvent.setEnddate(event.getEnddate());
		pastEvent.setContent(event.getContent());
		pastEvent.setPlace(event.getPlace());
		pastEvent.setPosition(event.getPosition());
		pastEvent.setDateline(event.getDateline());
		pastEvent.setEcategroyname(event.getEcategroyname());
		pastEvent.setVisible(event.getVisible());
		pastEvent.setCommentscount(event.getCommentscount());
		pastEvent.setFollowerscount(event.getFollowerscount());
		return pastEvent;
	}

	/**
	 * EventVo转到CommonEventVo
	 * 方便数据库操作
	 * @param eventVo
	 * @return
	 */
	public static CommonEventVo event2CommonEvent(EventVo event) {
		
		CommonEventVo commonEvent = new CommonEventVo();
		commonEvent.setEid(event.getEid());
		commonEvent.setOutline(event.getOutline());
		commonEvent.setUid(event.getUid());
		commonEvent.setUname(event.getUname());
		commonEvent.setStartdate(event.getStartdate());
		commonEvent.setEnddate(event.getEnddate());
		commonEvent.setContent(event.getContent());
		commonEvent.setPlace(event.getPlace());
		commonEvent.setPosition(event.getPosition());
		commonEvent.setDateline(event.getDateline());
		commonEvent.setEcategroyname(event.getEcategroyname());
		commonEvent.setVisible(event.getVisible());
		commonEvent.setCommentscount(event.getCommentscount());
		commonEvent.setFollowerscount(event.getFollowerscount());
		return commonEvent;
	}
	
	/**
	 * EventVo转到CommonEventVo
	 * 方便数据库操作
	 * @param eventVo
	 * @return
	 */
	public static EventVo event2Event(Object obj) {
		EventVo event = (EventVo)obj;
		EventVo vo = new CommonEventVo();
		vo.setEid(event.getEid());
		vo.setOutline(event.getOutline());
		vo.setUid(event.getUid());
		vo.setUname(event.getUname());
		vo.setStartdate(event.getStartdate());
		vo.setEnddate(event.getEnddate());
		vo.setContent(event.getContent());
		vo.setPlace(event.getPlace());
		vo.setPosition(event.getPosition());
		vo.setDateline(event.getDateline());
		vo.setEcategroyname(event.getEcategroyname());
		vo.setVisible(event.getVisible());
		vo.setCommentscount(event.getCommentscount());
		vo.setFollowerscount(event.getFollowerscount());
		return vo;
	}
	
}
