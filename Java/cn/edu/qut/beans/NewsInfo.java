package cn.edu.qut.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.edu.qut.entity.News;

public class NewsInfo {
	private int newsId;

	private String newsTitle;

	private String newsContent;

	private String newsDate;

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 日期格式
	
	/**
	 * 得到所有新闻的信息,不包括内容
	 * */
	public static ArrayList<NewsInfo> getNesInfo(List<News> news_List) {
		ArrayList<NewsInfo> newsInfos = new ArrayList<NewsInfo>();

		for (News news : news_List) {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setNewsDate(df.format(news.getNewsDate()));
			newsInfo.setNewsTitle(news.getNewsTitle());
			newsInfo.setNewsId(news.getNewsId());
			newsInfos.add(newsInfo);
		}

		return newsInfos;
	}
	
	/**
	 * 得到一个新闻的具体信息,包括内容
	 * */
	public NewsInfo(News news){
		this.newsContent = news.getNewsContent();
		this.newsDate = df.format(news.getNewsDate());
		this.newsTitle = news.getNewsTitle();
		this.newsId = news.getNewsId();
	}
	
	public NewsInfo(){
		
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

}
