package cn.edu.qut.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "proposal")
public class Proposal {

	@Id
	@Column(name = "prop_id")
	private long propId;

	@Column(name = "prop_no")
	private String propNo;

	@Column(name = "prop_type")
	private String propType;

	@Column(name = "prop_title")
	private String propTitle;

	@Column(name = "prop_state")
	private String propState;

	@Column(name = "prop_date")
	private Date propDate;

	@Column(name = "prop_last")
	private Date propLast; // 最后的修改日期

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prop_owner")
	private Member propOwner;

	@Column(name = "prop_content")
	private String propContent;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "support", joinColumns = @JoinColumn(name = "prop_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
	private Set<Member> memberSup = new HashSet<Member>();


	/*********************************12.19****************************************/
	@Column(name="prop_passType")
	private String propPassType;//部署中的提案的类型
	
	@Column(name="prop_satisf")//提案提出人对提案的满意度
	private String propSatisf;
	
	@Column(name="prop_implTime")//提案的完成时间
	private Date propImplTime;
	
	@Column(name="prop_deplTime")//提案的部署时间
	private Date propDeplTime;
	
	@Column(name = "prop_impl")//实施结果
	private String propImpl;
	
	@Column(name = "prop_alterIdea")//修改意见
	private String propAlterIdea;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prop_deptId")
	private Department primaryDept;//主办部门
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "deploy", joinColumns = @JoinColumn(name = "prop_id"), inverseJoinColumns = @JoinColumn(name = "dept_helpId"))
	private Set<Department> helpDept = new HashSet<Department>(); //所有部署中的提案
	
	@Column(name="prop_handshake")
	private Integer propHandshake;
	
	/*********************************12.28****************************************/
    @ManyToOne(fetch = FetchType.LAZY)//多个提案对应一个会议
    @JoinColumn(name ="prop_conId")
    private Conference propCon;
	
    public Conference getPropCon() {
		return propCon;
	}

	public void setPropCon(Conference propCon) {
		this.propCon = propCon;
	}
	
	/****************************************************************************/
	
	public Integer getPropHandshake() {
		return propHandshake;
	}

	public void setPropHandshake(Integer propHandshake) {
		this.propHandshake = propHandshake;
	}

	public Department getPrimaryDept() {
		return primaryDept;
	}

	public void setPrimaryDept(Department primaryDept) {
		this.primaryDept = primaryDept;
	}
	
	public String getPropPassType() {
		return propPassType;
	}

	public void setPropPassType(String propPassType) {
		this.propPassType = propPassType;
	}

	public String getPropSatisf() {
		return propSatisf;
	}

	public void setPropSatisf(String propSatisf) {
		this.propSatisf = propSatisf;
	}

	public Date getPropImplTime() {
		return propImplTime;
	}

	public void setPropImplTime(Date propImplTime) {
		this.propImplTime = propImplTime;
	}

	public Date getPropDeplTime() {
		return propDeplTime;
	}

	public void setPropDeplTime(Date propDeplTime) {
		this.propDeplTime = propDeplTime;
	}
	
	public String getPropImpl() {
		return propImpl;
	}

	public void setPropImpl(String propImpl) {
		this.propImpl = propImpl;
	}

	public String getPropAlterIdea() {
		return propAlterIdea;
	}

	public void setPropAlterIdea(String propAlterIdea) {
		this.propAlterIdea = propAlterIdea;
	}
	
	
	public Set<Department> getHelpDept() {
		return helpDept;
	}

	public void setHelpDept(Set<Department> helpDept) {
		this.helpDept = helpDept;
	}

	/*******************************************************************************/
	
	
	public Set<Member> getMemberSup() {
		return memberSup;
	}

	public void setMemberSup(Set<Member> memberSup) {
		this.memberSup = memberSup;
	}

	public Proposal() {

	}

	public String getPropContent() {
		return propContent;
	}

	public void setPropContent(String propContent) {
		this.propContent = propContent;
	}

	public long getPropId() {
		return propId;
	}

	public void setPropId(long propId) {
		this.propId = propId;
	}

	public String getPropNo() {
		return propNo;
	}

	public void setPropNo(String propNo) {
		this.propNo = propNo;
	}

	public String getPropTitle() {
		return propTitle;
	}

	public void setPropTitle(String propTitle) {
		this.propTitle = propTitle;
	}

	public String getPropState() {
		return propState;
	}

	public void setPropState(String propState) {
		this.propState = propState;
	}

	public Date getPropDate() {
		return propDate;
	}

	public void setPropDate(Date propDate) {
		this.propDate = propDate;
	}

	public Date getPropLast() {
		return propLast;
	}

	public void setPropLast(Date propLast) {
		this.propLast = propLast;
	}

	public Member getPropOwner() {
		return propOwner;
	}

	public void setPropOwner(Member propOwner) {
		this.propOwner = propOwner;
	}

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}
}
