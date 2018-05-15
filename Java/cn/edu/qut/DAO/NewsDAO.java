package cn.edu.qut.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.edu.qut.entity.News;

public class NewsDAO {
	/**
	 * 找到最大id
	 * 
	 * @author wildhunt_unique
	 */
	public static Integer getMaxId(Session session) {
		Number maxId = null;
		String sql = "select max(n.newsId) from " + News.class.getName() + " n";
		Query<Number> query = session.createQuery(sql);
		maxId = query.getSingleResult();
		if (maxId == null) {
			maxId = 1000;
		}
		return maxId.intValue();
	}

	/**
	 * 找到所有新闻
	 * 
	 * @author wildhunt_unique
	 */
	public static List<News> findAllNews(Session session) {
		List<News> news_list = new ArrayList<News>();
		String sql = "select n from " + News.class.getName() + " n";
		Query<News> query = session.createQuery(sql);
		news_list = query.getResultList();
		return news_list;
	}
}
