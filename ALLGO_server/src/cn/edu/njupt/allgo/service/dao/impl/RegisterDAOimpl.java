package cn.edu.njupt.allgo.service.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.njupt.allgo.service.dao.RegisterDAO;
import cn.edu.njupt.allgo.service.vo.EventFollowerVo;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

public class RegisterDAOimpl implements RegisterDAO{

	@Override
	public UserDataVo register(String uname, int usex, String uemail,
			String upassword ,String uregdate) {
		
		UserDataVo user = null;
		try{
			Session s = HibernateSessionFactory.getSession();
			Transaction t = s.beginTransaction();
			user = new UserDataVo();
			user.setUname(uname);
			user.setUsex(usex);
			user.setUemail(uemail);
			user.setUpassword(upassword);
			user.setUregdate(uregdate);
			s.save(user);
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		return user;
	}

	@Override
	public String isExist(String uname,String uemail) {
		String flag = null;
		
		Session s = HibernateSessionFactory.getSession();
		Transaction t = s.beginTransaction();
		String hql="from UserDataVo as user where user.uemail=:uemail";
		Query query=s.createQuery(hql);
		query.setString("uemail", uemail);
		List list=query.list();
		t.commit();
		
		if(list.size() > 0){
			flag = "邮箱已存在" ;
		}
		
		Transaction t2 = s.beginTransaction();
		String hql2="from UserDataVo as user where user.uname=:uname";
		Query query2=s.createQuery(hql2);
		query2.setString("uname", uname);
		List list2=query2.list();
		t2.commit();
		
		if(list2.size() > 0){
			flag = "用户名已存在" ;
		}
		
		s.close();
		return flag;
	}

}
