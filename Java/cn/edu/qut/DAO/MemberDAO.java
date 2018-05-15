package cn.edu.qut.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Deputation;
import cn.edu.qut.entity.Member;

public class MemberDAO {

	private static final String memberClass = "root";
	private static final String flag = "true";

	/**
	 * 姓名的模糊查询
	 * @author dd
	 * 
	 * @param memberName  成员姓名的一部分
	 * 
	 * @return 返回名字中包含memberName的成员对象
	 * */
	public static List<Member> indistinctName(Session session,String memberName)
	{
		List<Member> members=null;
		
		String sql="select m from "+Member.class.getName()+" m where m.flag =:flag and m.memberName like '%"+memberName+"%'";
		Query<Member> query = session.createQuery(sql);
		query.setParameter("flag", flag);
		members=query.getResultList();
		
		return members;
	}
	
	/**
	 * 根据工号找到一个成员对象
	 * 
	 * @author dd
	 * 
	 * @param memberNo
	 *            成员工号
	 * 
	 * @return 返回一个成员对象
	 */
	public static Member getMemberByNo(String memberNo, Session session) {
		Member member = null;
		String sql = "select m from " + Member.class.getName() + " m where m.memberNo = :memberNo ";
		Query<Member> query = session.createQuery(sql);
		query.setParameter("memberNo", memberNo);
		member = query.getSingleResult();
		return member;
	}

	/**
	 * 通过账号密码获取查询是否存在对应的Member对象
	 * 
	 * @author wildhunt_unique
	 * @param memberNo
	 *            账号
	 * @param memberPassword
	 *            密码
	 * @return Member 对应的Member对象
	 */
	public static Member findMemberByLogin(String memberNo, String memberPassword) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Member member = null;
		try {
			session.getTransaction().begin();

			String sql = "select m from " + Member.class.getName() + " m "
					+ "where m.memberNo = :memberNo and m.memberPassword = :memberPassword";
			Query<Member> query = session.createQuery(sql);
			query.setParameter("memberPassword", memberPassword);
			query.setParameter("memberNo", memberNo);
			member = query.getSingleResult();

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return member;
	}
	
	
	/**
	 * 找到团员中最大的Id值
	 * 
	 * @author wildhunt_unique
	 * @return 团员的最大Id
	 */
	public static long findMemberMaxId(Session session) {
		String sql = "select max(m.memberId) from " + Member.class.getName() + " m ";
		Query<Number> query = session.createQuery(sql);
		Number maxId = query.getSingleResult();
		if (maxId == null) {
			maxId = 100000L;
		}
		return maxId.longValue();
	}

	/**
	 * 通过Id值,找到对应的团员对象
	 * 
	 * @author wildhunt_unique
	 * @param
	 * @return Member对象
	 */
	public static Member getMemberById(Session session, long memberId) {
		Member member = null;

		String sql = "select m from " + Member.class.getName() + " m where m.memberId = :memberId";
		Query<Member> query = session.createQuery(sql);
		query.setParameter("memberId", memberId);
		member = query.getSingleResult();

		return member;
	}

	/**
	 * 通过No值,找到对应的团员对象
	 * 
	 * @author wildhunt_unique
	 * @param
	 * @return Member对象
	 */
	public static Member getMemberByNo(Session session, String memberNo) {
		Member member = null;

		String sql = "select m from " + Member.class.getName() + " m where m.memberNo = :memberNo";
		Query<Member> query = session.createQuery(sql);
		query.setParameter("memberNo", memberNo);
		member = query.getSingleResult();

		return member;
	}

	/**
	 * 返回所有成员的信息
	 * 
	 * @author dd
	 * 
	 * @return 返回一组member对象的信息
	 */

	public static List<Member> findAllMember(Session session) {
		List<Member> members = null;
		String sql = "select m from " + Member.class.getName() + " m where  m.flag = :flag";
		Query<Member> query = session.createQuery(sql);
		query.setParameter("flag", flag);
		members = query.getResultList();

		return members;
	}

	/**
	 * 返回所有成员的信息
	 * 
	 * @author dd
	 * 
	 * @return 返回一组member对象的信息
	 */
	public static List<Member> allMember(Session session) {

		List<Member> members = null;
		String sql = "select m from " + Member.class.getName() + " m where  m.flag = :flag";
		Query<Member> query = session.createQuery(sql);
		query.setParameter("flag", flag);
		members = query.getResultList();

		return members;
	}
}
