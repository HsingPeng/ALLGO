package cn.edu.njupt.allgo.service.vo;

public class PrivateMessageVo {

	private int MessageID;
	private int SenderUID;
	private String SenderName;
	private int ReceiverUID;
	private String ReceiverName;
	private String Contents;
	private int Sendtime;
	
	public PrivateMessageVo() {
		super();
	}
	
	/**
	 * 
	 * @param messageID
	 * @param senderUID
	 * @param senderName
	 * @param receiverUID
	 * @param ReceiverName
	 * @param contents
	 * @param sendtime
	 */
	public PrivateMessageVo(int messageID, int senderUID, String senderName,
			int receiverUID, String receiverName, String contents, int sendtime) {
		super();
		MessageID = messageID;
		SenderUID = senderUID;
		SenderName = senderName;
		ReceiverUID = receiverUID;
		ReceiverName = receiverName;
		Contents = contents;
		Sendtime = sendtime;
	}

	public int getMessageID() {
		return MessageID;
	}

	public void setMessageID(int messageID) {
		MessageID = messageID;
	}

	public int getSenderUID() {
		return SenderUID;
	}

	public void setSenderUID(int senderUID) {
		SenderUID = senderUID;
	}

	public String getSenderName() {
		return SenderName;
	}

	public void setSenderName(String senderName) {
		SenderName = senderName;
	}

	public int getReceiverUID() {
		return ReceiverUID;
	}

	public void setReceiverUID(int receiverUID) {
		ReceiverUID = receiverUID;
	}

	public String getReceiverName() {
		return ReceiverName;
	}

	public void setReceiverName(String receiverName) {
		ReceiverName = receiverName;
	}

	public String getContents() {
		return Contents;
	}

	public void setContents(String contents) {
		Contents = contents;
	}

	public int getSendtime() {
		return Sendtime;
	}

	public void setSendtime(int sendtime) {
		Sendtime = sendtime;
	}

	@Override
	public String toString() {
		return "PrivateMessageVo [MessageID=" + MessageID + ", SenderUID="
				+ SenderUID + ", SenderName=" + SenderName + ", ReceiverUID="
				+ ReceiverUID + ", ReceiverName=" + ReceiverName + ", Contents="
				+ Contents + ", Sendtime=" + Sendtime + "]";
	}
	
	
	
}
