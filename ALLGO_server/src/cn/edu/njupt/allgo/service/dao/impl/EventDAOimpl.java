package cn.edu.njupt.allgo.service.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.njupt.allgo.service.dao.EventAddDAO;
import cn.edu.njupt.allgo.service.dao.EventCommentsCreatDAO;
import cn.edu.njupt.allgo.service.dao.EventCreatDAO;
import cn.edu.njupt.allgo.service.dao.EventDestroyDAO;
import cn.edu.njupt.allgo.service.dao.EventDetailsDAO;
import cn.edu.njupt.allgo.service.dao.EventFollowDAO;
import cn.edu.njupt.allgo.service.dao.EventUnFollowDAO;
import cn.edu.njupt.allgo.service.dao.UnreadDAO;
import cn.edu.njupt.allgo.service.utils.DateTimeUtil;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.EventCommentVo;
import cn.edu.njupt.allgo.service.vo.EventFollowerVo;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

public class EventDAOimpl implements EventAddDAO , EventCommentsCreatDAO , EventCreatDAO ,
		EventDestroyDAO , EventDetailsDAO , EventFollowDAO , EventUnFollowDAO{
	
	//添加活动补充
	@Override
	public EventAddVo creatAdd( int eid,String addtime, String text) {

		EventAddVo eventAdd = null;
		try{
			eventAdd = new EventAddVo(-1,text, addtime, eid);
			Session s = HibernateSessionFactory.getSession();
			Transaction t = s.beginTransaction();
			s.save(eventAdd);
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		return eventAdd;
	}

	//删除活动
	@Override
	public EventVo destroyEvent(int eid, int uid) {
		boolean flag = false;
		EventVo event = null;
		try{
			Session s = HibernateSessionFactory.getSession();
			Transaction t = s.beginTransaction();
			event = (EventVo) s.load(EventVo.class, eid);
			if(event.getUid() == uid){
			s.delete(event);
			t.commit();
			s.close();
			flag = true ;
			}else{
			t.commit();
			s.close();
			}
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		if(!flag){
			event = null;
		}
		return event;
	}

	//创建活动
	@Override
	public EventVo creatEvent(String outline, int uid, String uname,
			String startdate, String enddate, String content, String place,
			String position, String ecategoryname, int visible) {
		EventVo eventVo = null;
		try{
			eventVo = new EventVo(-1,outline, uid, uname, startdate, enddate, content, place,
					position, DateTimeUtil.currentTime(), ecategoryname, visible,0,0);
			Session s = HibernateSessionFactory.getSession();
			Transaction t = s.beginTransaction();
			s.save(eventVo);
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		return eventVo;
	}

	//添加评论
	@Override
	public EventCommentVo creatComment(String comment, int uid, int eid,
			String uname, int replyuid, String replyuname) {
		EventCommentVo commentVo = null ;
		
		try{
				commentVo = new EventCommentVo(-1,uid,uname,eid, DateTimeUtil.currentTime(),replyuid,replyuname,comment);
				Session s = HibernateSessionFactory.getSession();
				Transaction t = s.beginTransaction();
				s.save(commentVo);
				t.commit();
				s.close();
		   }catch(Exception e){
			   	e.printStackTrace();
		   }
		
	return commentVo;
	}

	//获得加入活动的人
	@SuppressWarnings("unchecked")
	@Override
	public List<EventFollowerVo> getFollowers(int eid) {
		List<EventFollowerVo> list=null;

		Session session=HibernateSessionFactory.getSession();
		session.clear();
		String hql="from EventFollowerVo vo where vo.eid=?";
		Query query=session.createQuery(hql);
		query.setParameter(0, eid);

		list=query.list();
		
		return list;
	}

	//获得活动补充
	@SuppressWarnings("unchecked")
	@Override
	public List<EventAddVo> getAdd(int eid) {
		List<EventAddVo> list=null;

		Session session=HibernateSessionFactory.getSession();
		session.clear();
		String hql="from EventAddVo vo where vo.eid=?";
		Query query=session.createQuery(hql);
		query.setParameter(0,eid);

		list=query.list();
		
		return list;
	}

	//获得活动评论
	@SuppressWarnings("unchecked")
	@Override
	public List<EventCommentVo> getComments(int eid) {
		List<EventCommentVo> list=null;

		Session session=HibernateSessionFactory.getSession();
		session.clear();
		String hql="from EventCommentVo vo where vo.eid=?";
		Query query=session.createQuery(hql);
		query.setParameter(0, eid);

		list=query.list();
		
		return list;
	}

	//取消加入活动
	@Override
	public boolean unfollow(int eid, int uid) {
		boolean flag = false ;
		try{
			EventFollowerVo pk = new EventFollowerVo();
			pk.setEid(eid);
			pk.setUid(uid);
			Session s = HibernateSessionFactory.getSession();
			Transaction t = s.beginTransaction();
			EventFollowerVo vo = (EventFollowerVo) s.load(EventFollowerVo.class, pk);
			s.delete(vo);
			t.commit();
			s.close();
			flag = true;
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		return flag;
	}

	//加入活动
	@Override
	public EventFollowerVo follow(int eid,String uname , int uid) {
		EventFollowerVo eventFollower = null;
				try{
					Session s = HibernateSessionFactory.getSession();
					Transaction t = s.beginTransaction();
					eventFollower = new EventFollowerVo(uid, uname, eid);
					s.save(eventFollower);
					t.commit();
					s.close();
			   }catch(Exception e){
				   	e.printStackTrace();
			   }
		return eventFollower;
	}

	//检测是否已经跟随
	@Override
	public EventFollowerVo isfollowed(int eid, int uid) {
		EventFollowerVo eventFollower = null;
		Session s = HibernateSessionFactory.getSession();
		String hql="from EventFollowerVo as vo where vo.eid=:eid and vo.uid=:uid";
		Query query=s.createQuery(hql);
		query.setInteger("uid", uid);
		query.setInteger("eid", eid);
		List<EventFollowerVo> list=null;
		list=query.list();
		if(list.size() > 0){
			eventFollower = list.get(0);
		}
		s.close();
		return eventFollower;
	}
	
	//检测活动是否存在
	@Override
	public boolean isExist(int eid) {
		boolean flag = false;
		Session s = HibernateSessionFactory.getSession();
		Transaction t = s.beginTransaction();
		EventVo vo = (EventVo) s.get(EventVo.class, eid);
		t.commit();
		s.close();
		if(vo != null){
			flag = true;
		}
		return flag;
	}
	
}
