package cn.edu.qut.beans;

import cn.edu.qut.common.WordMap;
import cn.edu.qut.entity.Member;

/******************************** 12月14号新增 *****************************************/
public class MemberInfo {
	private long memberId;
	private String memberNo;
	private String memberName;
	private String memberClass;
	private String memberPassword;

	private int numOfProp;
	private String depuName;
	private String deptName;

	private String depuId;
	private String deptId;
	private String memberClassId;

	public MemberInfo() {
		// TODO Auto-generated constructor stub
	}

	public MemberInfo(Member member) {
		this.memberId = member.getMemberId();
		this.memberNo = member.getMemberNo();
		this.memberName = member.getMemberName();
		this.memberClass = WordMap.translate(member.getMemberClass());

		this.numOfProp = member.getProposals().size();
		this.deptName = member.getDepartment().getDeptName();
		this.depuName = member.getDeputation().getDepuName();

		this.deptId = member.getDepartment().getDeptId() + "";
		this.depuId = member.getDeputation().getDepuId() + "";
		this.setMemberClassId(member.getMemberClass());
		this.memberPassword = member.getMemberPassword();
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getDepuId() {
		return depuId;
	}

	public void setDepuId(String depuId) {
		this.depuId = depuId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	public int getNumOfProp() {
		return numOfProp;
	}

	public void setNumOfProp(int numOfProp) {
		this.numOfProp = numOfProp;
	}

	public String getDepuName() {
		return depuName;
	}

	public void setDepuName(String depuName) {
		this.depuName = depuName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getMemberClassId() {
		return memberClassId;
	}

	public void setMemberClassId(String memberClassId) {
		this.memberClassId = memberClassId;
	}

}
