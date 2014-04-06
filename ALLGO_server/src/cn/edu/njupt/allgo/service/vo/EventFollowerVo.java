package cn.edu.njupt.allgo.service.vo;

import java.io.Serializable;

public class EventFollowerVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013822673115945150L;
	private int uid ;
	private String uname ;
	private int eid;
	

	public EventFollowerVo() {
		super();
	}
	
	/**
	 * 
	 * @param uid
	 * @param uname
	 * @param eid
	 */
	public EventFollowerVo(int uid, String uname, int eid) {
		super();
		this.uid = uid;
		this.uname = uname;
		this.eid = eid;
	}



	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getUname() {
		return uname;
	}


	public void setUname(String uname) {
		this.uname = uname;
	}


	public int getEid() {
		return eid;
	}


	public void setEid(int eid) {
		this.eid = eid;
	}

	@Override
	public String toString() {
		return "EventFollowerVo [uid=" + uid + ", uname=" + uname + ", eid="
				+ eid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eid;
		result = prime * result + uid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventFollowerVo other = (EventFollowerVo) obj;
		if (eid != other.eid)
			return false;
		if (uid != other.uid)
			return false;
		return true;
	}
	
	
}
