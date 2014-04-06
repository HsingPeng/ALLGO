package cn.edu.njupt.allgo.vo;

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
	 * 活动回复
	 * @param CommentsID	评论ID
	 * @param UID	用户ID
	 * @param UName	用户昵称
	 * @param EID	活动ID
	 * @param SendTime	发送时间
	 * @param ReplyUID	所回复用户ID
	 * @param ReplyUName	所回复用户的昵称
	 * @param texts	内容
	 */
	public EventCommentVo (int CommentsID , int UID ,String UName ,int EID ,
			String SendTime , int ReplyUID , String ReplyUName , String texts) {
		this.commentsid = CommentsID ;
		this.uid = UID ;
		this.uname = UName ;
		this.eid = EID ;
		this.sendtime = SendTime ;
		this.replyuid = ReplyUID ;
		this.replyuname = ReplyUName ;
		this.texts = texts ;
	}
	
	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
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

	public int getCommentsid() {
		return commentsid;
	}

	public int getUid() {
		return uid;
	}

	public int getEid() {
		return eid;
	}

	public String getSendtime() {
		return sendtime;
	}

	public int getReplyuid() {
		return replyuid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public void setReplyuid(int replyuid) {
		this.replyuid = replyuid;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		EventCommentVo a = (EventCommentVo)o ;
		if(this.commentsid == a.commentsid) {
		return true;
		}
		return false ;
	}

	@Override
	public String toString() {
		return "EventCommentVo [commentsid=" + commentsid + ", uid=" + uid
				+ ", uname=" + uname + ", eid=" + eid + ", sendtime="
				+ sendtime + ", replyuid=" + replyuid + ", replyuname="
				+ replyuname + ", texts=" + texts + "]";
	}
	
	
	
}
