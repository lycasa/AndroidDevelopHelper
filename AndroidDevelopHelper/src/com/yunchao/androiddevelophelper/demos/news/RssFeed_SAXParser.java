package com.yunchao.androiddevelophelper.demos.news;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class RssFeed_SAXParser {

	public RSSFeed getFeed(String urlStr) throws MalformedURLException,
			Exception, SAXException {// 需要穿一个URL地址

		URL url = new URL(urlStr);
		System.out.println("RssFeed_SAXParser-->url:" + url);
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();// 构建sax解析工厂
		SAXParser saxParser = parserFactory.newSAXParser();// 解析工厂生产解析器
		XMLReader xmlReader = saxParser.getXMLReader();// 通过saxParser构建xmlReader阅读器
		// 构建自定义的xml解析器 作为 xmlReader的处理器（代理）
		RssHandler rssHandler = new RssHandler();

		xmlReader.setContentHandler(rssHandler);
		// 使用url打开流，并将流作为 xmlReader解析的输入源并解析
		InputSource is = new InputSource(url.openStream());

		xmlReader.parse(is);

		//return rssHandler.getFeed();
		return rssHandler.getRssFeed();
	}
}

