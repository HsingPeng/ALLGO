package cn.edu.njupt.allgo.service.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.njupt.allgo.service.dao.UnreadDAO;
import cn.edu.njupt.allgo.service.utils.DateTimeUtil;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;
import cn.edu.njupt.allgo.service.vo.UnreadVo;

public class UnreadDAOimpl implements UnreadDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<UnreadVo> getUnread(int uid) {
		List<UnreadVo> list=null;

		try{
			Session session=HibernateSessionFactory.getSession();
			session.clear();
			String hql="from UnreadVo vo where vo.uid=:uid and vo.isread=:isread";
			Query query=session.createQuery(hql);
			query.setParameter("uid",uid);
			query.setParameter("isread",false);
			list=query.list();
			if(list !=null){
				for(UnreadVo vo:list){
					updateUnread(vo.getRemindid());
				}
			}
		}catch(Exception e){
		   	e.printStackTrace();
	   }

		return list;
	}

	
	//标记消息为已读
	public boolean updateUnread(int remindid){
		boolean flag = false;
		try{
			Session session=HibernateSessionFactory.getSession();
			session.clear();
			String hql="update UnreadVo as vo set vo.isread=true where vo.remindid=:remindid";
			Query query=session.createQuery(hql);
			query.setParameter("remindid",remindid);
			query.executeUpdate();
			flag = true;
		  }catch(Exception e){
			   	e.printStackTrace();
		   }
		return flag;
	}

}
