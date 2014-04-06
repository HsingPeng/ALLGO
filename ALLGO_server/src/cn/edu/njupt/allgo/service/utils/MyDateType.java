package cn.edu.njupt.allgo.service.utils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public class MyDateType implements UserType {

	private static final int[] SQL_TYPES={Types.TIMESTAMP};
	
	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return (Serializable) arg0;
	}

	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		//如果是同一个对象
        if (arg0 == arg1)
        {
            return true;
        }
        	String oneString = (String)arg0;
            String anotherString = (String)arg1;
           if(oneString.equals(anotherString)){
        	   return true;
           }
        return false;
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		return arg0.hashCode();
	}

	@Override
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object nullSafeGet(ResultSet arg0, String[] arg1,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {
		String date = null;
		Timestamp time = arg0.getTimestamp(arg1[0]);
        if (time != null) {
        	date = DateTimeUtil.date2string(new Date(time.getTime()));
        }
        return date ;
	}

	@Override
	public void nullSafeSet(PreparedStatement statement, Object value, int index,
			SessionImplementor arg3) throws HibernateException, SQLException {
		if(value==null){
            statement.setNull(index, Types.TIMESTAMP);  
        }else if(((String)value).equals("")){
        	statement.setNull(index, Types.TIMESTAMP);
        }else{
        	Timestamp time = new Timestamp(DateTimeUtil.string2date((String) value).getTime());
            statement.setTimestamp(index, time);
        }
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public Class<Integer> returnedClass() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public int[] sqlTypes() {
		// TODO Auto-generated method stub
		return SQL_TYPES;
	}

}
