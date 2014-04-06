package cn.edu.njupt.allgo.service.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.njupt.allgo.service.dao.LoginDAO;
import cn.edu.njupt.allgo.service.dao.UserDataDAO;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

public class LogDAOimpl implements LoginDAO , UserDataDAO {

	@SuppressWarnings("unchecked")
	@Override
	public UserDataVo login(String username, String password) {
		List<UserDataVo> list=null;
		UserDataVo vo = null;
		try{
			Session session=HibernateSessionFactory.getSession();
			session.clear();
			String hql="from UserDataVo as user where user.uname=:uname and user.upassword=:upassword";
			Query query=session.createQuery(hql);
			query.setString("uname", username);
			query.setString("upassword", password);
			list=query.list();
			if(list.size() == 1){
				vo = list.get(0);
				vo.setUpassword(null);
			}
		}catch(Exception e){
		   	e.printStackTrace();
	   }
		return vo;
	}

	//得到用户信息
	@Override
	public UserDataVo getUserData(int uid) {
		UserDataVo vo = null;
		try{
			Session s = HibernateSessionFactory.getSession();
			Transaction t = s.beginTransaction();
			vo = (UserDataVo) s.load(UserDataVo.class, uid);
			t.commit();
			s.close();
			vo.setUpassword(null);
		}catch(Exception e){
		   	e.printStackTrace();
	   }
		return vo;
	}

}
