package cn.edu.qut.beans;

import cn.edu.qut.entity.Deputation;

public class DepuInfo {
	private String depuName;
	private String depuId;
	private String deputManger;
	
	public DepuInfo(Deputation deputation){
		this.depuId = deputation.getDepuId()+"";
		this.depuName = deputation.getDepuName();
		this.deputManger = deputation.getManageId();
	}
	
	public DepuInfo(){
		
	}

	public String getDepuName() {
		return depuName;
	}

	public void setDepuName(String depuName) {
		this.depuName = depuName;
	}

	public String getDepuId() {
		return depuId;
	}

	public void setDepuId(String depuId) {
		this.depuId = depuId;
	}

	public String getDeputManger() {
		return deputManger;
	}

	public void setDeputManger(String deputManger) {
		this.deputManger = deputManger;
	}
	
}
