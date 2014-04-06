package cn.edu.njupt.allgo.vo;

public class EventAddVo {
	
	private int eid;
	private int eaid;
	private String content;
	private String addtime;

	public EventAddVo() {
		super();
	}

	public EventAddVo(int EAID , String Content , String AddTime) {
		this.eaid = EAID ;
		this.content = Content ;
		this.addtime = AddTime ;
		this.eid = -1 ;
	}
	
	/**
	 * 活动补充信息
	 * @param EAID 该条补充的ID
	 * @param EID 活动ID
	 * @param Content 补充内容
	 * @param AddTime 补充时间
	 */
	
	public EventAddVo(int EAID , int EID , String Content , String AddTime) {
		this.eaid = EAID ;
		this.eid = EID ;
		this.content = Content ;
		this.addtime = AddTime ;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getEid() {
		return eid;
	}

	public int getEaid() {
		return eaid;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public void setEaid(int eaid) {
		this.eaid = eaid;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		EventAddVo a = (EventAddVo)o ;
		if(this.eaid == a.eaid) {
		return true;
		}
		return false ;
	}

	@Override
	public String toString() {
		return "EventAddVo [eaid=" + eaid + ", content=" + content
				+ ", addtime=" + addtime + "]";
	}
	
	
}
