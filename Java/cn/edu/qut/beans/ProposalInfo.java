package cn.edu.qut.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.Session;

import cn.edu.qut.entity.Department;
import cn.edu.qut.entity.Member;
import cn.edu.qut.entity.Proposal;

public class ProposalInfo {

	private long propId;

	private String propNo;
	private String propTitle;
	private String propType;

	private String propState;
	private String propDate;
	private String propLast; // 最后的修改日期
	private String propContent;

	private String memberName;
	private String depuName;
	private String memberSup;

	private String implResult;
	private String primaryDeptName;
	private String heleDeptName;
	private String implDate;
	private String handShake;

	private String propDealType;
	private String propIdea;
	private String satisfyState;

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 日期格式

	/**
	 * 存放提案信息的类(给用户看的 包括 提出人 提出者所在部门 附议人 等信息)
	 * 
	 * @author wildhunt_unique
	 */
	public ProposalInfo() {

	}

	/**
	 * 按照proposal对象初始化一个 提案信息对象 不包括内容
	 * 
	 * @author wildhunt_unique
	 */
	public ProposalInfo(Session session, Proposal proposal) {
		Set<Member> memberSups = proposal.getMemberSup();
		this.memberSup = "";
		for (Member member : memberSups) {
			this.memberSup = this.memberSup + member.getMemberName() + "、";
		}
		if (proposal.getPropLast() != null) {
			this.propLast = df.format(proposal.getPropLast());
		}
		this.propDate = df.format(proposal.getPropDate());
		this.propId = proposal.getPropId();
		this.propTitle = proposal.getPropTitle();
		this.propNo = proposal.getPropNo();
		this.propState = proposal.getPropState();
		this.propType = proposal.getPropType();
		this.depuName = proposal.getPropOwner().getDeputation().getDepuName();
		this.memberName = proposal.getPropOwner().getMemberName();
		this.satisfyState = "";
		int hanShakeTime = proposal.getPropHandshake();
		this.handShake = hanShakeTime + "";
		this.propDealType = proposal.getPropPassType();
		
		if (proposal.getPropImplTime() != null) {
			this.implDate = df.format(proposal.getPropImplTime());
		}
		if (proposal.getPrimaryDept() != null) {
			this.primaryDeptName = proposal.getPrimaryDept().getDeptName();
		}
		Set<Department> departments = proposal.getHelpDept();
		this.heleDeptName = "";
		for (Department department : departments) {
			this.heleDeptName = this.heleDeptName + department.getDeptName() + "、";
		}
	}
	
	
	/**
	 * 按照proposal对象初始化一个 提案信息对象  括内容\处理结果\提出意见
	 * 
	 * @author wildhunt_unique
	 */
	public ProposalInfo(Proposal proposal) {
		this.propContent = proposal.getPropContent();
		Set<Member> memberSups = proposal.getMemberSup();
		this.memberSup = "";

		for (Member member : memberSups) {
			this.memberSup = this.memberSup + member.getMemberName() + "、";
		}

		if (proposal.getPropLast() != null) {
			this.propLast = df.format(proposal.getPropLast());
		}

		this.propDate = df.format(proposal.getPropDate());
		this.propId = proposal.getPropId();
		this.propTitle = proposal.getPropTitle();
		this.propNo = proposal.getPropNo();
		this.propState = proposal.getPropState();
		this.propType = proposal.getPropType();
		this.depuName = proposal.getPropOwner().getDeputation().getDepuName();
		this.memberName = proposal.getPropOwner().getMemberName();
		this.satisfyState = "";
		int hanShakeTime = proposal.getPropHandshake();
		this.handShake = hanShakeTime + "";
		this.propDealType = proposal.getPropPassType();
		
		if (proposal.getPropImplTime() != null) {
			this.implDate = df.format(proposal.getPropImplTime());
		}
		if (proposal.getPrimaryDept() != null) {
			this.primaryDeptName = proposal.getPrimaryDept().getDeptName();
		}
		Set<Department> departments = proposal.getHelpDept();
		this.heleDeptName = "";
		for (Department department : departments) {
			this.heleDeptName = this.heleDeptName + department.getDeptName() + "、";
		}
		
		if (hanShakeTime >= 2 && proposal.getPropPassType().equals("立案")) {
			this.propIdea = proposal.getPropAlterIdea();
		}
		if (proposal.getPropSatisf() != null && !proposal.getPropSatisf().equals("")) {
			this.satisfyState = proposal.getPropSatisf();
		}
		this.implResult = proposal.getPropImpl();
	}
	
	@Override
	public String toString() {
		return "ProposalInfo [propId=" + propId + ", propNo=" + propNo + ", propTitle=" + propTitle + ", propType="
				+ propType + ", propState=" + propState + ", propDate=" + propDate + ", propLast=" + propLast
				+ ", propContent=" + propContent + ", memberName=" + memberName + ", depuName=" + depuName
				+ ", memberSup=" + memberSup + "]";
	}

	public String getSatisfyState() {
		return satisfyState;
	}

	public void setSatisfyState(String satisfyState) {
		this.satisfyState = satisfyState;
	}

	public String getPropIdea() {
		return propIdea;
	}

	public void setPropIdea(String propIdea) {
		this.propIdea = propIdea;
	}

	public String getPropDealType() {
		return propDealType;
	}

	public void setPropDealType(String propDealType) {
		this.propDealType = propDealType;
	}

	public String getHeleDeptName() {
		return heleDeptName;
	}

	public void setHeleDeptName(String heleDeptName) {
		this.heleDeptName = heleDeptName;
	}

	public String getImplDate() {
		return implDate;
	}

	public void setImplDate(String implDate) {
		this.implDate = implDate;
	}

	public String getHandShake() {
		return handShake;
	}

	public void setHandShake(String handShake) {
		this.handShake = handShake;
	}

	public String getImplResult() {
		return implResult;
	}

	public void setImplResult(String implResult) {
		this.implResult = implResult;
	}

	public String getPrimaryDeptName() {
		return primaryDeptName;
	}

	public void setPrimaryDeptName(String primaryDeptName) {
		this.primaryDeptName = primaryDeptName;
	}

	public String getMemberSup() {
		return memberSup;
	}

	public void setMemberSup(String memberSup) {
		this.memberSup = memberSup;
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

	public String getPropDate() {
		return propDate;
	}

	public void setPropDate(String propDate) {
		this.propDate = propDate;
	}

	public String getPropLast() {
		return propLast;
	}

	public void setPropLast(String propLast) {
		this.propLast = propLast;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getDepuName() {
		return depuName;
	}

	public void setDepuName(String depuName) {
		this.depuName = depuName;
	}

	public String getPropContent() {
		return propContent;
	}

	public void setPropContent(String propContent) {
		this.propContent = propContent;
	}

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}

}
