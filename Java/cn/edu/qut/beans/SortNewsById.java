package cn.edu.qut.beans;

import java.util.Comparator;

import cn.edu.qut.entity.News;

public class SortNewsById implements Comparator<News> {

	public int compare(News o1, News o2) {
		// TODO Auto-generated method stub
		return (o2.getNewsId()+"").compareTo(o1.getNewsId()+"");
	}
}
