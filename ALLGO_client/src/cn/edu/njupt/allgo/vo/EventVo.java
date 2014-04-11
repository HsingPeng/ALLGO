package cn.edu.njupt.allgo.vo;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;



public class EventVo implements Serializable {
  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7547669296651794801L;
	
	@Id(column = "eid")
	@NoAutoIncrement
	protected int eid; // eid 活动id  int
	@Column(column = "outline")
	protected String outline ;
	@Column(column = "uid")
	protected int uid ;
	@Column(column = "uname")
	protected String uname ;
	@Column(column = "startdate")
	protected String startdate ;
	@Column(column = "enddate")
	protected String enddate ;
	@Column(column = "content")
	protected String content ;
	@Column(column = "place")
	protected String place ;
	@Column(column = "position")
	protected String position ;
	@Column(column = "dateline")
	protected String dateline ;
	@Column(column = "ecategroyname")
	protected String ecategroyname ;
	@Column(column = "visible")
	protected int visible ;
	@Column(column = "commentscount")
	protected int commentscount ;
	@Column(column = "followerscount")
	protected int followerscount ;
    
   // public int photo;  
    /**
     * 形参（活动id，活动概要，创建者id，创建者昵称，活动开始时间，活动结束时间，活动内容，活动地点，创建时间，活动种类，可见性，评论数，跟随人数）
     * @param eid 		 活动id
     * @param outline 		 活动概要
     * @param uid 			 创建者id
     * @param uname 		 创建者昵称
     * @param startdate 	 活动开始时间
     * @param enddate 	 	 活动结束时间
     * @param content 		 活动内容
     * @param place 		 活动地点
     * @param position 		 位置定位
     * @param dateline 		 创建时间
     * @param ecategroyname  活动种类
     * @param visible 	 	 可见性
     * @param commentscount  评论数
     * @param followerscount 跟随人数
     */

	public EventVo(int eid, String outline, int uid, String uname,
			String startdate, String enddate, String content, String place,
			String position , String dateline, String ecategroyname, int visible,
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
		this.position = position;
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


	/**
	 * 测试方法
	 * @param test
	 */
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
