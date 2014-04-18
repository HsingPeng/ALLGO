package cn.edu.njupt.allgo.vo;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "userfriend")
public class UserFriendVo {
	
	@Column(column = "uid")
	private int uid;
	@Id(column = "fid")
	@NoAutoIncrement
	private int fid;
	@Column(column = "fname")
	private String fname;
	@Column(column = "categroyname")
	private String categroyname;
	
	
	
	public UserFriendVo() {
		super();
	}

	/**
	 * 
	 * @param uid
	 * @param fid
	 * @param fname
	 * @param categroyname
	 */
	public UserFriendVo(int uid, int fid, String fname, String categroyname) {
		super();
		this.uid = uid;
		this.fid = fid;
		this.fname = fname;
		this.categroyname = categroyname;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getCategroyname() {
		return categroyname;
	}

	public void setCategroyname(String categroyname) {
		this.categroyname = categroyname;
	}

	@Override
	public String toString() {
		return "UserFriendVo [uid=" + uid + ", fid=" + fid + ", fname=" + fname
				+ ", categroyname=" + categroyname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fid;
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
		UserFriendVo other = (UserFriendVo) obj;
		if (fid != other.fid)
			return false;
		if (uid != other.uid)
			return false;
		return true;
	}
	
	
	
}
