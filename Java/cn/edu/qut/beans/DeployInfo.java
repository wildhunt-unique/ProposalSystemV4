package cn.edu.qut.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import cn.edu.qut.DAO.ProposalDAO;
import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Member;
import cn.edu.qut.entity.Proposal;

/***************************12.21日********************************************/
/**
 * @author dd
 * 
 * 
 */
public class DeployInfo {
	private String deptName;// 主办部门名字
	private String deplTime;// 部署时间
	private String implTime;// 完成时间
	private String impl;// 完成结果
	private String helpName;// 协办部门名字
	private String satify;// 满意度
	private String alterIdea;// 修改意见

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public DeployInfo() {

	}

	public DeployInfo(Proposal proposal, Session session) {
		Set<Department> helpDepart = proposal.getHelpDept();
		this.helpName = "";
		for (Department department : helpDepart) {
			this.helpName = this.helpName + department.getDeptName() + "、";
		}
		this.deptName = proposal.getPrimaryDept().getDeptName();
		this.deplTime = df.format(proposal.getPropDeplTime());
		this.implTime = df.format(proposal.getPropImplTime());
		this.impl = proposal.getPropImpl();
		this.satify = proposal.getPropSatisf();
		this.alterIdea = proposal.getPropAlterIdea();
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeplTime() {
		return deplTime;
	}

	public void setDeplTime(String deplTime) {
		this.deplTime = deplTime;
	}

	public String getImplTime() {
		return implTime;
	}

	public void setImplTime(String implTime) {
		this.implTime = implTime;
	}

	public String getImpl() {
		return impl;
	}

	public void setImpl(String impl) {
		this.impl = impl;
	}

	public String getSatify() {
		return satify;
	}

	public void setSatify(String satify) {
		this.satify = satify;
	}

	public String getAlterIdea() {
		return alterIdea;
	}

	public void setAlterIdea(String alterIdea) {
		this.alterIdea = alterIdea;
	}

	public String getHelpName() {
		return helpName;
	}

	public void setHelpName(String helpName) {
		this.helpName = helpName;
	}
}
