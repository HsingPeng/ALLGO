package test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.njupt.allgo.service.utils.DateTimeUtil;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;
import cn.edu.njupt.allgo.service.vo.UserDataVo;

public class testDB {

	public static void main(String[] args) {
	/*	EventAddVo ui = new EventAddVo(12312321,"好的好的！！！",DateTimeUtil.currentTime(),4324324);
		Session s = HibernateSessionFactory.getSession();
		Transaction t = s.beginTransaction();
		s.save(ui);
		t.commit();
		System.out.println("恭喜,第一个Hibernate程序运行成功,记录已插入数据表db_eventadd中！");
		s.close();*/
		
		Session session=HibernateSessionFactory.getSession();
		session.clear();
		String hql="from UserDataVo as user where user.uname=:uname and user.upassword=:upassword";
		Query query=session.createQuery(hql);
		query.setString("uname", "bboxhe");
		query.setString("upassword", "123456");
		List list=query.list();
		System.out.println("login==>"+((UserDataVo)list.get(0)).toString());
	
	}
	
	
}
