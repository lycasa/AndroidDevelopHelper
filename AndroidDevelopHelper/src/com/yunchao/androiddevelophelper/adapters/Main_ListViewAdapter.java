package com.yunchao.androiddevelophelper.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yunchao.androiddevelophelper.R;
/**
 * 
 * @author liyunchao
 * 
 *
 */
public class Main_ListViewAdapter extends BaseAdapter {
 private List<String> data = new ArrayList<String>();
 private Context context;
 private LayoutInflater mLayoutInflater;
 private viewHolder mViewHolder;
 
	public Main_ListViewAdapter(List<String> data, Context context) {
	super();
	this.data = data;
	this.context = context;
	mLayoutInflater = LayoutInflater.from(context);
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mViewHolder = new viewHolder();
		if(convertView==null){
			convertView = mLayoutInflater.inflate(R.layout.main_listviewitem, null);
			mViewHolder.tv_item = (TextView) convertView.findViewById(R.id.tv_main_listview_item);
			convertView.setTag(mViewHolder);
		}else{
			mViewHolder = (viewHolder) convertView.getTag();
		}
		
		String mtextview = (String) getItem(position);
		mViewHolder.tv_item.setText(mtextview);
		
		return convertView;
	}

	
	
	class viewHolder{
		private TextView tv_item;
	}
	
	
}
