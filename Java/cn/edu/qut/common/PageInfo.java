package cn.edu.qut.common;

import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.qut.beans.MemberInfo;
import cn.edu.qut.beans.SortByDate;
import cn.edu.qut.beans.SortMemByNo;
import cn.edu.qut.service.AccountManagement;

public class PageInfo {
	private int total;
	private ArrayList rows = new ArrayList();

	public PageInfo() {
		
	}
	
	public static PageInfo cratePageInfo(ArrayList datas, int page, int rowsInt) {
		int begin = (page - 1) * rowsInt;
		if (begin > datas.size()) {
			return null;
		}
		PageInfo pageInfo = new PageInfo();
		pageInfo.setTotal(datas.size());
		ArrayList pageDate = new ArrayList();
		
		for (int i = begin; i < (begin+rowsInt); i++) {
			System.out.println(i+":"+datas.size());
			if (i > datas.size()-1) {
				continue;
			}else {
				pageDate.add(datas.get(i));
			}
		}
		pageInfo.setRows(pageDate);
		return pageInfo;
	}
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList getRows() {
		return rows;
	}

	public void setRows(ArrayList rows) {
		this.rows = rows;
	}

	public static void main(String args[]) {
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<MemberInfo> memberInfos = AccountManagement.findMemberByDepaId(null);
		Collections.sort(memberInfos, new SortMemByNo());
		PageInfo pageInfo = PageInfo.cratePageInfo(memberInfos, 4, 10); 
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(pageInfo);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (MemberInfo memberInfo : memberInfos) {
			System.out.println(memberInfo.getMemberNo()+":"+memberInfo.getMemberName());
		}
		System.out.println(jsonStr);
	}
}
