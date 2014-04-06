package cn.edu.njupt.allgo.service.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.edu.njupt.allgo.service.dao.TimeLineCommonDAO;
import cn.edu.njupt.allgo.service.dao.TimeLineFollowDAO;
import cn.edu.njupt.allgo.service.dao.TimeLineFriendDAO;
import cn.edu.njupt.allgo.service.dao.TimeLineUserDAO;
import cn.edu.njupt.allgo.service.utils.DateTimeUtil;
import cn.edu.njupt.allgo.service.vo.EventAddVo;
import cn.edu.njupt.allgo.service.vo.EventFollowerVo;
import cn.edu.njupt.allgo.service.vo.EventVo;
import cn.edu.njupt.allgo.service.vo.HibernateSessionFactory;

@SuppressWarnings("unchecked")
public class TimeLineDAOimpl implements TimeLineCommonDAO, TimeLineFollowDAO,
		TimeLineFriendDAO, TimeLineUserDAO {

	//得到用户发起的活动
	@Override
	public List<EventVo> getUserEvent(int uid, int page, int pagenum) {
		List<EventVo> list=null;

		Session session=HibernateSessionFactory.getSession();
		session.clear();
		String hql="from EventVo vo where vo.uid=:uid and vo.visible=0";
		Query query=session.createQuery(hql);
		query.setParameter("uid",uid);
		query.setFirstResult((page-1)*pagenum);
		query.setMaxResults(pagenum);
		list=query.list();
		if(list ==null||list.size() == 0){
			list = null;
		}

		return list;
	}

	//得到好友的活动
	@Override
	public List<EventVo> getFriendEvent(int uid, int page, int pagenum) {
		List<EventVo> listVo = null;
		Session session=HibernateSessionFactory.getSession();
		session.clear();
		String hql="select vo.fid from UserFriendVo vo where vo.uid=:uid";
		Query query=session.createQuery(hql);
		query.setParameter("uid",uid);
		query.setFirstResult((page-1)*pagenum);
		query.setMaxResults(pagenum);
		List<Integer> list = query.list();
		for(int i=0;i<list.size();i++){
			int fid = (int) list.get(i);
			String hql2="from EventVo vo where vo.uid=:fid";
			Query query2=session.createQuery(hql2);
			query2.setParameter("fid",fid);
			if(listVo == null){
				listVo = new ArrayList<EventVo>();
			}
			listVo.addAll(query2.list());
		}
		if(listVo ==null||listVo.size() == 0){
			list = null;
		}
		return listVo;
	}

	//得到用户参与的活动
	@Override
	public List<EventVo> getFollowEvent(int uid, int page, int pagenum) {
		List<EventVo> listVo = null;
		Session session=HibernateSessionFactory.getSession();
		session.clear();
		String hql="select vo.eid from EventFollowerVo vo where vo.uid=:uid";
		Query query=session.createQuery(hql);
		query.setParameter("uid",uid);
		query.setFirstResult((page-1)*pagenum);
		query.setMaxResults(pagenum);
		List list = query.list();
		for(int i=0;i<list.size();i++){
			int eid = (int) list.get(i);
			String hql2="from EventVo vo where vo.eid=:eid";
			Query query2=session.createQuery(hql2);
			query2.setParameter("eid",eid);
			if(listVo == null){
				listVo = new ArrayList<EventVo>();
			}
			listVo.addAll(query2.list());
		}
		if(listVo ==null||listVo.size() == 0){
			list = null;
		}
		return listVo;
	}

	//得到所用公共的活动
	@Override
	public List<EventVo> getCommonEvent(int page, int pagenum, String position,
			String ecategoryname, String StartTimeRangA, String StartTimeRangB) {
		boolean isFilter = false;
		List<EventVo> list = null;
		Session session=HibernateSessionFactory.getSession();
		session.clear();
		String hql="from EventVo vo";
		if(position!=null&&!position.equals("")){
			if(!isFilter){
				hql = hql.concat(" where ");
				isFilter = true;
			}else{
				hql = hql.concat(" and ");
			}
			hql = hql.concat(" vo.position=:position ");
		}
		
		if(ecategoryname!=null&&!ecategoryname.equals("")){
			if(!isFilter){
				hql = hql.concat(" where ");
				isFilter = true;
			}else{
				hql =hql.concat(" and ");
			}
			hql = hql.concat(" vo.ecategroyname=:ecategroyname ");
		}

		if(StartTimeRangA!=null&&!StartTimeRangA.equals("")){
			if(!isFilter){
				hql =hql.concat(" where ");
				isFilter = true;
			}else{
				hql = hql.concat(" and ");
			}
			hql = hql.concat(" vo.startdate in(:StartTimeRangA,:StartTimeRangB) ");
		}

		Query query=session.createQuery(hql);
		query.setFirstResult((page-1)*pagenum);
		query.setMaxResults(pagenum);
		
		if(position!=null&&!position.equals("")){
			query.setString("position", position);
		}
		
		if(ecategoryname!=null&&!ecategoryname.equals("")){
			query.setString("ecategroyname", ecategoryname);
		}
		
		if(StartTimeRangA!=null&&!StartTimeRangA.equals("")){
			Timestamp timeA = new Timestamp(DateTimeUtil.string2date(StartTimeRangA).getTime());
			Timestamp timeB = new Timestamp(DateTimeUtil.string2date(StartTimeRangB).getTime());
			query.setDate("StartTimeRangA", timeA);
			query.setDate("StartTimeRangB", timeB);
		}
		list = query.list();
		if(list ==null||list.size() == 0){
			list = null;
		}
		
		return list;
	}

}
