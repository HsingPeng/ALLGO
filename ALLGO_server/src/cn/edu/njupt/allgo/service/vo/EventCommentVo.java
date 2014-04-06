package cn.edu.njupt.allgo.service.vo;

public class EventCommentVo {

	
	private int commentsid;
	private int uid;
	private String uname;
	private int eid;
	private String sendtime;
	private int replyuid;
	private String replyuname;
	private String texts;
	
	public EventCommentVo() {
		super();
	}

	/**
	 * 
	 * @param commentsid
	 * @param uid
	 * @param uname
	 * @param eid
	 * @param sendtime
	 * @param replyuid
	 * @param replyuname
	 * @param texts
	 */
	public EventCommentVo(int commentsid, int uid, String uname, int eid,
			String sendtime, int replyuid, String replyuname, String texts) {
		super();
		this.commentsid = commentsid;
		this.uid = uid;
		this.uname = uname;
		this.eid = eid;
		this.sendtime = sendtime;
		this.replyuid = replyuid;
		this.replyuname = replyuname;
		this.texts = texts;
	}

	public int getCommentsid() {
		return commentsid;
	}

	public void setCommentsid(int commentsid) {
		this.commentsid = commentsid;
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

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public int getReplyuid() {
		return replyuid;
	}

	public void setReplyuid(int replyuid) {
		this.replyuid = replyuid;
	}

	public String getReplyuname() {
		return replyuname;
	}

	public void setReplyuname(String replyuname) {
		this.replyuname = replyuname;
	}

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	@Override
	public String toString() {
		return "EventCommentVo [commentsid=" + commentsid + ", uid=" + uid
				+ ", uname=" + uname + ", eid=" + eid + ", sendtime="
				+ sendtime + ", replyuid=" + replyuid + ", replyuname="
				+ replyuname + ", texts=" + texts + "]";
	}
	
}
