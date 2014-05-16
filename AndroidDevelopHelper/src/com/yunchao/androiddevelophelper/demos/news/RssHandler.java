package com.yunchao.androiddevelophelper.demos.news;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class RssHandler extends DefaultHandler {
	RSSFeed RSSFeed;// 用于保存解析过程中的channel
	RSSItem RSSItem;// 用于保存解析过程中的item
	String lastElementName = "";// 标记变量，用于标记在解析过程中我们关心的几个标签，若不是我们关心的标签记做0
	final int RSS_TITLE = 1;// 若是 title 标签，记做1，注意有两个title，但我们都保存在item的成员变量中
	final int RSS_LINK = 2;// 若是 link 标签，记做2
	final int RSS_DESCRIPTION = 3;// 若是 description 标签，记做3
	final int RSS_CATEGORY = 4;// 若是category标签,记做 4
	final int RSS_PUBDATE = 5; // 若是pubdate标签,记做5,注意有两个pubdate,但我们都保存在item的pubdate成员变量中
	int currentstate = 0;

	public RssHandler() {
	}
	// 下面通过重载 DefaultHandler 的 5 个方法来实现 sax 解析

    // 1. 这个方法在解析xml文档的一开始执行,一般我们需要在该方法中初始化解析过程中有可能用到的变量
	public void startDocument() throws SAXException {
		super.startDocument();
		RSSFeed = new RSSFeed();
		RSSItem = new RSSItem();
	}
	// 2. 当遇到文本结点时进行处理，空白符不用做处理，只需要对字符做处理
	@Override
	public void characters(char[] ch, int start, int length) {
		String theString = new String(ch, start, length);
		// 获取字符串
		String text = new String(ch, start, length);
		Log.i("i", "要获取的内容："+text);
		// 判断当前标志位 与那一种标志相同，然后做相应处理
		switch (currentstate) {
		case RSS_TITLE:
			RSSItem.setTitle(text);
			currentstate = 0;// 设置完后，重置为开始状态
			break;
		case RSS_LINK:
			RSSItem.setLink(text);
			currentstate = 0;// 设置完后，重置为开始状态
			break;
		case RSS_DESCRIPTION:
			RSSItem.setDescription(text);
			currentstate = 0;// 设置完后，重置为开始状态
			break;
		case RSS_CATEGORY:
			RSSItem.setCategory(text);
			currentstate = 0;// 设置完后，重置为开始状态
			break;
		case RSS_PUBDATE:
			RSSItem.setPubdate(text);
			currentstate = 0;// 设置完后，重置为开始状态
			break;
		default:
			return;
		}
	}
	/**
	 * 3. 这个方法在解析标签开始标记时执行,一般我们需要在该方法取得标签属性值,但由于我们的rss文档
	 * 中并没有任何我们关心的标签属性,因此我们主要在这里进行的是设置标记变量currentstate, 以 标记我们处理到哪个标签
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		// localName:不含命名空间前缀的标签名(建议使用)
		// qName:含有命名空间前缀的标签名
		// attributes:接收属性值
		if (localName.equals("channel")) {
			currentstate = 0;
			return;
		}
		if (localName.equals("item")) {
			RSSItem = new RSSItem();
			return;
		}
		if (localName.equals("title")) {
			currentstate = RSS_TITLE;
			return;
		}
		if (localName.equals("description")) {
			currentstate = RSS_DESCRIPTION;
			return;
		}
		if (localName.equals("link")) {
			currentstate = RSS_LINK;
			return;
		}
		if (localName.equals("category")) {
			currentstate = RSS_CATEGORY;
			return;
		}
		if (localName.equals("pubdate")) {
			currentstate = RSS_PUBDATE;
			return;
		}
		currentstate = 0;
	}

	// 4. 结束元素节点
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 如果解析一个item节点结束，就将RSSItem添加到RSSFeed中
		if (localName.equals("item")) {
			RSSFeed.addItem(RSSItem);
			return;
		}
	}

	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();

		// 这个方法在整个xml文档解析结束时执行,一般需要在该方法中返回或保存整个文档解析解析结果,

		// 但由于我们已经在解析过程中把结果保持在rssFeed中,所以这里什么也不做

	}
	public RSSFeed getRssFeed() {
		return RSSFeed;
	}
	
}

