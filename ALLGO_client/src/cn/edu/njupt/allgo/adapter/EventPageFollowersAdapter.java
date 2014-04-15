package cn.edu.njupt.allgo.adapter;

import java.util.List;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.util.ImageUtil;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class EventPageFollowersAdapter extends BaseAdapter {

	private Context context;
	private List<Integer> uids ;
	private ImageUtil imageUtil;
	
	@Override
	public int getCount() {
		return uids.size();
	}

	@Override
	public Object getItem(int position) {

		return uids.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	public EventPageFollowersAdapter(Context context ,List<Integer> uids ,ImageUtil imageUtil){
		this.context = context;
		this.uids = uids;
		this.imageUtil = imageUtil;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView imageView = null;
		if (convertView == null) {
			imageView = new ImageView(context);
		} else {
			imageView = (ImageView) convertView;
		}
		// 设置GridView的显示的格子的间距
		imageView.setLayoutParams(new GridView.LayoutParams(60, 60));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setPadding(4, 4, 4, 4);
		imageView.setImageResource(R.drawable.ic_avatar_120);
		imageUtil.displayAvatar(imageView, uids.get(position));
		return imageView;
	}

}
