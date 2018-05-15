package cn.edu.qut.beans;

import java.util.Comparator;

public class SortPropByNo implements Comparator<ProposalInfo> {

	public int compare(ProposalInfo o1, ProposalInfo o2) {
		// TODO Auto-generated method stub
		return o2.getPropNo().compareTo(o1.getPropNo());
	}
}
