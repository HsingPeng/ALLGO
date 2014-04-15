package cn.edu.njupt.allgo.vo;

import java.io.Serializable;

public class UserDataVo implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 623937101202033311L;
	private int uid;
	private String uname;
	private String upassword;
	private String uemail;
	private int usex;
	private String uhead;
	private String ubirthday;
	private String uregdate;
	private String uaddress;
	private String usatement;

	
	/**
	 * 
	 * @param uid			用户ID
	 * @param uname			用户昵称
	 * @param upassword		密码
	 * @param uemail		电子邮件
	 * @param usex			性别
	 * @param uhead			用户头像
	 * @param ubirthday		生日
	 * @param uregdate		注册日期
	 * @param address		地址
	 * @param usatement		用户备注
	 */
	public UserDataVo (int uid , String uname , String upassword , String uemail ,
			int usex , String uhead , String ubirthday , String uregdate ,
			String uaddress ,String usatement) { 
		
		this.uid = uid ;
		this.uname = uname ;
		this.upassword = upassword ;
		this.uemail = uemail ;
		this.usex = usex ;
		this.uhead = uhead ;
		this.ubirthday = ubirthday ;
		this.uregdate = uregdate ;
		this.uaddress = uaddress ;
		this.usatement = usatement ;
		
	}
	
	
	public void setUid(int uid) {
		this.uid = uid;
	}


	public void setUregdate(String uregdate) {
		this.uregdate = uregdate;
	}


	public UserDataVo() {
		super();
	}


	public String getUname() {
		return uname;
	}


	public void setUname(String uname) {
		this.uname = uname;
	}


	public String getUpassword() {
		return upassword;
	}


	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}


	public String getUemail() {
		return uemail;
	}


	public void setUemail(String uemail) {
		this.uemail = uemail;
	}


	public int getUsex() {
		return usex;
	}


	public void setUsex(int usex) {
		this.usex = usex;
	}


	public String getUhead() {
		return uhead;
	}


	public void setUhead(String uhead) {
		this.uhead = uhead;
	}


	public String getUbirthday() {
		return ubirthday;
	}


	public void setUbirthday(String ubirthday) {
		this.ubirthday = ubirthday;
	}


	public String getUaddress() {
		return uaddress;
	}


	public void setUaddress(String uaddress) {
		this.uaddress = uaddress;
	}


	public String getUsatement() {
		return usatement;
	}


	public void setUsatement(String usatement) {
		this.usatement = usatement;
	}


	public int getUid() {
		return uid;
	}


	public String getUregdate() {
		return uregdate;
	}


	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		UserDataVo a = (UserDataVo)o ;
		if(this.uid == a.uid) {
		return true;
		}
		return false ;
	}


	@Override
	public String toString() {
		return "UserDataVo [uid=" + uid + ", uname=" + uname + ", upassword="
				+ upassword + ", uemail=" + uemail + ", usex=" + usex
				+ ", uhead=" + uhead + ", ubirthday=" + ubirthday
				+ ", uregdate=" + uregdate + ", uaddress=" + uaddress
				+ ", usatement=" + usatement + "]";
	}
	
	
}
