package com.yunchao.androiddevelophelper.demos.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RSSFeed {
	private String title;//标题
	private String pubdate;//发布日期
	private int itemcount;//用于计算列表的数目
	private List<RSSItem> itemlist;//用于描述列表

	public RSSFeed() {
		//加入对象的创建
		itemlist = new ArrayList<RSSItem>();
	}

	public int addItem(RSSItem item) {
		itemlist.add(item);
		itemcount++;
		return itemcount;
	}
	//根据下标获取RssItem
	public RSSItem getItem(int location) {
		return itemlist.get(location);
	}
	//为ListView 设置HashMap<String,Object>
	public List<HashMap<String, Object>> getAllItemsForListView() {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		int size = itemlist.size();
		for (int i = 0; i < size; i++) {
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put(RSSItem.TITLE, itemlist.get(i).getTitle());
			item.put(RSSItem.PUBDATE, itemlist.get(i).getPubdate());
			data.add(item);
		}
		return data;
	}

	public int getItemCount() {
		return itemlist.size();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPubDate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getTitle() {
		return title;
	}

	public String getPubDate() {
		return pubdate;
	}
	public List<RSSItem> getRssItems() {
		return itemlist;
	}

}

