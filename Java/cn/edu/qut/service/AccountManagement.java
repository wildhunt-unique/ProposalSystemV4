package cn.edu.qut.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.edu.qut.DAO.DepartmentDAO;
import cn.edu.qut.DAO.DeputationDAO;
import cn.edu.qut.DAO.HibernateUtil;
import cn.edu.qut.DAO.MemberDAO;
import cn.edu.qut.beans.DeptInfo;
import cn.edu.qut.beans.DepuInfo;
import cn.edu.qut.beans.MemberInfo;
import cn.edu.qut.beans.Node;
import cn.edu.qut.beans.TreeNode;
import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Deputation;
import cn.edu.qut.entity.Member;

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

public class AccountManagement {

	/**
	 * 默认部门
	 */
	private static final Integer depablock = 1000;

	/**
	 * 默认代表团
	 */
	private static final Integer depublock = 10000;

	/**
	 * 管理员权限
	 */
	private static final String rootClass = "root";

	/**
	 * 部门联络员权限
	 */
	private static final String masterClass = "master";

	/**
	 * 普通用户权限
	 */
	private static final String userClass = "user";

	/**
	 * 团长权限
	 */
	private static final String adminClass = "user";

	/**
	 * 分管校长权限
	 */
	private static final String manageClass = "manager";

	/**
	 * 删除标志位 true是存在 false是被删除
	 */
	private static final String flag = "true";

	/**
	 * 给一个成员设置其管理的部门（校长）
	 * 
	 * @author dd
	 * @param memberNo
	 *            成员no
	 * @param deptid
	 *            部门ID
	 * 
	 * @return 返回状态码
	 */
	public static String addDeptManager(String memberNo, ArrayList<Integer> deptid) {
		String statecode = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			Member member = MemberDAO.getMemberByNo(session, memberNo);
			if (member.getMemberClass().equals(adminClass)) {// 原来如果是团长
				member.getDeputation().setManageId(null);// 撤职
			}
			if (member.getMemberClass().equals(masterClass)) {// 原来是部门联络员
				member.getDepartment().setMaster(null);// 撤职
			}
			member.getManageDept().clear();
			member.setMemberClass(manageClass);// 设置为团长权限
			for (Integer integer : deptid) {
				Department department = DepartmentDAO.findDepartment(session, integer);
				if (department.getFlag().equals(flag))// 如果该部门存在
				{
					department.setManager(null);
					department.setManager(member);
					statecode = "success_100000";
				} else {
					statecode = "error_100003";
				}

			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			statecode = "error";
		}

		return statecode;
	}

	/**
	 * 得到部门的节点
	 * 
	 * @author wildhunt_unique
	 */
	public static ArrayList<Node> getDeptNode() {
		ArrayList<Node> nodes = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			List<Department> departments = DepartmentDAO.findAllDepartment(session);
			nodes = Node.createNodeDeptList(departments);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return nodes;
	}

	/**
	 * 姓名的模糊查询
	 * 
	 * @author dd
	 * @param partName
	 * 
	 * @return 返回姓名中包含该片段的成员信息变量
	 */
	public static ArrayList<MemberInfo> findMemByName(String partName) {
		ArrayList<MemberInfo> memberInfos = new ArrayList<MemberInfo>();
		List<Member> members = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			members = MemberDAO.indistinctName(session, partName);

			for (Member member : members) {
				if (!(member.getMemberClass().equals(rootClass))) {
					MemberInfo memberInfo = new MemberInfo(member);
					memberInfos.add(memberInfo);
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return memberInfos;
	}

	/**
	 * 得到一个部门的信息
	 * 
	 * @author wildhunt_unique
	 * @param deptId
	 *            部门id
	 */
	public static DeptInfo getDeptInfoById(Integer deptId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		Department department = null;
		DeptInfo deptInfo = null;
		try {
			session.getTransaction().begin();

			department = session.get(Department.class, deptId);
			deptInfo = new DeptInfo(department);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return deptInfo;
	}

	/**
	 * 修改一个部门的信息
	 * 
	 * @author wildhunt_unique
	 * @param deptMaster
	 */
	public static String modifyDept(Integer deptId, String deptName, String deptMaster) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String stateCode = "";
		Member member = null;
		Department department = null;
		try {
			session.getTransaction().begin();
			department = session.get(Department.class, deptId);

			if (deptMaster != null && !deptMaster.equals("")) {
				if (department.getMaster() != null && !department.getMaster().equals("")) {
					member = MemberDAO.getMemberById(session, Integer.parseInt(department.getMaster()));// 找到原来的联络员
					member.setMemberClass(userClass);// 撤职
				}
				member = MemberDAO.getMemberById(session, Integer.parseInt(deptMaster));// 新的联络员
				member.setMemberClass(masterClass);
				department.setMaster(deptMaster);
			}

			department.setDeptName(deptName);

			session.getTransaction().commit();
			stateCode = "success_100000";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			stateCode = "error";
		}
		return stateCode;
	}

	/**
	 * 修改一代表团的信息
	 * 
	 * @author wildhunt_unique
	 */
	public static String modifyDepu(Integer depuId, long memberId, String depuName) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		String stateCode = "";
		Deputation deputation = null;
		Member member = null;

		try {
			session.getTransaction().begin();

			deputation = session.get(Deputation.class, depuId);
			deputation.setDepuName(depuName);

			if (memberId != 0) {
				member = session.get(Member.class, memberId);
				// 修改团长
				String manageId = deputation.getManageId();// 找到原来的团长的id
				if (manageId != null) {
					Member oldManage = MemberDAO.getMemberById(session, Integer.parseInt(manageId));
					oldManage.setMemberClass("user");// 原来的撤职
				}
				deputation.setManageId(member.getMemberId() + "");// 设置新的团长
				member.setMemberClass("admin");
			}

			session.getTransaction().commit();
			stateCode = "success_100000";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			stateCode = "error";
		}
		return stateCode;
	}

	/**
	 * 得到一个代表团的信息 用于修改
	 * 
	 * @author wildhunt_unique
	 * @return 代表团信息对象
	 */
	public static DepuInfo getDepuInfoById(Integer depuId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		DepuInfo depuInfo = null;

		try {
			session.getTransaction().begin();

			Deputation deputation = DeputationDAO.findDeputationById(depuId, session);
			depuInfo = new DepuInfo(deputation);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return depuInfo;
	}

	/**
	 * 删除部门
	 * 
	 * @author dd
	 * @param deptId
	 *            部门ID
	 * 
	 * @return 返回状态码
	 */
	public static String deleteDept(Integer deptId) {

		String stateCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			if (deptId != null && !deptId.equals(depablock))// 如果输入的部门ID合法,默认部门不能删除
			{
				Department department = DepartmentDAO.findDepartment(session, deptId);
				if (department.getFlag().equals(flag))// 如所找部门存在
				{
					Set<Member> members = department.getMembers();
					for (Member member : members) {
						member.setDepartment(DepartmentDAO.findDepartment(session, depablock));
					}
					department.setFlag("false");
					stateCode = "success_100000";
				} else {
					stateCode = "error_100003";
				}
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
	 * 删除代表团
	 * 
	 * @author dd
	 * @param depuId
	 *            代表团ID
	 * 
	 * @return 返回状态码
	 */
	public static String deleteDepu(Integer depuId) {

		String stateCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			if (depuId != null && !depuId.equals(depublock))// 如果输入的部门ID合法
			{
				Deputation deputation = DeputationDAO.findDeputationById(depuId, session);

				if (deputation.getFlag().equals(flag))// 如所找部门存在
				{
					deputation.setFlag("false");
					Set<Member> members = deputation.getMembers();
					String manageId = deputation.getManageId();
					if (manageId != null) {
						long manageid = Long.parseLong(manageId);
						Member Mmember = MemberDAO.getMemberById(session, manageid);
						Mmember.setMemberClass("user");
					}
					for (Member member : members) {
						member.setDeputation(DeputationDAO.findDeputationById(depublock, session));
					}
					stateCode = "success_100000";
				} else {
					stateCode = "error_100003";
				}
			} else
				stateCode = "error_100003";
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return stateCode;
	}

	/**
	 * 根据工号找到一个成员信息对象
	 * 
	 * @author dd
	 * @param memberNo
	 *            成员no
	 * 
	 * 
	 * @return 返回一个成员信息对象
	 */
	public static MemberInfo getMemberInfoByNo(String memberNo) {
		MemberInfo memberInfo = null;
		Member member = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			member = MemberDAO.getMemberByNo(memberNo, session);
			memberInfo = new MemberInfo(member);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return memberInfo;

	}

	/**
	 * 删除成员
	 * 
	 * @author dd
	 * @param memberId
	 *            成员ID
	 * 
	 * @return 返回状态码
	 */
	public static String deleteMember(ArrayList<String> memberNos) {
		String statdCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			for (String memberNo : memberNos) {
				if (memberNo != null) {
					Member member = MemberDAO.getMemberByNo(session, memberNo);
					if (member.getFlag().equals(flag))// 如果标志位为真，即该成员存在
					{
						member.setFlag("false");
						;// 将标志位置为false，即这个成员已被删除
						member.setMemberNo(null);// 将成员工号置为空
						statdCode = "success_100000";
					} else
						statdCode = "error_100003";
					session.persist(member);
				} else {
					statdCode = "error_100003";
				}

			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return statdCode;
	}

	/**
	 * 判断输入的工号是否是已存在
	 * 
	 * @author dd
	 * @param memberNo
	 *            待判断的成员工号
	 * 
	 * @return 返回一个布尔型，为true表示该工号尚未有成员使用，为false表示该编号已被其他成员使用
	 */
	public static boolean judgeMemberNo(String memberNo) {
		boolean b = true;
		List<Member> members = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();
			members = MemberDAO.findAllMember(session);

			for (Member member : members) {
				if (member.getMemberNo().equals(memberNo)) {
					b = false;
					break;
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		return b;
	}

	/****************************** 12月16号begin ***************************/
	/**
	 * 得到一个部门下所有成员信息
	 * 
	 * @author dd
	 * @param depaid
	 *            部门的ID
	 * @return 成员对象的列表
	 * 
	 */
	public static ArrayList<MemberInfo> findMemberByDepaId(Integer depaid) {
		ArrayList<MemberInfo> memberInfos = new ArrayList<MemberInfo>();
		List<Member> members = null;
		Set<Member> members_Set = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			if (depaid == null)// 如果传入的部门ID为空，则返回所有部门成员的信息
			{
				members = MemberDAO.allMember(session);
				for (Member member : members) {
					if (!member.getMemberClass().equals(rootClass))// 如果不是管理员
					{
						MemberInfo memberInfo = new MemberInfo(member);
						memberInfos.add(memberInfo);
					}
				}
			} else {
				Department department = session.get(Department.class, depaid);
				members_Set = department.getMembers();

				for (Member member : members_Set) {
					if (member.getFlag().equals(flag) && !member.getMemberClass().equals(rootClass))// 如果该成员存在并且不是管理员
					{
						MemberInfo memberInfo = new MemberInfo(member);
						memberInfos.add(memberInfo);
					}

				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return memberInfos;

	}

	/**
	 * 得到一个代表团下所有成员信息
	 * 
	 * @author dd
	 * @param depuid
	 *            代表团/部门的ID
	 * @return 成员对象的列表
	 * 
	 */
	public static ArrayList<MemberInfo> findMemberByDepuId(Integer depuid) {
		ArrayList<MemberInfo> memberInfos = new ArrayList<MemberInfo>();
		List<Member> members = null;
		Set<Member> members_Set = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			if (depuid == null)// 如果传入的代表团ID为空，则返回所有代表团成员的信息
			{
				members = MemberDAO.allMember(session);

				for (Member member : members) {
					if (member.getMemberClass().equals(rootClass)) {// 不要管理员
						continue;
					}
					if (member.getDeputation().getDepuId() != depublock) {
						MemberInfo memberInfo = new MemberInfo(member);
						memberInfos.add(memberInfo);
					}
				}
			} else {
				Deputation deputation = session.get(Deputation.class, depuid);
				members_Set = deputation.getMembers();

				for (Member member : members_Set) {
					if (member.getFlag().equals(flag) && !(member.getMemberClass().equals(rootClass))) {
						MemberInfo memberInfo = new MemberInfo(member);
						memberInfos.add(memberInfo);
					}
				}
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return memberInfos;

	}

	/****************************** 12月16号end ***************************/

	/**************************** 12月14号 ******************************/
	/**
	 * 增加一个部门
	 * 
	 * @author dd
	 * @param dpt
	 *            部门对象,不包括部门Id
	 * @return 返回一个状态码
	 */
	public static String addDepartment(Department dpt) {
		String stateCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			dpt.setFlag(flag);
			dpt.setDeptId(DepartmentDAO.getDepartmentMaxId(session) + 1);
			session.persist(dpt);
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
	 * 增加一个代表团
	 * 
	 * @author dd
	 * @param deputation
	 *            代表团对象,不包括代表团ID
	 * @return 返回一个状态码
	 * 
	 */
	public static String addDeputation(Deputation deputation) {
		String stateCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();

			deputation.setFlag(flag);
			deputation.setDepuId(DeputationDAO.findDeputationMaxId(session) + 1);
			session.persist(deputation);

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
	 * 增加部门成员
	 * 
	 * @author dd
	 * @param deptId
	 *            该成员所在部门的编号
	 * @param member
	 *            成员对象，不包含成员ID，所在部门ID
	 * @return 返回状态码
	 */
	public static String addMember(Member member, int deptId, int depuId) {
		String stateCode = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			member.setMemberId(MemberDAO.findMemberMaxId(session) + 1);
			member.setDepartment(DepartmentDAO.findDepartment(session, deptId));
			member.setDeputation(DeputationDAO.findDeputationById(depuId, session));
			member.setFlag(flag);
			session.persist(member);

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
	 * 为一个成员设置其代表团
	 * 
	 * @author dd
	 */
	public static String memeberSetDepu(long memberId, Integer depuId) {
		String stateCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			Member member = MemberDAO.getMemberById(session, memberId);
			member.setDeputation(DeputationDAO.findDeputationById(depuId, session));

			session.getTransaction().commit();
			stateCode = "success_100000";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";
		}

		return stateCode;
	}

	/**
	 * 为一个成员设置其代表团
	 * 
	 * @author dd
	 */
	public static String memeberSetDepu(String memberNo, Integer depuId) {
		String stateCode = null;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			Member member = MemberDAO.getMemberByNo(session, memberNo);
			member.setDeputation(DeputationDAO.findDeputationById(depuId, session));

			session.getTransaction().commit();
			stateCode = "success_100000";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";
		}

		return stateCode;
	}

	/*******************************************************************/

	/**
	 * 得到一个代表团下成员信息树
	 * 
	 * @author wildhunt_unique
	 * @param
	 * @return 代表团id
	 */
	public static ArrayList<TreeNode> getMemTreeByDepu(Integer depuId) {
		ArrayList<TreeNode> treeNode = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			Deputation deputation = session.get(Deputation.class, depuId);
			treeNode = TreeNode.createDepuMemTree(deputation);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return treeNode;
	}

	/**
	 * 得到一个部门下成员信息树
	 * 
	 * @author wildhunt_unique
	 * @param
	 * @return 部门id
	 */
	public static ArrayList<TreeNode> getMemTreeByDept(int deptId) {
		ArrayList<TreeNode> nodes = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			Department department = session.get(Department.class, deptId);
			nodes = TreeNode.createDeptMemTree(department);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return nodes;
	}

	/**
	 * 得到一个部门信息树
	 * 
	 * @author wildhunt_unique
	 * @return 部门信息树的节点
	 */
	public static ArrayList<TreeNode> getDepartmentTree() {
		ArrayList<TreeNode> treeNode = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			List<Department> departments = DepartmentDAO.findAllDepartment(session);
			treeNode = TreeNode.createDeptTree(departments);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return treeNode;
	}

	/**
	 * 得到一个代表团信息树
	 * 
	 * @author wildhunt_unique
	 * @return 部门信息树的节点
	 */
	public static ArrayList<TreeNode> getDeputationTree() {
		ArrayList<TreeNode> treeNode = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			List<Deputation> deputations = DeputationDAO.findAllDeputations(session);
			treeNode = TreeNode.createDepuTree(deputations);

			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		return treeNode;
	}

	/**
	 * 修改成员信息 (已通过测试) 修改by wildhunt_unique 增加了修改部门和代表团 增加了判断是否修改密码 没考虑 团长转代表团的情况
	 * 没考虑部门联络员转部门的情况
	 * 
	 * @author dd
	 * @param member
	 *            输入一个成员对象
	 * 
	 * @return 返回一个状态码
	 */
	public static String alterMember(Member member, Integer deptId, Integer depuId) {
		String stateCode = null;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		try {
			session.getTransaction().begin();

			Member modifyMem = new Member();
			modifyMem = MemberDAO.getMemberByNo(session, member.getMemberNo());
			modifyMem.setMemberName(member.getMemberName());
			Deputation deputation = session.get(Deputation.class, depuId);
			Department department = session.get(Department.class, deptId);
			if (!deputation.getDepuId().equals(modifyMem.getDeputation().getDepuId())) {// 换代表团
				if (modifyMem.getMemberClass().equals(adminClass)) {// 原来是团长
					modifyMem.getDeputation().setManageId(null);// 消除团内 其是团长的记录
					modifyMem.setMemberClass(userClass);// 撤职
				}
				modifyMem.setDeputation(deputation);
			}
			if (!department.getDeptId().equals(modifyMem.getDepartment().getDeptId())) {// 换部门
				if (modifyMem.getMemberClass().equals(masterClass)) {// 原来是部门联络员
					modifyMem.getDepartment().setMaster(null);
					modifyMem.setMemberClass(userClass);// 撤职
				}
				modifyMem.setDepartment(department);
			}
			if (member.getMemberPassword() != null && !member.getMemberPassword().equals("")) {// 是否需要修改密码
				modifyMem.setMemberPassword(member.getMemberPassword());
			}

			stateCode = "success_100000";
			session.getTransaction().commit();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
			return "error";
		}
		return stateCode;
	}

}
