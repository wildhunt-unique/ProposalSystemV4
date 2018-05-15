package cn.edu.qut.beans;

import cn.edu.qut.entity.Department;

public class DeptInfo {
	public DeptInfo(Department department) {
		// TODO Auto-generated constructor stub
		this.deptName = department.getDeptName();
		this.deptMaster = department.getMaster();
	}

	private String deptName;
	private String deptMaster;
	private String dpetManager;
	
	public String getDeptMaster() {
		return deptMaster;
	}

	public void setDeptMaster(String deptMaster) {
		this.deptMaster = deptMaster;
	}

	public String getDpetManager() {
		return dpetManager;
	}

	public void setDpetManager(String dpetManager) {
		this.dpetManager = dpetManager;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
