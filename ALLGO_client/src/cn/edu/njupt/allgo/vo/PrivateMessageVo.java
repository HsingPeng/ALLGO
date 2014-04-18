package cn.edu.njupt.allgo.vo;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "privatemessage")
public class PrivateMessageVo {
	@Id(column = "messageid")
	@NoAutoIncrement
	private int messageid;
	@Column(column = "senderuid")
	private int senderuid;
	@Column(column = "sendername")
	private String sendername;
	@Column(column = "receiveruid")
	private int receiveruid;
	@Column(column = "receivename")
	private String receivename;
	@Column(column = "contents")
	private String contents;
	@Column(column = "sendtime")
	private String sendtime;
	
	public PrivateMessageVo() {
		super();
	}
	
	/**
	 * 
	 * @param messageid
	 * @param senderuid
	 * @param sendername
	 * @param receiveruid
	 * @param receivename
	 * @param contents
	 * @param sendtime
	 */
	public PrivateMessageVo(int messageid, int senderuid, String sendername,
			int receiveruid, String receivename, String contents,
			String sendtime) {
		super();
		this.messageid = messageid;
		this.senderuid = senderuid;
		this.sendername = sendername;
		this.receiveruid = receiveruid;
		this.receivename = receivename;
		this.contents = contents;
		this.sendtime = sendtime;
	}

	public int getMessageid() {
		return messageid;
	}

	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}

	public int getSenderuid() {
		return senderuid;
	}

	public void setSenderuid(int senderuid) {
		this.senderuid = senderuid;
	}

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public int getReceiveruid() {
		return receiveruid;
	}

	public void setReceiveruid(int receiveruid) {
		this.receiveruid = receiveruid;
	}

	public String getReceivename() {
		return receivename;
	}

	public void setReceivename(String receivename) {
		this.receivename = receivename;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	@Override
	public String toString() {
		return "PrivateMessageVo [messageid=" + messageid + ", senderuid="
				+ senderuid + ", sendername=" + sendername + ", receiveruid="
				+ receiveruid + ", receivename=" + receivename + ", contents="
				+ contents + ", sendtime=" + sendtime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + messageid;
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
		PrivateMessageVo other = (PrivateMessageVo) obj;
		if (messageid != other.messageid)
			return false;
		return true;
	}

}
