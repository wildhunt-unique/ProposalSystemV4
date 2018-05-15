package cn.edu.qut.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.edu.qut.entity.Deputation;
import cn.edu.qut.entity.Member;

public class DeputationDAO {
	/**
	 * @author wildhunt_unique
	 */
	public static Deputation findDeputationById(Integer depuId, Session session) {
		Deputation deputation = null;
		String sql = "select d from " + Deputation.class.getName() + " d where d.depuId = :depuId";
		Query<Deputation> query = session.createQuery(sql);
		query.setParameter("depuId", depuId);
		deputation = query.getSingleResult();
		return deputation;
	}
	
	/**
	 * 找到代表团id的最大值
	 * @author wildhunt_unique
	 * */
	public static Integer findDeputationMaxId(Session session){
		Integer maxId;
		
		String sql = "select max(d.depuId) from " + Deputation.class.getName() + " d" ;
		Query<Integer> query = session.createQuery(sql);
		maxId = query.getSingleResult();
		if(maxId == null){
			maxId = 10000;
		}
		
		return maxId;
	}
	
	public static List<Deputation> findAllDeputations(Session session){
		List<Deputation> deputations = null;
		
		String sql = "select d from " + Deputation.class.getName() + " d" ;
		Query<Deputation> query = session.createQuery(sql);
		deputations = query.getResultList();
		
		return deputations;
	}
}
