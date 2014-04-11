package cn.edu.njupt.allgo.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.vo.UnreadVo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.edu.njupt.allgo.R;

import com.haarman.listviewanimations.ArrayAdapter;

public  class UnreadCardsAdapter extends ArrayAdapter<UnreadVo> {
	private List<UnreadVo> unreads;
	private Context Context;

	public UnreadCardsAdapter(Context context,ArrayList<UnreadVo> unreads) {
		this.Context = context;
		this.unreads = unreads;
	}

	@Override
	public int getCount() {
		return (unreads==null)?0:unreads.size();
	}

	@Override
	public UnreadVo getItem(int position) {
		return unreads.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		UnreadVo unread = getItem(position);
		ViewHolder viewHolder;
		View view = convertView;
		
		if (view == null) {
			view = LayoutInflater.from(Context).inflate(R.layout.list_unreadlist_card, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView_unread_RCategroy = (TextView) view.findViewById(R.id.textView_unread_RCategroy);
			viewHolder.textView_unread_Annotation = (TextView) view.findViewById(R.id.textView_unread_Annotation);
			viewHolder.textView_unread_time = (TextView) view.findViewById(R.id.textView_unread_time);
			view.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) view.getTag();
			resetViewHolder(viewHolder);
		}
		viewHolder.textView_unread_RCategroy.setText(setRCategroy(unread.getRcategroy()));
		viewHolder.textView_unread_Annotation.setText(unread.getAnnotation());
		viewHolder.textView_unread_time.setText(DateUtil.showDate(unread.getTime()));
		
		return view;
		
		
	}

	private String setRCategroy (int arg0) {
		switch(arg0) {
		case 00 :
			return  "加入的活动有更新" ;
		case 01:
			return "有人回复我的评论" ;
		case 02:
			return "我的活动有新评论" ;
		case 03:
			return "我的活动有人加入" ;
		case 04:
			return "加入的活动被删除" ;
		case 05:
			return "有人请求加为好友" ;
		case 06:
			return "有人发新消息给你" ;

		default : return "" ;	
		}
	}
	
	
	private void resetViewHolder(ViewHolder viewHolder) {
		// TODO 自动生成的方法存根
		viewHolder.textView_unread_RCategroy.setText(null);
		viewHolder.textView_unread_Annotation.setText(null);
		viewHolder.textView_unread_time.setText(null);
	}


	private static class ViewHolder {
		public TextView textView_unread_time;
		public TextView textView_unread_Annotation;
		public TextView textView_unread_RCategroy;

	}
}