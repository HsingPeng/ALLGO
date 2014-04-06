package cn.edu.njupt.allgo.vo;

public class EventFollowerVo {

	private int FollowerID ;
	private int uid;
	private String uname ;
	private int eid ;
	private String uhead ;
	
	public EventFollowerVo() {
		super();
	}

	public EventFollowerVo(int followerID, int uid, String uname, int eid,
			String uhead) {
		super();
		FollowerID = followerID;
		this.uid = uid;
		this.uname = uname;
		this.eid = eid;
		this.uhead = uhead;
	}

	public int getFollowerID() {
		return FollowerID;
	}

	public void setFollowerID(int followerID) {
		FollowerID = followerID;
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

	public String getUhead() {
		return uhead;
	}

	public void setUhead(String uhead) {
		this.uhead = uhead;
	}

	@Override
	public String toString() {
		return "EventFollowerVo [FollowerID=" + FollowerID + ", uid=" + uid
				+ ", uname=" + uname + ", eid=" + eid + ", uhead=" + uhead
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + FollowerID;
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
		if (FollowerID != other.FollowerID)
			return false;
		return true;
	}
	
}
