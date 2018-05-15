package cn.edu.qut.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "conference")
public class Conference {
	@Id
	@Column(name ="con_id")
	private int conId;
	
	@Column(name="con_name")
	private String conName;

	public  Conference() 
	{
		
	}
	
	public int getConId() {
		return conId;
	}

	public void setConId(int conId) {
		this.conId = conId;
	}

	public String getConName() {
		return conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "propCon")
	private Set<Proposal> proposals=new HashSet<Proposal>(0);

	public Set<Proposal> getProposals() {
		return proposals;
	}

	public void setProposals(Set<Proposal> proposals) {
		this.proposals = proposals;
	}
	
	

}
