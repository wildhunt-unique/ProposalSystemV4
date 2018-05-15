package cn.edu.qut.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.pagination.SQL2008StandardLimitHandler;

import cn.edu.qut.entity.Department;

public class DepartmentDAO {

	/**
	 * 查找数据库中部门ID的最大值
	 * 
	 * @author dd
	 * @return 返回当前数据库中的部门ID的最大值
	 * 
	 */
	public static int getDepartmentMaxId(Session session) {
		Number maxId = null;
		String sqlString = "select max(d.deptId) from " + Department.class.getName() + " d ";
		Query<Number> query = session.createQuery(sqlString);
		maxId = query.getSingleResult();
		if (maxId == null) {
			maxId = 100000L;
		}
		return maxId.intValue();
	}

	/**
	 * 根据所给的部门ID找到部门
	 * 
	 * @author dd
	 * @param deptId
	 *            部门ID
	 * @return 返回一个ID值为deptId的部门对象
	 */
	public static Department findDepartment(Session session, int deptId) {
		Department department = null;
		String sqlstring = "select d from " + Department.class.getName() + " d where d.deptId = :deptId";
		Query<Department> query = session.createQuery(sqlstring);
		query.setParameter("deptId", deptId);
		department = query.getSingleResult();
		return department;
	}

	/**
	 * 得到所有部门的持久化对象
	 * @author wildhunt_unique
	 * */
	public static List<Department> findAllDepartment(Session session) {
		List<Department> departments = null;

		String sql = "select d from " + Department.class.getName() + " d";
		Query<Department> query = session.createQuery(sql);
		departments = query.getResultList();

		return departments;
	}
}
