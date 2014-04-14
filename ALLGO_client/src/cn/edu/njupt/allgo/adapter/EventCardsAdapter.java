package cn.edu.njupt.allgo.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.util.ImageUtil;
import cn.edu.njupt.allgo.vo.EventVo;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.njupt.allgo.R;

import com.haarman.listviewanimations.ArrayAdapter;
import com.lidroid.xutils.BitmapUtils;

public  class EventCardsAdapter extends ArrayAdapter<EventVo> {
	private List<EventVo> events;
	private Context Context;
	private ImageUtil imageUtils;

	public EventCardsAdapter(Context context,ArrayList<EventVo> events,ImageUtil imageUtils) {
		this.Context = context;
		this.events = events;
		this.imageUtils = imageUtils;
	}

	@Override
	public int getCount() {
		return (events==null)?0:events.size();
	}

	@Override
	public EventVo getItem(int position) {
		return events.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		EventVo event = getItem(position);
		ViewHolder viewHolder;
		View view = convertView;
		
		if (view == null) {
			view = LayoutInflater.from(Context).inflate(R.layout.list_eventlist_card, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView_username = (TextView) view.findViewById(R.id.textView_username);
			viewHolder.textView_place = (TextView) view.findViewById(R.id.textView_place);
			viewHolder.textView_outline = (TextView) view.findViewById(R.id.textView_outline);
			viewHolder.textView_time = (TextView) view.findViewById(R.id.textView_time);
			viewHolder.textView_followerscount = (TextView) view.findViewById(R.id.textView_followerscount);
			viewHolder.textView_commentscount = (TextView) view.findViewById(R.id.textView_commentscount);
			viewHolder.textView_event_position = (TextView) view.findViewById(R.id.textView_event_position);
			viewHolder.imageView_eventlist_when = (ImageView) view.findViewById(R.id.imageView_eventlist_when);
			viewHolder.imageView_eventlist_userhead = (ImageView) view.findViewById(R.id.imageView_eventlist_userhead);
			view.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) view.getTag();
			resetViewHolder(viewHolder);
		}
		//Log.i("","event.uname==>" + event.getUname());
		viewHolder.textView_username.setText(event.getUname());
		viewHolder.textView_place.setText(event.getPlace());
		viewHolder.textView_outline.setText(event.getOutline());
		viewHolder.textView_time.setText(DateUtil.smartDate(event.getStartdate(),event.getEnddate()));
		viewHolder.textView_followerscount.setText(""+event.getFollowerscount());
		viewHolder.textView_commentscount.setText("" + event.getCommentscount() );
		viewHolder.textView_event_position.setText(event.getPosition().split(" ")[0]+
				"省 "+event.getPosition().split(" ")[1]+"市 "+event.getPosition().split(" ")[2] );
		viewHolder.imageView_eventlist_when.setImageResource(setWhenImage(event.getStartdate(),event.getEnddate()));
		imageUtils.configDefaultLoadFailedImage(R.drawable.default_head_widget);
		imageUtils.displayAvatar(viewHolder.imageView_eventlist_userhead,event.getUid());
		return view;

		
	}

	/**
	 * 动态设置丝带图片
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private int setWhenImage(String startdate, String enddate) {
		int image = 1;
		switch(DateUtil.judgeDate(startdate, enddate)){
		case 1:
			image =  R.drawable.silk_riband_red_past;
			break;
		case 2:
			image =  R.drawable.silk_riband_blue_being;
			break;
		case 3:
			image =  R.drawable.silk_riband_green_goingto;
			break;
		}
		return image;
	}

	private void resetViewHolder(ViewHolder viewHolder) {
		// TODO 自动生成的方法存根
		//Log.i("Debug","==resetViewHolder==");
		viewHolder.textView_username.setText(null);
		viewHolder.textView_place.setText(null);
		viewHolder.textView_outline.setText(null);
		viewHolder.textView_time.setText(null);
		viewHolder.textView_followerscount.setText(null);
		viewHolder.textView_commentscount.setText(null);
		viewHolder.textView_event_position.setText(null);
	}


	private static class ViewHolder {
		TextView textView_username;
		TextView textView_place;
		TextView textView_outline;
		TextView textView_time;
		TextView textView_followerscount;
		TextView textView_commentscount;
		TextView textView_event_position;
		ImageView imageView_eventlist_when;
		ImageView imageView_eventlist_userhead;
	}
}