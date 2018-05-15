package cn.edu.qut.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import cn.edu.qut.entity.Conference;

public class ConferenceDAO {
   /********************************12.28***********************************************/
	/**
	 * 找到会议最大的编号
	 * @author dd
	 * 
	 * 
	 * @return 返回当前会议ID最大值
	 * */
	public static Integer getConferenceMaxId(Session session)
	{
		Integer maxId = null;
		String sql = "select max(c.conId) from "+Conference.class.getName()+" c";
	
		Query<Integer> query = session.createQuery(sql);
		maxId = query.getSingleResult();
		if (maxId == null) {
			maxId = 100000;
		}
		return maxId;

	}
	
	/**
	 * 找到所有的会议
	 * @author dd
	 * 
	 * @return 返回一个会议对象的list
	 */
	public static List<Conference> findAllConference(Session session)
	{
		List<Conference>conferences=null;
		String sql="select c from "+Conference.class.getName()+" c";
		Query<Conference>query=session.createQuery(sql);
		conferences=query.getResultList();
		
		return conferences;
	}
	
 /***********************************************************************************/
	
}
