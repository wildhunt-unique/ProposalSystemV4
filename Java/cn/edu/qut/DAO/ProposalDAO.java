package cn.edu.qut.DAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.edu.qut.beans.ProposalInfo;
import cn.edu.qut.entity.Conference;
import cn.edu.qut.entity.Member;
import cn.edu.qut.entity.Proposal;

public class ProposalDAO {

	private static final String passState = "部署中";// 部署提案前状态信息
	private static final String checkState = "待审核";// 部署提案前状态信息

	@SuppressWarnings("deprecation")
	public static List<Proposal> findPropByState(Session session, String propState) {
		List<Proposal> proposals = null;

		String sql = "select p from " + Proposal.class.getName() + " p where p.propState = :propState";
		Query<Proposal> query = session.createQuery(sql);
		query.setParameter("propState", propState);
		proposals = query.getResultList();

		return proposals;
	}

	/**
	 * 通过条件查询得到一些提案的信息
	 * 
	 * @author wildhunt_unique
	 */
	public static List<Proposal> findPropByCondition(Session session, String propState, String propType, Date beginDate,
			Date endDate) {
		List<Proposal> proposals = null;

		String sqlString = "select p from " + Proposal.class.getName() + " p ";

		if (propState != null || propType != null || (beginDate != null && endDate != null)) {
			sqlString = sqlString + " where 1=1 ";
		}

		if (propState != null) {
			sqlString = sqlString + " and p.propState = :propState ";
		}

		if (propType != null) {
			sqlString = sqlString + " and p.propType = :propType ";
		}

		if (beginDate != null && endDate != null) {
			sqlString = sqlString + "and propDate > :beginDate and propDate< :endDate ";
		}

		Query<Proposal> query = session.createQuery(sqlString);

		if (propState != null) {
			query.setParameter("propState", propState);
		}

		if (propType != null) {
			query.setParameter("propType", propType);
		}

		if (beginDate != null && endDate != null) {
			query.setParameter("beginDate", beginDate);
			query.setParameter("endDate", endDate);
		}

		proposals = query.getResultList();

		return proposals;
	}

	public static List<Proposal> findAllCheckProp(Session session) {
		List<Proposal> proposals = null;

		String sql = "select p from " + Proposal.class.getName() + " p where p.propState = :propState";
		Query<Proposal> query = session.createQuery(sql);
		query.setParameter("propState", checkState);
		proposals = query.getResultList();

		return proposals;
	}

	/**
	 * 查找状态为通过的所有提案
	 * 
	 * @author dd
	 * 
	 * @return 返回所有状态为通过的提案对象
	 */
	public static List<Proposal> passProposal(Session session) {

		List<Proposal> proposals = null;
		String sql = "select p from " + Proposal.class.getName() + " p where p.propState = :state";

		Query<Proposal> query = session.createQuery(sql);
		query.setParameter("state", passState);
		proposals = query.getResultList();

		return proposals;
	}

	/**
	 * 找到一个代表团下所有待审核的提案,按时间递减排序(作废)
	 * 
	 * @author wildhunt_unique
	 * @param depuId代表团Id
	 * @return Proposal提案对象list
	 */
	public static List<Proposal> findSupportProp(Session session, String depuId) {
		List<Proposal> proposals = null;

		String sql = "select p from ";

		return proposals;
	}

	/**
	 * @author wildhunt_unique
	 * @return 得到当前提案的最大Id值
	 */
	public static long getProposalMaxId(Session session) {
		Number maxId = null;

		String sql = "select max(p.propId) from " + Proposal.class.getName() + " p ";
		Query<Number> query = session.createQuery(sql);
		maxId = query.getSingleResult();
		if (maxId == null) {
			maxId = 100000L;
		}

		return maxId.longValue();
	}

	/**
	 * @author wildhunt_unique
	 * @param proposal
	 *            要添加的提案对象
	 * @param member
	 *            提案的拥有者
	 * @return 是否添加成功
	 */
	public static boolean addProposal(Session session, Proposal proposal, Member member) {
		try {
			proposal.setPropOwner(member);
			session.save(proposal);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * @author wildhunt_unique
	 * @param proposal
	 *            要添加的提案对象
	 * @param member
	 *            提案的拥有者
	 * @return 是否添加成功
	 */
	public static boolean addMeetingProposal(Session session, Proposal proposal, Member member,Conference conference) {
		try {
			proposal.setPropOwner(member);
			proposal.setPropCon(conference);
			session.save(proposal);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * 给出所有的提案实体类
	 * 
	 * @author wildhunt_unique
	 * @return Proposal的list列表
	 */
	public static List<Proposal> findAllProposals(Session session) {
		List<Proposal> proposal_List = null;

		String sql = "select p from " + Proposal.class.getName() + " p ";
		Query<Proposal> query = session.createQuery(sql);
		proposal_List = query.getResultList();

		return proposal_List;
	}

	/**
	 * 通过提案编号 来获得一个提案对象
	 * 
	 * @author wildhunt_unique
	 * @param propNo
	 *            提案编号
	 * @return 一个Proposal对象
	 */
	public static Proposal getProposalByNo(Session session, String propNo) {
		Proposal proposal = null;

		String sql = "select p from " + Proposal.class.getName() + " p where p.propNo = :propNo";
		Query<Proposal> query = session.createQuery(sql);
		query.setParameter("propNo", propNo);
		proposal = query.getSingleResult();

		return proposal;
	}

	/**
	 * @author wildhunt_unique
	 */
	public static Proposal getProposalById(Session session, long propId) {
		Proposal proposal = null;

		String sql = "select p from " + Proposal.class.getName() + " p " + " where p.propId = :propId";
		Query<Proposal> query = session.createQuery(sql);
		query.setParameter("propId", propId);
		proposal = query.getSingleResult();

		return proposal;
	}

}
