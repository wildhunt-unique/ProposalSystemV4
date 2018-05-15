package cn.edu.qut.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.edu.qut.DAO.ConferenceDAO;
import cn.edu.qut.DAO.DepartmentDAO;
import cn.edu.qut.DAO.HibernateUtil;
import cn.edu.qut.DAO.MemberDAO;
import cn.edu.qut.DAO.NewsDAO;
import cn.edu.qut.DAO.ProposalDAO;
import cn.edu.qut.beans.DeployInfo;
import cn.edu.qut.beans.NewsInfo;
import cn.edu.qut.beans.Node;
import cn.edu.qut.beans.ProposalInfo;
import cn.edu.qut.common.WordMap;
import cn.edu.qut.entity.Conference;
import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Deputation;
import cn.edu.qut.entity.Member;
import cn.edu.qut.entity.News;
import cn.edu.qut.entity.Proposal;

/*

                        _ooOoo_
                       o8888888o
                       88" . "88
                       (| -_- |)
                        O\ = /O
                    ____/`---'\____
                  .   ' \\| |// `.
                   / \\||| : |||// \
                 / _||||| -:- |||||- \
                   | | \\\ - /// | |
                 | \_| ''\---/'' | |
                  \ .-\__ `-` ___/-. /
               ___`. .' /--.--\ `. . __
            ."" '< `.___\_<|>_/___.' >'"".
           | | : `- \`.;`\ _ /`;.`/ - ` : | |
             \ \ `-. \_ __\ /__ _/ .-` / /
     ======`-.____`-.___\_____/___.-`____.-'======
                        `=---='

     .............................................
              佛祖保佑                永无BUG

                   江城子 . 程序员之歌

               十年生死两茫茫，写程序，到天亮。
                   千行代码，Bug何处藏。
              纵使上线又怎样，朝令改，夕断肠。

                需求每天新想法，天天改，日日忙。
                   相顾无言，惟有泪千行。
               每晚灯火阑珊处，夜难寐，加班狂。
 */
/**
 * 提案管理类
 * 
 * @author wildhunt_unique
 */
public class ProposalManagement {

	private static final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 会议的prop_Type
	 */
	private static final String meetting = "会议";

	/**
	 * 等待部署处理的状态
	 */
	private static final String dealState = "部署中";// deployState

	/**
	 * 等待团长审核的状态
	 */
	private static final String checkState = "待审核";

	/**
	 * 部署实施中的状态
	 */
	private static final String deployState = "实施中";// dealState

	/**
	 * 等待进行分类的提案
	 */
	private static final String passState = "通过";

	/**
	 * 得到审核要求的最少附议人数
	 */
	private static final int minSupprotNum = 3;

	/**
	 * 部门联络员权限
	 */
	private static final String masterClass = "master";

	/**
	 * 初始化状态,谁也没有进行握手
	 */
	private static final Integer noShake = 0;

	/**
	 * 部门第一次返回结果
	 */
	private static final Integer deptShake = 1;

	/**
	 * 提出人觉得不满意 进行交涉
	 */
	private static final Integer memShake = 2;

	/**
	 * 部门针对提出的交涉,再次修改意见
	 */
	private static final Integer deptReShake = 3;

	/**
	 * 分类的类型是立案
	 */
	private static final String passType = "立案";

	/**
	 * 创建一个新闻
	 * 
	 * @author wildhunt_unique
	 */
	public static String createNews(News news) {
		String stateCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			news.setNewsId(NewsDAO.getMaxId(session) + 1);
			session.persist(news);

			session.getTransaction().commit();
			stateCode = "success_100000";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";// 未知错误
		}

		return stateCode;
	}

	/**
	 * 得到所有的新闻信息(不包括内容)
	 * 
	 * @author wildhunt_unique
	 */
	public static ArrayList<NewsInfo> findAllNews() {
		ArrayList<NewsInfo> newsInfo_ArrayList = null;
		List<News> news_List = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			news_List = NewsDAO.findAllNews(session);
			newsInfo_ArrayList = NewsInfo.getNesInfo(news_List);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return newsInfo_ArrayList;
	}

	/**
	 * 得到一个新闻具体信息(包括内容)
	 * 
	 * @author wildhunt_unique
	 */
	public static NewsInfo getNewsById(int id) {
		NewsInfo newsInfo = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			News news = session.get(News.class, id);
			newsInfo = new NewsInfo(news);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return newsInfo;
	}

	/**
	 * 增加一个会议
	 * 
	 * @author dd
	 * @param conference
	 *            要增加的会议对象
	 *
	 * @return 返回一个状态码
	 */
	public static String addConference(String conName) {
		String stateCode = null;
		Conference conference = new Conference();
		conference.setConName(conName);
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			conference.setConId(ConferenceDAO.getConferenceMaxId(session) + 1);
			session.persist(conference);
			stateCode = "success_100000";
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";// 未知错误
		}
		return stateCode;
	}

	/**
	 * 检索所有的会议Node对象
	 * 
	 * @author dd
	 * 
	 * 
	 */
	public static ArrayList<Node> findAllConferenceNode() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		List<Conference> conList = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			conList = ConferenceDAO.findAllConference(session);
			nodes = Node.createNodeConList(conList);
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return nodes;
	}

	/**
	 * 根据会议ID，查找其下的所有提案
	 * 
	 * @author dd
	 * @param conId
	 *            会议ID
	 * @return 返回提案信息对象的 arraylist
	 */
	public static ArrayList<ProposalInfo> findProposalByConId(Integer conId) {
		ArrayList<ProposalInfo> proposalInfos = new ArrayList<ProposalInfo>();

		Conference conference = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			conference = session.get(Conference.class, conId);
			Set<Proposal> proposals = conference.getProposals();
			for (Proposal proposal : proposals) {
				ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
				proposalInfos.add(proposalInfo);
			}

			session.getTransaction().commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return proposalInfos;
	}

	/**
	 * 提案人提出实施的修改意见（必须是对立案，且必须是该提案的提出人提出修改意见）
	 * 
	 * @author dd
	 * @param propNo
	 *            提案编号
	 * @param memberId
	 *            成员ID
	 * @param alterIdea
	 *            修改意见
	 * 
	 * @return 返回状态码
	 */
	public static String addPropAlterIdea(String propNo, long memberId, String alterIdea) {
		String statecode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Proposal proposal = ProposalDAO.getProposalByNo(session, propNo);
			Member member = MemberDAO.getMemberById(session, memberId);
			if (proposal.getPropPassType().equals(passType) && proposal.getPropHandshake() == deptShake)// 如果该提案是立案,并且是第一次握手
			{
				if (proposal.getPropOwner() == member)// 如果该成员是该提案的提出人
				{
					proposal.setPropAlterIdea(alterIdea);
					proposal.setPropHandshake(memShake);// 进入到成员握手阶段
					statecode = "success_100000";

				} else {
					statecode = "error_100003";
				}

			} else
				statecode = "error_100003";
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return statecode;

	}

	/**
	 * 提案提出人为提案设置满意度
	 * 
	 * @author dd
	 * 
	 * @param propNo
	 *            提案编号
	 * @param memberNo
	 *            成员编号
	 * @return 返回状态码
	 */
	public static String addPropStatisfy(String memberNo, String propNo, String statisfy) {
		String statecode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			Proposal proposal = ProposalDAO.getProposalByNo(session, propNo);
			Member member = MemberDAO.getMemberByNo(memberNo, session);
			Integer handShakeState = proposal.getPropHandshake();
			if (proposal.getPropOwner() == member && proposal.getPropState().equals(deployState)
					&& handShakeState >= deptShake) { // 第一交互//部门至少得回复
				proposal.setPropSatisf(statisfy);
				proposal.setPropHandshake(deptReShake);
				proposal.setPropState("实施完成");
				statecode = "success_100000";
			} else
				statecode = "error_100003";

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return statecode;
	}

	/**
	 * 校长分管的部门下,其承办的提案
	 * 
	 * @author wildhunt_unique
	 */
	public static ArrayList<ProposalInfo> findProposalByPrex(long memberId) {
		ArrayList<ProposalInfo> proposalInfos = new ArrayList<ProposalInfo>();
		Set<Department> dept_set = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			Member member = MemberDAO.getMemberById(session, memberId);
			dept_set = member.getManageDept();
			for (Department department : dept_set) {
				Set<Proposal> proposals = department.getPrimaryProposal();
				for (Proposal proposal : proposals) {
					ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
					proposalInfos.add(proposalInfo);
				}
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return proposalInfos;
	}

	/**
	 * 校长查询其管理的部门
	 * 
	 * @author dd
	 * @param memberId
	 *            成员ID
	 * 
	 * @return 返回部门实体list
	 */
	public static ArrayList<Department> findDepartmentByPrex(long memberId) {
		ArrayList<Department> departments = new ArrayList<Department>();
		Set<Department> dept_set = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			Member member = MemberDAO.getMemberById(session, memberId);
			dept_set = member.getManageDept();
			for (Department department : dept_set) {
				departments.add(department);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return departments;
	}

	/**
	 * 查找状态为通过的提案
	 * 
	 * @author dd
	 * 
	 * @return 返回所有状态为通过的提案对象列表
	 */
	public static ArrayList<ProposalInfo> findPassProp() {
		List<Proposal> proposals = null;
		ArrayList<ProposalInfo> proposalInfos = new ArrayList<ProposalInfo>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			proposals = ProposalDAO.findPropByState(session, passState);

			for (Proposal proposal : proposals) {
				ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
				proposalInfos.add(proposalInfo);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return proposalInfos;
	}

	/**
	 * 根据输入的通过提案的ID，修改其通过类型,改为立案或建议
	 * 
	 * @author dd
	 * @param propNo
	 *            提案编号
	 * 
	 * @return 返回状态码
	 */
	public static String setPassPropByNo(String propNo, String propPassType) {
		String statecode = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			Proposal proposal = ProposalDAO.getProposalByNo(session, propNo);
			proposal.setPropPassType(propPassType);
			proposal.setPropState(dealState);
			statecode = "success_100000";

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return statecode;
	}

	/**
	 * 得到一个部门作为协办部门负责的提案信息
	 * 
	 * @author dd
	 * @param deptID
	 *            部门id
	 * 
	 * @return 返回提案信息对象的LIST
	 **/
	public static ArrayList<ProposalInfo> findHelpProp(Long memberId) {
		ArrayList<ProposalInfo> proposalInfos = new ArrayList<ProposalInfo>();
		Department department = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			Member member = session.get(Member.class, memberId);
			department = member.getDepartment();

			Set<Proposal> proposals = department.getHelpProposal();
			for (Proposal proposal : proposals) {
				ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
				proposalInfos.add(proposalInfo);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return proposalInfos;

	}

	/**
	 * 得到一个部门作为主办部门负责的提案信息
	 * 
	 * @author dd
	 * @param deptID
	 *            部门id
	 * 
	 * @return 返回提案信息对象的LIST
	 **/
	public static ArrayList<ProposalInfo> findPrimaryProp(long memberId) {
		ArrayList<ProposalInfo> proposalInfos = new ArrayList<ProposalInfo>();
		Department department = null;
		Member member = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			member = session.get(Member.class, memberId);
			department = member.getDepartment();
			Set<Proposal> proposals = department.getPrimaryProposal();
			for (Proposal proposal : proposals) {
				ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
				proposalInfos.add(proposalInfo);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return proposalInfos;

	}

	/**
	 * 
	 * 得到一个提案的全部部署信息
	 * 
	 * @author dd
	 * @param propNo
	 *            提案编号
	 * 
	 * @return 返回提案部署信息
	 */
	public static DeployInfo getPropDeplInfor(String propNo) {
		DeployInfo deployInfo = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			Proposal proposal = ProposalDAO.getProposalByNo(session, propNo);
			deployInfo = new DeployInfo(proposal, session);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return deployInfo;
	}

	/**
	 * 通过条件查询得到提案信息list
	 * 
	 * @author wildhunt_unique
	 * @param propState
	 *            提案状态
	 * @param propType
	 *            提案类型 (议案,会议)
	 * @param beginDate
	 *            起止时间
	 * @param endDate
	 *            起止时间
	 */
	public static ArrayList<ProposalInfo> findPropByCondition(String propState, String propType, String beginDate,
			String endDate) {
		ArrayList<ProposalInfo> proposalInfos = new ArrayList<ProposalInfo>();
		List<Proposal> proposals = null;

		Date beginDate_Date = null;
		Date endDate_Date = null;

		propState = WordMap.translate(propState);
		propType = WordMap.translate(propType);

		if (!beginDate.equals("") && !endDate.equals("")) {
			beginDate = beginDate + " 00:00:01";
			endDate = endDate + " 23:59:59";
			beginDate_Date = new Date();
			endDate_Date = new Date();
			try {
				beginDate_Date = fmt.parse(beginDate);
				endDate_Date = fmt.parse(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			proposals = ProposalDAO.findPropByCondition(session, propState, propType, beginDate_Date, endDate_Date);
			for (Proposal proposal : proposals) {
				ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
				proposalInfos.add(proposalInfo);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return proposalInfos;
	}

	/**
	 * 为一个提案添加部署分配部门
	 * 
	 * @author dd
	 * @param primaryDeptId
	 *            主办部门ID
	 * @param propNo
	 *            提案NO
	 * @param helpDeptId
	 *            协办部门IDlist
	 * @return 返回状态码
	 * 
	 */
	public static String addDeploy(Integer primaryDeptId, String propNo, ArrayList<Integer> helpDeptId) {
		String stateCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			Proposal proposal = ProposalDAO.getProposalByNo(session, propNo);
			Department primaryDept = DepartmentDAO.findDepartment(session, primaryDeptId);
			proposal.setPropState(deployState);
			proposal.setPrimaryDept(primaryDept);

			for (Integer helpId : helpDeptId) {
				Department department = DepartmentDAO.findDepartment(session, helpId);
				proposal.getHelpDept().add(department);
			}

			proposal.setPropDeplTime(new Date());
			proposal.setPropHandshake(noShake);

			session.getTransaction().commit();
			stateCode = "success_100000";

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			stateCode = "error_100003";
			return stateCode;
		}
		return stateCode;
	}

	/**
	 * 返回部门实施结果信息
	 * 
	 * @author dd
	 * @param deptId
	 *            部门ID
	 * @param implSituation
	 *            部门实施的结果
	 * 
	 * @return 返回状态码
	 */
	public static String addImplInfor(String propNo, String implSituation, Long memberId) {
		String stateCode = "";

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			Member member = session.get(Member.class, memberId);// 当前操作人
			Proposal proposal = ProposalDAO.getProposalByNo(session, propNo);
			if (proposal.getPropHandshake() == deptReShake || !member.getMemberClass().equals(masterClass))// 如果已经有了实施结果或者当前操作人不是部门联络员，返回错误信息
			{
				stateCode = "error_100003";
			} else if (proposal.getPropHandshake() == noShake)// 如果一次实施结果都没返回
			{
				proposal.setPropImpl(implSituation);
				proposal.setPropImplTime(new Date());
				proposal.setPropHandshake(deptShake);// 握手设为1
				stateCode = "success_100000";

			} else if (proposal.getPropHandshake() == memShake) {// 如果提案人有修改意见，部门重新进行反馈
				proposal.setPropImpl(implSituation);
				proposal.setPropImplTime(new Date());
				proposal.setPropHandshake(deptReShake);// 握手设为3
				stateCode = "success_100000";
			} else {
				stateCode = "error_100003";
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return stateCode;
	}

	/**
	 * 查找所有等待管理员进行部署的提案
	 * 
	 * @author dd
	 * 
	 * @return 返回所有状态为通过的提案对象列表
	 */
	public static ArrayList<ProposalInfo> findAllPassProp() {
		List<Proposal> proposals = null;
		ArrayList<ProposalInfo> proposalInfos = new ArrayList<ProposalInfo>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			proposals = ProposalDAO.findPropByState(session, dealState);

			for (Proposal proposal : proposals) {
				ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
				proposalInfos.add(proposalInfo);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return proposalInfos;
	}

	/**
	 * 通过Id值 获得一个提案信息对象 (已通过测试)
	 * 
	 * @author wildhunt_unique
	 * @return ProposalInfo 提案信息 包括内容
	 * @param propId
	 *            提案的Id
	 */
	public static ProposalInfo getProposalInfoById(long propId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		ProposalInfo proposalInfo = null;
		Proposal proposal = null;
		try {
			session.getTransaction().begin();

			proposal = ProposalDAO.getProposalById(session, propId);
			proposalInfo = new ProposalInfo(session, proposal);
			proposalInfo.setPropContent(proposal.getPropContent());

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return proposalInfo;
	}

	/**
	 * 得到所有提案的装载信息
	 * 
	 * @author wildhunt_unique
	 * 
	 */
	public static ArrayList<ProposalInfo> findAllProposalInfo() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		ArrayList<ProposalInfo> proposalInfo_List = new ArrayList<ProposalInfo>();
		ProposalInfo proposalInfo = null;
		List<Proposal> proposal_List = null;

		try {
			session.getTransaction().begin();

			proposal_List = ProposalDAO.findAllProposals(session);

			for (Proposal proposal : proposal_List) {// 不要保存的
				if (proposal.getPropState().equals("保存")) {
					continue;
				}
				proposalInfo = new ProposalInfo(session, proposal);
				proposalInfo_List.add(proposalInfo);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return proposalInfo_List;
	}

	/**
	 * 添加一个提案(已通过测试)
	 * 
	 * @author wildhunt_unique
	 * @param proposal
	 *            提案对象 不包括Id、拥有者
	 * @param memberId
	 *            提案提出者的Id
	 * @return
	 * @return boolean 是否添加成功
	 */
	public static String addProposal(Proposal proposal, long memberId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Member member = null;
		try {
			session.getTransaction().begin();

			member = MemberDAO.getMemberById(session, memberId);
			proposal.setPropId(ProposalDAO.getProposalMaxId(session) + 1);
			proposal.setPropNo("P" + proposal.getPropId());
			proposal.setPropHandshake(noShake);
			ProposalDAO.addProposal(session, proposal, member);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";// 未知错误
		}
		return "success_100000";// 操作成功
	}

	/**
	 * 添加一个会议提案(已通过测试)
	 * 
	 * @author wildhunt_unique
	 * @param proposal
	 *            提案对象 不包括Id、拥有者
	 * @param memberId
	 *            提案提出者的Id
	 * @return
	 * @return boolean 是否添加成功
	 */
	public static String addMeetingProp(Proposal proposal, long memberId, int conId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Member member = null;
		try {
			session.getTransaction().begin();

			member = MemberDAO.getMemberById(session, memberId);
			proposal.setPropId(ProposalDAO.getProposalMaxId(session) + 1);
			proposal.setPropNo("P" + proposal.getPropId());
			proposal.setPropHandshake(noShake);
			Conference conference = session.get(Conference.class, conId);
			ProposalDAO.addMeetingProposal(session, proposal, member, conference);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";// 未知错误
		}
		return "success_100000";// 操作成功
	}

	/**
	 * 得到一个团员的所有提案信息 不包括内容!!!(已通过测试)
	 * 
	 * @author wildhunt_unique
	 * @param memberId
	 *            团员Id
	 * @return 提案信息列表 不包括内容!!!
	 */
	public static ArrayList<ProposalInfo> findProposalByMember(long memberId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		ArrayList<ProposalInfo> proposalInfos = new ArrayList<ProposalInfo>();
		Member member = null;
		ProposalInfo proposalInfo = null;
		try {
			session.getTransaction().begin();

			member = MemberDAO.getMemberById(session, memberId);
			Set<Proposal> proposals = member.getProposals();

			for (Proposal proposal : proposals) {
				proposalInfo = new ProposalInfo(session, proposal);
				proposalInfos.add(proposalInfo);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return proposalInfos;
	}

	/**
	 * 得到一个提案的具体信息对象 包括内容(已通过测试)
	 * 
	 * @author wildhunt_unique
	 * @return ProposalInfo提案信息对象
	 * @param propNo
	 *            提案编号
	 */
	public static ProposalInfo getProposalInfoByNo(String propNo) {
		ProposalInfo proposalInfo = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			Proposal proposal = ProposalDAO.getProposalByNo(session, propNo);
			proposalInfo = new ProposalInfo(proposal);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return proposalInfo;
	}

	/**
	 * 得到一个团员所有可以被附议的提案信息 按时间递减排序 不包括内容(已通过测试)
	 * 
	 * @author wildhunt_unique
	 * @param memberId
	 *            该团员的Id值
	 * @return 提案信息list
	 */
	public static ArrayList<ProposalInfo> findSupportPropByMember(long memberId) {
		ArrayList<ProposalInfo> proposalInfo_list = new ArrayList<ProposalInfo>();
		List<Proposal> proposals;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			proposals = ProposalDAO.findPropByState(session, checkState);
			for (Proposal proposal : proposals) {
				if (proposal.getPropOwner().getMemberId() != memberId) {// 不要自己的
					ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
					proposalInfo_list.add(proposalInfo);
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return proposalInfo_list;
	}

	/**
	 * 为一个提案添加一个附议人(已通过测试)
	 * 
	 * @author wildhunt_unique
	 * @param memberId
	 *            附议人Id
	 * @param propNo
	 *            被附议的提案编号
	 * @return 信息编码
	 */
	public static String addSupport(long memberId, String propNo) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Proposal proposal = null;
		Member member = null;

		try {
			session.getTransaction().begin();

			proposal = ProposalDAO.getProposalByNo(session, propNo);
			member = MemberDAO.getMemberById(session, memberId);

			if (member.getMemberId() == proposal.getPropOwner().getMemberId()) { // 不能自己附议自己,error_100002
				return "error_100002";
			}

			for (Member memberSup : proposal.getMemberSup()) { // 已经附议的,不能再附议,error_100001
				if (memberSup.getMemberId() == memberId) {
					return "error_100001";
				}
			}
			proposal.getMemberSup().add(member);
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error_000000";// 未知错误
		}
		return "success_100001"; // 附议成功 success_100001
	}

	/**
	 * 获得一个团长下,该代表团所有可以的提案(已通过测试)
	 * 
	 * @author wildhunt_unique
	 * @param memberId
	 *            团长id
	 * @return ProposalInfo对象列表
	 */
	public static ArrayList<ProposalInfo> findCheckProposalInfos(long memberId) {
		ArrayList<ProposalInfo> proposalInfo_list = new ArrayList<ProposalInfo>();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Member member = null;
		Deputation deputation = null;
		Set<Member> members = null;

		try {
			session.getTransaction().begin();

			member = MemberDAO.getMemberById(session, memberId);
			deputation = member.getDeputation();
			members = deputation.getMembers();
			for (Member memberItem : members) {
				Set<Proposal> proposal_Set = memberItem.getProposals();
				for (Proposal proposal : proposal_Set) {// 循环每个人的提案
					ProposalInfo proposalInfo = new ProposalInfo(session, proposal);
					int supprotNum = proposal.getMemberSup().size();
					if (proposal.getPropState().equals(checkState)) { // 首先,只要待审核的
						if (supprotNum >= minSupprotNum || proposal.getPropType().equals(meetting)) {// 其次,只需要超过四人附议的提案//或者是会议的提案
							proposalInfo_list.add(proposalInfo);
						}
					}
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return proposalInfo_list;
	}

	/**
	 * 审核一个提案 传入将来的状态 以及 被审核的提案编号 一开始只有上帝和我知道,我在写什么 现在,只有上帝知道
	 * 
	 * @author wildhunt_un2ique
	 * @return 状态码
	 */
	public static String checkProposalByNo(String propState, String propNo) {
		String stateCode = "";
		Proposal proposal = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			proposal = ProposalDAO.getProposalByNo(session, propNo);
			if (proposal.getPropState().equals(checkState)) { // 只能审核那些待审核的
				int supprotNum = proposal.getMemberSup().size();
				if (supprotNum >= minSupprotNum || proposal.getPropType().equals(meetting)) { // 其次只能操作,附议人数超过最低人数,或者是会议
					proposal.setPropState(propState);
					stateCode = "success_100000";
				} else {
					stateCode = "error_100005";
				}
			} else {
				stateCode = "error_100005";// 只能审核那些待审核的
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";
		}
		return stateCode;
	}

	/**
	 * 修改一个提案的信息
	 * 
	 * @author wildhunt_unique
	 * 
	 */
	public static String modifyProposalByNo(Long memberId, String propNo, String propTitle, String propContent,
			String propType, String propStateIn) {
		Proposal proposal = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			proposal = ProposalDAO.getProposalByNo(session, propNo);
			String propState = proposal.getPropState();

			if (memberId != proposal.getPropOwner().getMemberId()) { // 不是自己的
				return "error_100004"; // 无权进行此项操作
			}

			if (!(propState.equals(checkState) || propState.equals("保存"))) { // 不是保存,也不是,修改
				return "error_100003"; // 无权进行此项操作
			}

			if (propState.equals(checkState)) { // 修改的情况

				proposal.setPropContent(propContent);
				proposal.setPropTitle(propTitle);

				if (propStateIn.equals("保存")) {
					return "error_100003"; // 无权进行此项操作
				} else {
					proposal.setPropState(checkState);
				}

				proposal.setPropLast(new Date());

			} else { // 保存的情况

				proposal.setPropContent(propContent);
				proposal.setPropTitle(propTitle);
				proposal.setPropDate(new Date());
				proposal.setPropState(propStateIn);
				proposal.setPropType(propType);
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";
		}

		return "success_100000";
	}
}
