package cn.edu.njupt.allgo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.application.MyDeclare;

import com.lidroid.xutils.BitmapUtils;

public class ImageUtil extends BitmapUtils{

	private Context context ;
	private MyDeclare declare;
	
	public ImageUtil(Context context) {
		super(context);
		this.context = context;
		declare =(MyDeclare)context.getApplicationContext();
		this.configDiskCacheEnabled(true);
		this.configDefaultCacheExpiry(1000L * 60 * 60 * 24 * 3); //缓存时间3天
		this.configDefaultLoadFailedImage(R.drawable.ov_photo_error_48);
	}


	/**
	 * 通过UID显示头像
	 * @param container
	 * @param uid
	 */
	public <T extends View> void displayAvatar(T container,int uid){
		display(container, getAvatarURL(uid));
	}

	public String getAvatarURL(int uid) {
		String result = declare.getHost_url() + "photo/avatar/"+uid+".jpg";
		return result;
	}

	/**
	 * 清除某个头像缓存
	 * @param uid
	 */
	public void clearCacheAvatar(int uid){
		this.clearCache(getAvatarURL(uid));
	}
	
	// 将Bitmap转换成InputStream  
    public static InputStream Bitmap2InputStream(Bitmap bm) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    }
	
}
