package cn.edu.njupt.allgo.vo;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "myevent")
public class MyEventVo extends EventVo {
	
	private static final long serialVersionUID = 5079966250145898628L;

	

	public MyEventVo(int eid, String outline, int uid, String uname,
			String startdate, String enddate, String content, String place,
			String position, String dateline, String ecategroyname,
			int visible, int commentscount, int followerscount) {
		super(eid, outline, uid, uname, startdate, enddate, content, place, position,
				dateline, ecategroyname, visible, commentscount, followerscount);
		// TODO 自动生成的构造函数存根
	}



	public MyEventVo(){
		super();
	}

}
