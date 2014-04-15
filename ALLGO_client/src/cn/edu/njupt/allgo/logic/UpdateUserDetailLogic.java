package cn.edu.njupt.allgo.logic;

import android.graphics.Bitmap;
import cn.edu.njupt.allgo.vo.UserDataVo;

public interface UpdateUserDetailLogic {

	/**
	 * 提交用户信息
	 * @param vo
	 */
	public void submitDetail(UserDataVo vo);
	
	/**
	 * 提交头像
	 * @param avatar
	 */
	public void updateAvatar(Bitmap avatar);
	
}
