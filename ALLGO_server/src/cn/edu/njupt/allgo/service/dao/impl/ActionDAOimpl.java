package cn.edu.njupt.allgo.service.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.njupt.allgo.service.dao.ActionDAO;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.EventCommentVo;
import cn.edu.njupt.allgo.service.vo.EventFollowerVo;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;
import cn.edu.njupt.allgo.service.vo.UnreadVo;

public class ActionDAOimpl implements ActionDAO {

	@Override
	public UnreadVo putUnread(int UID, int RCategroy, int ID, int Action,
			String Annotation, String time, boolean isread) {
		UnreadVo unread = null;
		try{
			unread = new UnreadVo(-1,UID, RCategroy, ID, Action, Annotation, time,isread);
			Session s = HibernateSessionFactory.getSession();
			Transaction t = s.beginTransaction();
			s.save(unread);
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		return unread ;
	}

	@Override
	public int getEventUID(int eid) {
		EventVo event = null;
		try{
			Session s = HibernateSessionFactory.getSession();
			Transaction t = s.beginTransaction();
			event = (EventVo) s.load(EventVo.class, eid);
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		if(event == null){
			return -1;
		}else{
			return event.getUid();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getAllFollowerUser(int eid) {
		List<Integer> list=null;
		
		try{
			Session session=HibernateSessionFactory.getSession();
			session.clear();
			String hql="select vo.uid from EventFollowerVo vo where vo.eid=:eid";
			Query query=session.createQuery(hql);
			query.setParameter("eid",eid);
			list=query.list();

		}catch(Exception e){
		   	e.printStackTrace();
	   }

		return list;
	}

	@Override
	public void deleteComment(int eid) {
		try{
			Session s = HibernateSessionFactory.getSession();
			String hql="from EventCommentVo vo where vo.eid=:eid";
			Query query=s.createQuery(hql);
			query.setParameter("eid",eid);
			@SuppressWarnings("unchecked")
			List<EventCommentVo> list=query.list();
			Transaction t = s.beginTransaction();
			for(EventCommentVo comment:list){
				s.delete(comment);
			}
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		
	}

	@Override
	public void deleteAdd(int eid) {
		try{
			Session s = HibernateSessionFactory.getSession();
			String hql="from EventAddVo vo where vo.eid=:eid";
			Query query=s.createQuery(hql);
			query.setParameter("eid",eid);
			@SuppressWarnings("unchecked")
			List<EventAddVo> list=query.list();
			Transaction t = s.beginTransaction();
			for(EventAddVo add:list){
				s.delete(add);
			}
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		
	}

	@Override
	public void deleteFollower(int eid) {
		try{
			Session s = HibernateSessionFactory.getSession();
			String hql="from EventFollowerVo vo where vo.eid=:eid";
			Query query=s.createQuery(hql);
			query.setParameter("eid",eid);
			@SuppressWarnings("unchecked")
			List<EventFollowerVo> list=query.list();
			Transaction t = s.beginTransaction();
			for(EventFollowerVo add:list){
				s.delete(add);
			}
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		
	}

	@Override
	public void countFollower(int eid) {
		try{
			Session s = HibernateSessionFactory.getSession();
			String hql="update EventVo vo set vo.followerscount=vo.followerscount + 1 where vo.eid=:eid";
			Query query=s.createQuery(hql);
			query.setParameter("eid",eid);
			query.executeUpdate();
			Transaction t = s.beginTransaction();
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		
	}

	@Override
	public void countComment(int eid) {
		try{
			Session s = HibernateSessionFactory.getSession();
			String hql="update EventVo vo set vo.commentscount=vo.commentscount + 1 where vo.eid=:eid";
			Query query=s.createQuery(hql);
			query.setParameter("eid",eid);
			query.executeUpdate();
			Transaction t = s.beginTransaction();
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
	}

}
