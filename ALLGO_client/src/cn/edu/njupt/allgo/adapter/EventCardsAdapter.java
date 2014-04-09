package cn.edu.njupt.allgo.adapter;

import java.util.ArrayList;
import java.util.List;
import cn.edu.njupt.allgo.util.ChangeDateUtil;

import cn.edu.njupt.allgo.vo.EventVo;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.edu.njupt.allgo.R;

import com.haarman.listviewanimations.ArrayAdapter;

public  class EventCardsAdapter extends ArrayAdapter<EventVo> {
	private List<EventVo> events;
	private Context Context;

	public EventCardsAdapter(Context context,ArrayList<EventVo> events) {
		this.Context = context;
		this.events = events;
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
			view = LayoutInflater.from(Context).inflate(R.layout.list_commonlist_card, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView_username = (TextView) view.findViewById(R.id.textView_username);
			viewHolder.textView_place = (TextView) view.findViewById(R.id.textView_place);
			viewHolder.textView_outline = (TextView) view.findViewById(R.id.textView_outline);
			viewHolder.textView_time = (TextView) view.findViewById(R.id.textView_time);
			viewHolder.textView_followerscount = (TextView) view.findViewById(R.id.textView_followerscount);
			viewHolder.textView_commentscount = (TextView) view.findViewById(R.id.textView_commentscount);
			view.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) view.getTag();
			resetViewHolder(viewHolder);
		}
		Log.i("","event.uname==>" + event.getUname());
		viewHolder.textView_username.setText(event.getUname());
		viewHolder.textView_place.setText(event.getPlace());
		viewHolder.textView_outline.setText(event.getOutline());
		viewHolder.textView_time.setText(ChangeDateUtil.showDate(event.getStartdate()));
		viewHolder.textView_followerscount.setText(""+event.getFollowerscount());
		viewHolder.textView_commentscount.setText("" + event.getCommentscount() );
		
		return view;
		
		
	}


	private void resetViewHolder(ViewHolder viewHolder) {
		// TODO 自动生成的方法存根
		Log.i("Debug","==resetViewHolder==");
		viewHolder.textView_username.setText(null);
		viewHolder.textView_place.setText(null);
		viewHolder.textView_outline.setText(null);
		viewHolder.textView_time.setText(null);
		viewHolder.textView_followerscount.setText(null);
		viewHolder.textView_commentscount.setText(null);
	}


	private static class ViewHolder {
		TextView textView_username;
		TextView textView_place;
		TextView textView_outline;
		TextView textView_time;
		TextView textView_followerscount;
		TextView textView_commentscount;
	}
}