package cn.edu.njupt.allgo.service.vo;

public class EventAddVo {
	
	private int eaid;
	private String content;
	private String addtime;
	private int eid;

	

	/**
	 * 
	 * @param eaid
	 * @param content
	 * @param addtime
	 * @param eid
	 */
	public EventAddVo(int eaid, String content, String addtime, int eid) {
		super();
		this.eaid = eaid;
		this.content = content;
		this.addtime = addtime;
		this.eid = eid;
	}


	public EventAddVo() {
		super();
	}


	public int getEaid() {
		return eaid;
	}


	public void setEaid(int eaid) {
		this.eaid = eaid;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getAddtime() {
		return addtime;
	}


	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public int getEid() {
		return eid;
	}


	public void setEid(int eid) {
		this.eid = eid;
	}


	@Override
	public String toString() {
		return "EventAddVo [eaid=" + eaid + ", content=" + content
				+ ", addtime=" + addtime + ", eid=" + eid + "]";
	}





	
}
