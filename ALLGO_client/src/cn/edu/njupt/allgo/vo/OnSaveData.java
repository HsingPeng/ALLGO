package cn.edu.njupt.allgo.vo;

import java.io.Serializable;

import cn.edu.njupt.allgo.fragment.BaseFRAGMENT;

public class OnSaveData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7341342340736407436L;
	public BaseFRAGMENT currentFragment = null;
	
	public OnSaveData(BaseFRAGMENT currentFragment){
		this.currentFragment = currentFragment ;
	}
	
	public BaseFRAGMENT getFragment() {
		return currentFragment ;
	}
	
}
