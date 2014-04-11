package cn.edu.njupt.allgo.service.listener;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.WsOutbound;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.njupt.allgo.service.dao.ActionDAO;
import cn.edu.njupt.allgo.service.dao.UnreadDAO;
import cn.edu.njupt.allgo.service.dao.impl.ActionDAOimpl;
import cn.edu.njupt.allgo.service.dao.impl.UnreadDAOimpl;
import cn.edu.njupt.allgo.service.utils.DateTimeUtil;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.EventCommentVo;
import cn.edu.njupt.allgo.service.vo.EventFollowerVo;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;
import cn.edu.njupt.allgo.service.vo.ServerMsg;
import cn.edu.njupt.allgo.service.vo.UnreadVo;



@WebListener
public class ActionListener implements ServletContextAttributeListener {

	private Map<Integer, WsOutbound> userMap ;
	private ActionDAO dao = new ActionDAOimpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {
		if(userMap == null){
			userMap = (Map<Integer, WsOutbound>) arg0.getServletContext().getAttribute("OnLineList");
		}
		//System.out.println("listener==>attributeAdded");
		Enumeration<String> att = arg0.getServletContext().getAttributeNames();
		while(att.hasMoreElements()){
			String next = att.nextElement();
			if(next.startsWith("action")){
					ServerMsg message = (ServerMsg) arg0.getServletContext().getAttribute(next);
				if(message != null){
				arg0.getServletContext().removeAttribute(next);
				doAction(message);
				}
			}
		}
	}


	//处理HttpServlet提交的动作
	private void doAction(ServerMsg message) {
		switch(message.getType()){
		case 9:		//提交活动评论
			action9(message);
			break;
		case 10:	//加入活动
			action10(message);
			break;
		case 11:	//取消加入活动
			action11(message);
			break;
		case 14:	//添加活动补充
			action14(message);
			break;
		case 15:	//删除活动
			action15(message);
			break;
		}
	}


	//提交活动评论
	private void action9(ServerMsg message){
		EventCommentVo action = (EventCommentVo) message.getAction();
		String text = action.getTexts();
		int ruid = action.getReplyuid();
		int eid = action.getEid();
		int uid = action.getUid();
		dao.countComment(eid);	//活动评论数+1
		
		if(ruid != -1&&ruid != uid){		//发送给被评论者
			WsOutbound outbound = userMap.get(ruid);
			if(outbound != null){
				UnreadVo unread = dao.putUnread(ruid,1,action.getEid(),0,text,DateTimeUtil.currentTime(),true);
				unread.setIsread(false);
				sendUnread(unread,outbound);
				
			}else{
				dao.putUnread(ruid,1,action.getEid(),0,text,DateTimeUtil.currentTime(),false);
			}
		}
		int euid = dao.getEventUID(eid);
		if(ruid !=euid&&uid != euid ){		//发送给活动创建者
			
			WsOutbound outbound = userMap.get(euid);
			if(outbound != null){
				Map<String, Object> outMap = new HashMap<String, Object>();
				outMap.put("response", "remind_unread");
				UnreadVo unread = dao.putUnread(euid,2,action.getEid(),0,text,DateTimeUtil.currentTime(),true);
				unread.setIsread(false);
				sendUnread(unread,outbound);
			}else{
				dao.putUnread(euid,2,action.getEid(),0,text,DateTimeUtil.currentTime(),false);
			}
		}
	}

	//发送到目标用户
	private void sendUnread(UnreadVo unread, WsOutbound outbound) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		outMap.put("response", "remind_unread");
		List<UnreadVo> list = new ArrayList<UnreadVo>();
		list.add(unread);
		outMap.put("remind_unread", list);
		String jsonString = JSONObject.fromObject(outMap).toString();
		CharBuffer buffer = CharBuffer.wrap(jsonString);
		try {
			outbound.writeTextMessage(buffer);
			outbound.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	//加入活动
	private void action10(ServerMsg message){
		EventFollowerVo action = (EventFollowerVo) message.getAction();
		int eid = action.getEid();
		String uname = action.getUname();
		dao.countFollower(eid);		//活动参与人数计数+1
		//发送给活动创建者
		int euid = dao.getEventUID(eid);
		WsOutbound outbound = userMap.get(euid);
		if(outbound != null){
			UnreadVo unread = dao.putUnread(euid,3,action.getEid(),0,uname+"加入了你的活动",DateTimeUtil.currentTime(),true);
			unread.setIsread(false);
			sendUnread(unread,outbound);
		}else{
			dao.putUnread(euid,3,action.getEid(),0,uname+"加入了你的活动",DateTimeUtil.currentTime(),false);
		}
	}
	

	private void action11(ServerMsg message) {
		
		dao.cutFollower((int) message.getAction());
		
	}
	
	//添加活动补充
	public void action14(ServerMsg message){
		EventAddVo action = (EventAddVo) message.getAction();
		int eid = action.getEid();
		List<Integer> uids = dao.getAllFollowerUser(eid);
		for(int uid:uids){
			WsOutbound outbound = userMap.get(uid);
			if(outbound != null){
				UnreadVo unread = dao.putUnread(uid,0,eid,0,action.getContent(),DateTimeUtil.currentTime(),true);
				unread.setIsread(false);
				sendUnread(unread,outbound);
			}else{
				dao.putUnread(uid,0,eid,0,action.getContent(),DateTimeUtil.currentTime(),false);
			}
		}
	}
	
	//删除活动
	public void action15(ServerMsg message){
		EventVo action = (EventVo) message.getAction();
		int eid = action.getEid();
		List<Integer> uids = dao.getAllFollowerUser(eid);	//通知所有参与的用户
		for(int uid:uids){
			WsOutbound outbound = userMap.get(uid);
			if(outbound != null){
				UnreadVo unread = dao.putUnread(uid,4,eid,1,action.getOutline(),DateTimeUtil.currentTime(),true);
				unread.setIsread(false);
				sendUnread(unread,outbound);
			}else{
				dao.putUnread(uid,4,eid,1,action.getOutline(),DateTimeUtil.currentTime(),false);
			}
		}
		
		dao.deleteFollower(eid);		//删除所有参与者,补充和评论
		dao.deleteAdd(eid);
		dao.deleteComment(eid);
		
	}
	
	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {


	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {

	}

}
