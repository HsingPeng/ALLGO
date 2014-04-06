package cn.edu.njupt.allgo.service.dao;

import java.util.List;

import cn.edu.njupt.allgo.service.vo.EventVo;

public interface TimeLineCommonDAO {

	/**
	 * 获得所有活动
	 * @param page
	 * @param pagenum
	 * @param place
	 * @param ecategoryname
	 * @param StartTimeRangA
	 * @param StartTimeRangB
	 * @return
	 */
	public List<EventVo> getCommonEvent(int page, int pagenum,
			String place, String ecategoryname, String StartTimeRangA,
			String StartTimeRangB);
}
