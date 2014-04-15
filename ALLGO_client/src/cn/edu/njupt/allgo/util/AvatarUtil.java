package cn.edu.njupt.allgo.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.edu.njupt.allgo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

/**
 * 1.构造时将Activity和imagebutton传入
 * 2.需要重写Activiy的onActivityResult方法,里面写入本类的onActivityResult方法
 * 3.调用getAvatar获取图片
 * 4.结束时调用destroy删除临时文件
 * @author 深蓝
 *
 */
public class AvatarUtil {

	private ImageButton img_btn;
	private Activity activity;
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	// 创建一个以当前时间为名称的文件
	
	private File tempFile = new File(Environment.getExternalStorageDirectory(),getPhotoFileName());
	private Bitmap photo;

	/**
	 * 照相或相册获取头像
	 * 1.构造时将Activity和imagebutton传入
	 * 2.需要重写Activiy的onActivityResult方法,里面写入本类的onActivityResult方法
	 * 3.调用getAvatar获取图片
	 * 4.最好在结束时调用destroy()方法
	 * @author 深蓝
	 *
	 */
	public AvatarUtil(ImageButton img_btn,Activity activity){
		this.img_btn = img_btn;
		this.activity=activity;
		tempFile.deleteOnExit();			//退出时自动删除临时文件,经测试可能不起作用，原因不明
		img_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
	}

	//提示对话框方法
		private void showDialog() {
			new AlertDialog.Builder(activity)
			.setTitle("头像设置")
			.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					// 调用系统的拍照功能
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// 指定调用相机拍照后照片的储存路径
					intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempFile));
					activity.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
					}
				})
			.setNeutralButton("相册", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
				activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
				}
			})
			.setNegativeButton("清除", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					img_btn.setImageResource(R.drawable.ic_avatar_register);
					photo = null;
					}
			})
			.show();
		}
	
		/**
		 * 将此类放入Activtiy的onActivityResult方法里面
		 * @param requestCode
		 * @param resultCode
		 * @param data
		 */
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub


			switch (requestCode) {
			case PHOTO_REQUEST_TAKEPHOTO://当选择拍照时调用
			startPhotoZoom(Uri.fromFile(tempFile), 150);
			break;


			case PHOTO_REQUEST_GALLERY://当选择从本地获取图片时
			//做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
			if (data != null)
			startPhotoZoom(data.getData(), 150);
			break;


			case PHOTO_REQUEST_CUT://返回的结果
			if (data != null) 
			setPicToView(data);
			break;
			}

		}
		
		/**
		 * 得到Bitmap图像
		 * @return	没有则返回null
		 */
		public Bitmap getAvatar(){
			return photo;
		}
		
		/**
		 * 得到图像文件
		 * @return
		 */
		/*public File getFile(){
				if(photo == null){
					return null;
				}else{
					return tempFile;
				}
			}*/

		/**
		 * 删除临时文件
		 */
		public void destroy(){
			tempFile.delete();
		}
		
		private void startPhotoZoom(Uri uri, int size) {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			// crop为true是设置在开启的intent中设置显示的view可以剪裁
			intent.putExtra("crop", "true");


			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);


			// outputX,outputY 是剪裁图片的宽高
			intent.putExtra("outputX", size);
			intent.putExtra("outputY", size);
			intent.putExtra("return-data", true);


			activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
		}
		
		//将进行剪裁后的图片显示到UI界面上
		private void setPicToView(Intent picdata) {
			Bundle bundle = picdata.getExtras();
			if (bundle != null) {
			photo = bundle.getParcelable("data");
			//saveFile();
			img_btn.setImageBitmap(photo);
			}
		}
		
		//废弃
		private void saveFile(){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			photo.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
			byte[] bitmapdata = bos.toByteArray();

			//write the bytes in file
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(tempFile);
				fos.write(bitmapdata);
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		// 使用系统当前日期加以调整作为照片的名称
		private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss",Locale.CHINA);
		return dateFormat.format(date)+"";
		}
	
}
