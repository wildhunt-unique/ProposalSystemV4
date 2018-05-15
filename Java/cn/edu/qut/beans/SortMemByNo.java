package cn.edu.qut.beans;

import java.util.Comparator;

public class SortMemByNo implements Comparator<MemberInfo> {

	public int compare(MemberInfo o1, MemberInfo o2) {
		// TODO Auto-generated method stub
		return o1.getMemberNo().compareTo(o2.getMemberNo());
	}
}
