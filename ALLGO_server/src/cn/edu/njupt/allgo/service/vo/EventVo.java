package cn.edu.njupt.allgo.service.vo;

import java.io.Serializable;




public class EventVo implements Serializable {
  
  
	private static final long serialVersionUID = 8831880940182205919L;

	protected int eid; // eid 活动id  int

	protected String outline ;

	protected int uid ;

	protected String uname ;

	protected String startdate ;

	protected String enddate ;

	protected String content ;

	protected String place ;
	
	protected String position ;

	protected String dateline ;

	protected String ecategroyname ;

	protected int visible ;

	protected int commentscount ;

	protected int followerscount ;
    
   // public int photo;  
   
	/**
	 * 形参（活动id，活动概要，创建者id，创建者昵称，活动开始时间，活动结束时间，活动内容，活动地点，创建时间，活动种类，可见性，评论数，跟随人数）
	 * @param eid
	 * @param outline
	 * @param uid
	 * @param uname
	 * @param startdate
	 * @param enddate
	 * @param content
	 * @param place
	 * @param position
	 * @param dateline
	 * @param ecategroyname
	 * @param visible
	 * @param commentscount
	 * @param followerscount
	 */
	public EventVo(int eid, String outline, int uid, String uname,
			String startdate, String enddate, String content, String place,
			String position ,String dateline, String ecategroyname, int visible,
			int commentscount, int followerscount) {
		super();
		this.eid = eid;
		this.outline = outline;
		this.uid = uid;
		this.uname = uname;
		this.startdate = startdate;
		this.enddate = enddate;
		this.content = content;
		this.place = place;
		this.position = position ;
		this.dateline = dateline;
		this.ecategroyname = ecategroyname;
		this.visible = visible;
		this.commentscount = commentscount;
		this.followerscount = followerscount;
	}
    
	
	public EventVo() {
		super();
	}


	public void setEid(int eid) {
		this.eid = eid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public void setDateline(String dateline) {
		this.dateline = dateline;
	}


	public String getOutline() {
		return outline;
	}


	public void setOutline(String outline) {
		this.outline = outline;
	}


	public String getUname() {
		return uname;
	}


	public void setUname(String uname) {
		this.uname = uname;
	}


	public String getStartdate() {
		return startdate;
	}


	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}


	public String getEnddate() {
		return enddate;
	}


	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public String getEcategroyname() {
		return ecategroyname;
	}


	public void setEcategroyname(String ecategroyname) {
		this.ecategroyname = ecategroyname;
	}


	public int getVisible() {
		return visible;
	}


	public void setVisible(int visible) {
		this.visible = visible;
	}


	public int getCommentscount() {
		return commentscount;
	}


	public void setCommentscount(int commentscount) {
		this.commentscount = commentscount;
	}


	public int getFollowerscount() {
		return followerscount;
	}


	public void setFollowerscount(int followerscount) {
		this.followerscount = followerscount;
	}


	public int getEid() {
		return eid;
	}


	public int getUid() {
		return uid;
	}


	public String getDateline() {
		return dateline;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	//测试方法
    public EventVo(int test) {
    	this(11003617 + test,"去栖霞山爬山",123456,test + "千军万马",
    			"Mon Feb 15 08:00:00 GMT+08:00 2014",null,"去栖霞山爬山","栖霞山",
    			"江苏省 南京市 栖霞区","Mon Feb 13 08:00:00 GMT+08:00 2013","旅游",0,
    			0,0);

    }  


	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		EventVo a = (EventVo)o ;
		if(this.eid == a.eid) {
		return true;
		}
		return false ;
	}


	@Override
	public String toString() {
		return "EventVo [eid=" + eid + ", outline=" + outline + ", uid=" + uid
				+ ", uname=" + uname + ", startdate=" + startdate
				+ ", enddate=" + enddate + ", content=" + content + ", place="
				+ place + ", position=" + position + ", dateline=" + dateline
				+ ", ecategroyname=" + ecategroyname + ", visible=" + visible
				+ ", commentscount=" + commentscount + ", followerscount="
				+ followerscount + "]";
	}


}
