package cn.edu.qut.servlet;

import java.util.ArrayList;

import cn.edu.qut.DAO.MemberDAO;
import cn.edu.qut.beans.MemberInfo;
import cn.edu.qut.beans.ProposalInfo;
import cn.edu.qut.entity.Member;
import cn.edu.qut.service.ProposalManagement;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();

		Member loginMember = MemberDAO.findMemberByLogin("root","root");
		MemberInfo info = new MemberInfo(loginMember);
		String jsonStr = null;

		System.out.println(jsonStr);
	}

}
