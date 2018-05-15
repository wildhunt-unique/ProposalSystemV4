package cn.edu.qut.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {

	@Id
	@Column(name = "dept_id")
	private Integer deptId; // 部门标识符

	@Column(name = "dept_name", nullable = false)
	private String deptName; // 部门名称

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "department")
	private Set<Member> members = new HashSet<Member>(0);

	/*************************** 12.19 **********************************/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryDept")
	private Set<Proposal> primaryProposal = new HashSet<Proposal>(0);

	@ManyToMany(mappedBy = "helpDept", cascade = CascadeType.ALL)
	private Set<Proposal> helpProposal = new HashSet<Proposal>(0);

	@Column(name = "flag")
	private String flag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_manager")
	private Member manager;

	@Column(name="dept_master")
	private String Master;
	
	public String getMaster() {
		return Master;
	}

	public void setMaster(String master) {
		Master = master;
	}

	public Member getManager() {
		return manager;
	}

	public void setManager(Member manager) {
		this.manager = manager;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Set<Proposal> getPrimaryProposal() {
		return primaryProposal;
	}

	public void setPrimaryProposal(Set<Proposal> primaryProposal) {
		this.primaryProposal = primaryProposal;
	}

	public Set<Proposal> getHelpProposal() {
		return helpProposal;
	}

	public void setHelpProposal(Set<Proposal> helpProposal) {
		this.helpProposal = helpProposal;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	public Department() {
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
