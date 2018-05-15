package cn.edu.qut.service;

import java.util.List;
import java.util.Set;

import cn.edu.qut.beans.MemberInfo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.qut.DAO.DepartmentDAO;
import cn.edu.qut.DAO.HibernateUtil;
import cn.edu.qut.DAO.MemberDAO;
import cn.edu.qut.DAO.ProposalDAO;
import cn.edu.qut.beans.TreeNode;
import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Member;
import cn.edu.qut.entity.Proposal;

public class ServerTest {
	public static void main(String[] args) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		try {
			session.getTransaction().begin();
			ObjectMapper mapper = new ObjectMapper();

			Member loginMember = MemberDAO.findMemberByLogin("root","root");
			MemberInfo info = new MemberInfo(loginMember);
			String jsonStr = mapper.writeValueAsString(info);


			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
}
