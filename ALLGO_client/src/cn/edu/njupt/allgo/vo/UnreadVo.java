package cn.edu.njupt.allgo.vo;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "unread")
public class UnreadVo {


	@Id(column = "eid")
	@NoAutoIncrement
	private int remindid;
	@Column(column = "uid")
	private int uid;
	@Column(column = "rcategroy")
	private int rcategroy;
	@Column(column = "id")
	private int id;
	@Column(column = "action")
	private int action;
	@Column(column = "annotation")
	private String annotation;
	@Column(column = "time")
	private String time;
	@Column(column = "isread")
	private boolean isread;
	
	public UnreadVo() {
		super();
	}
	
	/**
	 * @param RemindID		提醒ID
	 * @param UID			用户ID
	 * @param RCategroy		提醒种类
	 * @param ID			编号
	 * @param Action		选中后的动作
	 * @param Annotation	注释
	 * @param time			时间
	 * @param isread		是否已阅读
	 */
	public UnreadVo (int RemindID , int UID ,int RCategroy , int ID ,
			int Action ,String Annotation , String time , boolean isread) {
		this.remindid = RemindID ;
		this.uid = UID ;
		this.rcategroy = RCategroy ;
		this.id = ID ;
		this.action = Action ;
		this.annotation = Annotation ;
		this.time = time ;
		this.isread = isread ;
	}
	
	
	public void setRemindid(int remindid) {
		this.remindid = remindid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setRcategroy(int rcategroy) {
		this.rcategroy = rcategroy;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAnnotation() {
		return annotation;
	}

	
	public int getRemindid() {
		return remindid;
	}


	public int getUid() {
		return uid;
	}


	public int getRcategroy() {
		return rcategroy;
	}


	public int getId() {
		return id;
	}


	public int getAction() {
		return action;
	}


	public String getTime() {
		return time;
	}

	public boolean isIsread() {
		return isread;
	}

	public void setIsread(boolean isread) {
		this.isread = isread;
	}

	@Override
	public String toString() {
		return "UnreadVo [remindid=" + remindid + ", uid=" + uid
				+ ", rcategroy=" + rcategroy + ", id=" + id + ", action="
				+ action + ", annotation=" + annotation + ", time=" + time
				+ ", isread=" + isread + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + remindid;
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
		UnreadVo other = (UnreadVo) obj;
		if (remindid != other.remindid)
			return false;
		return true;
	}

}
