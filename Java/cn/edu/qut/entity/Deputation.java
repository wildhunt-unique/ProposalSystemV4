package cn.edu.qut.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "deputation")
public class Deputation {

	@Id
	@Column(name = "depu_id")
	private Integer depuId; // 代表团标识符

	@Column(name = "depu_name", nullable = false)
	private String depuName; // 代表团名字
	
	@Column(name = "manage_id")
	private String manageId; // boss的id 为了便于管理 就不引入实体了

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "deputation")
	private Set<Member> members = new HashSet<Member>(0);//代表下的成员

	@Column(name = "flag")
	private String flag;
	
	public Deputation() {
		
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Integer getDepuId() {
		return depuId;
	}

	public void setDepuId(Integer depuId) {
		this.depuId = depuId;
	}

	public String getDepuName() {
		return depuName;
	}

	public void setDepuName(String depuName) {
		this.depuName = depuName;
	}

	public String getManageId() {
		return manageId;
	}

	public void setManageId(String manageId) {
		this.manageId = manageId;
	}

	public Set<Member> getMembers() {
		return members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}
}
