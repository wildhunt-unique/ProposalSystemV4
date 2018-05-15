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
@Table(name = "member")
public class Member {

	@Id
	@Column(name = "member_id")
	private long memberId;

	@Column(name = "member_no")
	private String memberNo;

	@Column(name = "member_name")
	private String memberName;

	@Column(name = "member_class")
	private String memberClass;

	@Column(name = "member_password")
	private String memberPassword;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "depu_id")
	private Deputation deputation;

	@Column(name = "flag")
	private String flag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Department department;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "propOwner")
	private Set<Proposal> proposals = new HashSet<Proposal>(0);

	@ManyToMany(mappedBy = "memberSup", cascade = CascadeType.ALL)
	private Set<Proposal> propSup = new HashSet<Proposal>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
	private Set<Department> manageDept = new HashSet<Department>(0);

	public Set<Department> getManageDept() {
		return manageDept;
	}

	public void setManageDept(Set<Department> manageDept) {
		this.manageDept = manageDept;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Proposal> getPropSup() {
		return propSup;
	}

	public void setPropSup(Set<Proposal> propSup) {
		this.propSup = propSup;
	}

	public Set<Proposal> getProposals() {
		return proposals;
	}

	public void setProposals(Set<Proposal> proposals) {
		this.proposals = proposals;
	}

	public Member() {

	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberClass() {
		return memberClass;
	}

	public void setMemberClass(String memberClass) {
		this.memberClass = memberClass;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public Deputation getDeputation() {
		return deputation;
	}

	public void setDeputation(Deputation deputation) {
		this.deputation = deputation;
	}

}
