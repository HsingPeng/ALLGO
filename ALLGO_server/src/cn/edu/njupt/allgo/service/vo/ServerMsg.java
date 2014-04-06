package cn.edu.njupt.allgo.service.vo;

import java.util.List;

public class ServerMsg {

	private int type ;
	private Object action ;
	private List<Integer> user;
	
	public ServerMsg() {
		super();
	}
	
	/**
	 * 服务器消息通信类
	 * @param type		消息种类
	 * @param action	内容
	 * @param user		部分待通知用户(暂时不用)
	 */
	public ServerMsg(int type, Object action, List<Integer> user) {
		super();
		this.type = type;
		this.action = action;
		this.user = user;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getAction() {
		return action;
	}

	public void setAction(Object action) {
		this.action = action;
	}

	public List<Integer> getUser() {
		return user;
	}

	public void setUser(List<Integer> user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ServerMsg [type=" + type + ", action=" + action + ", user="
				+ user + "]";
	}

}
