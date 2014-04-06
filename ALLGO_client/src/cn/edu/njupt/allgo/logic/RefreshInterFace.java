package cn.edu.njupt.allgo.logic;

import java.util.ArrayList;

import cn.edu.njupt.allgo.vo.EventVo;

public interface RefreshInterFace {
	
	/**
	 * 用于各个地方刷新
	 * @param result
	 * @param kind
	 */
	public void refresh(Object result , int kind) ;
}
