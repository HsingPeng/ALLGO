package cn.edu.njupt.allgo.service.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.njupt.allgo.service.dao.UpdateUserDetailDAO;
import cn.edu.njupt.allgo.service.dao.UserDataDAO;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

public class UpdateUserDetailDAOimpl implements UpdateUserDetailDAO {

	@Override
	public UserDataVo updateUserDetail(int uid, int usex, String ubirthday,String uaddress,
			String usatement) {
		try{
			Session s = HibernateSessionFactory.getSession();
			String hql="update UserDataVo vo set vo.ubirthday=:ubirthday , vo.usex=:usex , vo.uaddress=:uaddress , vo.usatement=:usatement where vo.uid=:uid";
			Query query=s.createQuery(hql);
			query.setParameter("uid",uid);
			query.setParameter("usex",usex);
			query.setParameter("uaddress",uaddress);
			query.setParameter("usatement",usatement);
			query.setParameter("ubirthday",ubirthday);
			query.executeUpdate();
			Transaction t = s.beginTransaction();
			t.commit();
			s.close();
	   }catch(Exception e){
		   	e.printStackTrace();
	   }
		UserDataDAO dao =new LogDAOimpl();
		return dao.getUserData(uid);
	}

}
